package com.clickless.workflow.controller;

import com.clickless.common.core.domain.AjaxResult;
import com.clickless.common.utils.DateUtils;
import com.clickless.workflow.cmd.DelelteExecutionCommand;
import com.clickless.workflow.cmd.DeleteTaskCommand;
import com.clickless.workflow.enums.BusinessStatusEnum;
import com.clickless.workflow.req.TaskCompleteREQ;
import com.clickless.workflow.req.TaskREQ;
import com.clickless.workflow.service.IBusinessStatusService;
import com.clickless.workflow.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.bpmn.model.*;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("任务管理控制层")
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    TaskRuntime taskRuntime;

    @Autowired
    HistoryService historyService;

    @Autowired
    IBusinessStatusService businessStatusService;

    @Autowired
    ManagementService managementService;

    @ApiOperation("查询当前用户的待办任务")
    @PostMapping("/list/wait")
    public AjaxResult findWaitTask(@RequestBody TaskREQ req) {

        String assignee = UserUtils.getUsername();

        TaskQuery query = taskService.createTaskQuery()
                .taskCandidateOrAssigned(assignee) // 候选人或者办理人
                .orderByTaskCreateTime().asc();

        if (StringUtils.isNotEmpty(req.getTaskName())) {
            query.taskNameLikeIgnoreCase("%" + req.getTaskName() + "%");
        }
        // 分页查询
        List<Task> taskList = query.listPage(req.getFirstResult(), req.getSize());

        long total = query.count();

        List<Map<String, Object>> records = new ArrayList<>();
        for (Task task : taskList) {
            Map<String, Object> result = new HashMap<>();
            result.put("taskId", task.getId());
            result.put("taskName", task.getName());
            result.put("processStatus", task.isSuspended() ? "已暂停" : "已启动");
            result.put("taskCreateTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, task.getCreateTime()));
            result.put("processInstanceId", task.getProcessInstanceId());
            result.put("executionId", task.getExecutionId());
            result.put("processDefinitionId", task.getProcessDefinitionId());
            // 任务办理人: 如果是候选人则没有值，办理人才有
            result.put("taskAssignee", task.getAssignee());

            // 查询流程实例
            ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId()).singleResult();
            result.put("processName", pi.getProcessDefinitionName());
            result.put("version", pi.getProcessDefinitionVersion());
            result.put("proposer", pi.getStartUserId());
            result.put("businessKey", pi.getBusinessKey());

            records.add(result);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("records", records);
        return AjaxResult.success(result);
    }


    @ApiOperation("获取目标节点（下一个节点）")
    @GetMapping("/next/node")
    public AjaxResult getNextNodeInfo(@RequestParam String taskId) {
        // 1. 获取当前任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // 2. 从当前任务信息中获取此流程定义id
        String processDefinitionId = task.getProcessDefinitionId();
        // 3. 拿到流程定义id后可获取此bpmnModel对象
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        // 4. 通过任务节点id，来获取当前节点信息
        FlowElement flowElement = bpmnModel.getFlowElement(task.getTaskDefinitionKey());
        // 封装下一个用户任务节点信息
        List<Map<String, Object>> nextNodes = new ArrayList<>();
        getNextNodes(flowElement, nextNodes);

        return AjaxResult.success(nextNodes);
    }

    public void getNextNodes(FlowElement flowElement, List<Map<String, Object>> nextNodes) {
        // 获取当前节点的连线信息
        List<SequenceFlow> outgoingFlows = ((FlowNode) flowElement).getOutgoingFlows();
        // 当前节点的所有下一节点出口
        for (SequenceFlow outgoingFlow : outgoingFlows) {
            // 下一节点的目标元素
            FlowElement nextFlowElement = outgoingFlow.getTargetFlowElement();
            if (nextFlowElement instanceof UserTask) {
                // 用户任务，则获取响应给前端设置办理人或者候选人
                Map<String, Object> node = new HashMap<>();
                node.put("id", nextFlowElement.getId()); // 节点id
                node.put("name", nextFlowElement.getName()); // 节点id
                nextNodes.add(node);
            } else if (nextFlowElement instanceof EndEvent) {
                break;
            } else if (nextFlowElement instanceof ParallelGateway // 并行网关
                    || nextFlowElement instanceof ExclusiveGateway) { // 排他网关
                getNextNodes(nextFlowElement, nextNodes);
            }
        }
    }

    @ApiOperation("完成任务")
    @PostMapping("/complete")
    public AjaxResult completeTask(@RequestBody TaskCompleteREQ req) {
        String taskId = req.getTaskId();
        // 1. 查询任务信息
        org.activiti.api.task.model.Task task = taskRuntime.task(taskId);
        if (task == null) {
            return AjaxResult.error("任务不存在或不是您办理的任务");
        }
        String procInstId = task.getProcessInstanceId();
        // 2. 指定任务审批意见
        taskService.addComment(taskId, procInstId, req.getMessage());

        // 3. 完成任务
        taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskId).build());

        // 4. 查询下一个任务
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(procInstId).list();

        // 5. 指定办理人
        if (CollectionUtils.isEmpty(taskList)) {
            // task.getBusinessKey() m5版本中没有 值
            HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(procInstId).singleResult();
            // 更新业务状态已完成
            businessStatusService.updateState(hpi.getBusinessKey(), BusinessStatusEnum.FINISH);
            return AjaxResult.success();
        } else {
            Map<String, List<String>> assigneeMap = req.getAssigneeMap();
            if (assigneeMap == null) {
                // 如果没有办理人，直接将流程实例删除（非法操作）
                deleteProcessInstance(procInstId);
                return AjaxResult.success("审批节点未分配审批人，流程直接中断取消");
            }
            // 有办理人
            for (Task t : taskList) {
                if (StringUtils.isNotEmpty(t.getAssignee())) {
                    // 如果当前任务有办理人，则直接忽略，不用指定办理人
                    continue;
                }
                // 根据当前任务节点id获取办理人
                String[] assignees = req.getAssignees(t.getTaskDefinitionKey());
                if (ArrayUtils.isEmpty(assignees)) {
                    // 没有办理人
                    deleteProcessInstance(procInstId);
                    return AjaxResult.success("审批节点未分配审批人，流程直接中断取消");
                }

                if (assignees.length == 1) {
                    taskService.setAssignee(t.getId(), assignees[0]);
                } else {
                    // 多个作为候选人
                    for (String assignee : assignees) {
                        taskService.addCandidateUser(t.getId(), assignee);
                    }
                }
            }
        }

        return AjaxResult.success();
    }

    private void deleteProcessInstance(String procInstId) {
        runtimeService.deleteProcessInstance(procInstId, "审批节点未分配审批人，流程直接中断取消");
        HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(procInstId).singleResult();
        businessStatusService.updateState(hpi.getBusinessKey(), BusinessStatusEnum.CANCEL);
    }

    @ApiOperation("签收（拾取）任务")
    @PostMapping("/claim")
    public AjaxResult claimTask(@RequestParam String taskId) {
        try {
            taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(taskId).build());
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("签收任务失败：" + e.getMessage());
        }
    }

    @ApiOperation("转办任务给别人办理")
    @PostMapping("/turn")
    public AjaxResult turnTask(@RequestParam String taskId,
                               @RequestParam String assigneeUserKey) {
        try {
            org.activiti.api.task.model.Task task = taskRuntime.task(taskId);
            // 转办
            taskService.setAssignee(taskId, assigneeUserKey);

            String message = String.format("%s 转办任务 【%s】给 %s 办理",
                    UserUtils.getUsername(), task.getName(), assigneeUserKey);

            // 处理意见
            taskService.addComment(taskId, task.getProcessInstanceId(), message);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("转办任务失败：" + e.getMessage());
        }
    }

    @ApiOperation("获取历史任务节点，用于驳回功能")
    @GetMapping("/back/nodes")
    public AjaxResult getBackNodes(@RequestParam String taskId) {
        try {
            Task task = taskService.createTaskQuery()
                    .taskId(taskId)
                    .taskAssignee(UserUtils.getUsername())
                    .singleResult();
            if (task == null) {
                return AjaxResult.error("没有此任务或不是该任务办理人");
            }
            // 查询历史任务节点
           /* List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .list();*/

            // 不把当前节点查询出来, 没有办理完的节点不查询，每条数据都有一个唯一值，我们使用随机数
            String sql = "select random() AS ID_, t2.* from " +
                    " ( select distinct t1.TASK_DEF_KEY_, t1.NAME_ from " +
                    "  ( select ID_, RES.TASK_DEF_KEY_, RES.NAME_, RES.START_TIME_, RES.END_TIME_ " +
                    "   from ACT_HI_TASKINST RES " +
                    "   WHERE RES.PROC_INST_ID_ = #{processInstanceId} and TASK_DEF_KEY_ != #{taskDefKey}" +
                    "   and RES.END_TIME_ is not null order by RES.START_TIME_ asc " +
                    "  ) t1 " +
                    " ) t2";

            List<HistoricTaskInstance> list = historyService.createNativeHistoricTaskInstanceQuery()
                    .sql(sql)
                    .parameter("processInstanceId", task.getProcessInstanceId())
                    .parameter("taskDefKey", task.getTaskDefinitionKey()) // 不把当前节点查询出来
                    .list();

            List<Map<String, Object>> records = new ArrayList<>();

            for (HistoricTaskInstance hti : list) {
                Map<String, Object> data = new HashMap<>();
                data.put("activityId", hti.getTaskDefinitionKey());
                data.put("activityName", hti.getName());
                records.add(data);
            }

            return AjaxResult.success(records);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("查询驳回节点失败：" + e.getMessage());
        }
    }

    @ApiOperation("驳回历史节点")
    @PostMapping("/back")
    public AjaxResult backProcess(@RequestParam String taskId,
                                  @RequestParam String targetActivityId) {
        try {
            // 1. 查询当前任务信息
            Task task = taskService.createTaskQuery()
                    .taskId(taskId)
                    .taskAssignee(UserUtils.getUsername())
                    .singleResult();
            if (task == null) {
                return AjaxResult.error("当前任务不存在或你不是任务办理人");
            }

            String procInstId = task.getProcessInstanceId();

            // 2. 获取流程模型实例 BpmnModel
            BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
            // 3. 当前节点信息
            FlowNode curFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(task.getTaskDefinitionKey());
            // 4. 获取当前节点的原出口连线
            List<SequenceFlow> sequenceFlowList = curFlowNode.getOutgoingFlows();
            // 5. 临时存储当前节点的原出口连线
            List<SequenceFlow> oriSequenceFlows = new ArrayList<>();
            oriSequenceFlows.addAll(sequenceFlowList);
            // 6. 将当前节点的原出口清空
            sequenceFlowList.clear();
            // 7. 获取目标节点信息
            FlowNode targetFlowNode = (FlowNode) bpmnModel.getFlowElement(targetActivityId);
            // 8. 获取驳回的新节点
            // 获取目标节点的入口连线
            List<SequenceFlow> incomingFlows = targetFlowNode.getIncomingFlows();
            // 存储所有目标出口
            List<SequenceFlow> allSequenceFlow = new ArrayList<>();
            for (SequenceFlow incomingFlow : incomingFlows) {
                // 找到入口连线的源头（获取目标节点的父节点）
                FlowNode source = (FlowNode) incomingFlow.getSourceFlowElement();
                List<SequenceFlow> sequenceFlows;
                if (source instanceof ParallelGateway) {
                    // 并行网关: 获取目标节点的父节点（并行网关）的所有出口，
                    sequenceFlows = source.getOutgoingFlows();
                } else {
                    // 其他类型父节点, 则获取目标节点的入口连续
                    sequenceFlows = targetFlowNode.getIncomingFlows();
                }
                allSequenceFlow.addAll(sequenceFlows);
            }

            // 9. 将当前节点的出口设置为新节点
            curFlowNode.setOutgoingFlows(allSequenceFlow);

            // 10. 完成当前任务，流程就会流向目标节点创建新目标任务
            //      删除已完成任务，删除已完成并行任务的执行数据 act_ru_execution
            List<Task> list = taskService.createTaskQuery().processInstanceId(procInstId).list();
            for (Task t : list) {
                if (taskId.equals(t.getId())) {
                    // 当前任务，完成当前任务
                    String message = String.format("【%s 驳回任务 %s => %s】",
                            UserUtils.getUsername(), task.getName(), targetFlowNode.getName());
                    taskService.addComment(t.getId(), procInstId, message);
                    // 完成任务，就会进行驳回到目标节点，产生目标节点的任务数据
                    taskService.complete(taskId);
                    // 删除执行表中 is_active_ = 0的执行数据， 使用command自定义模型
                    DelelteExecutionCommand deleteExecutionCMD = new DelelteExecutionCommand(task.getExecutionId());
                    managementService.executeCommand(deleteExecutionCMD);
                } else {
                    // 删除其他未完成的并行任务
                    // taskService.deleteTask(taskId); // 注意这种方式删除不掉，会报错：流程正在运行中无法删除。
                    // 使用command自定义命令模型来删除，直接操作底层的删除表对应的方法，对应的自定义是否删除
                    DeleteTaskCommand deleteTaskCMD = new DeleteTaskCommand(t.getId());
                    managementService.executeCommand(deleteTaskCMD);
                }
            }

            // 11. 完成驳回功能后，将当前节点的原出口方向进行恢复
            curFlowNode.setOutgoingFlows(oriSequenceFlows);

            // 12. 查询目标任务节点历史办理人
            List<Task> newTaskList = taskService.createTaskQuery().processInstanceId(procInstId).list();
            for (Task newTask : newTaskList) {
                // 取之前的历史办理人
                HistoricTaskInstance oldTargerTask = historyService.createHistoricTaskInstanceQuery()
                        .taskDefinitionKey(newTask.getTaskDefinitionKey()) // 节点id
                        .processInstanceId(procInstId)
                        .finished() // 已经完成才是历史
                        .orderByTaskCreateTime().desc() // 最新办理的在最前面
                        .list().get(0);
                taskService.setAssignee(newTask.getId(), oldTargerTask.getAssignee());
            }

            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("驳回失败：" + e.getMessage());
        }
    }


    @ApiOperation("查询当前登录用户已完成任务信息")
    @PostMapping("/list/complete")
    public AjaxResult findCompleteTask(@RequestBody TaskREQ req) {
        try {
            HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery()
                    .taskAssignee(UserUtils.getUsername())
                    .orderByTaskCreateTime()
                    .desc()
                    .finished();// 已办任务

            if (StringUtils.isNotEmpty(req.getTaskName())) {
                query.taskNameLike("%" + req.getTaskName() + "%");
            }

            // 分页查询
            List<HistoricTaskInstance> taskList = query.listPage(req.getFirstResult(), req.getSize());

            long total = query.count();

            List<Map<String, Object>> records = new ArrayList<>();

            for (HistoricTaskInstance task : taskList) {
                Map<String, Object> result = new HashMap<>();
                result.put("taskId", task.getId());
                result.put("taskName", task.getName());
                result.put("taskStartTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, task.getStartTime()));
                result.put("taskEndTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, task.getEndTime()));
                result.put("processInstanceId", task.getProcessInstanceId());
                result.put("processDefinitionId", task.getProcessDefinitionId());

                // 查询流程实例
                HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(task.getProcessInstanceId()).singleResult();
                result.put("processName", pi.getProcessDefinitionName());
                result.put("version", pi.getProcessDefinitionVersion());
                result.put("proposer", pi.getStartUserId());
                result.put("businessKey", pi.getBusinessKey());

                records.add(result);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("total", total);
            result.put("records", records);

            return AjaxResult.success(result);

        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("查询失败：" + e.getMessage());
        }
    }
}




























