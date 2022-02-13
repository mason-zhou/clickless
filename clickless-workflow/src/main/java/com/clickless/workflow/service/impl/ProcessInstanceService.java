package com.clickless.workflow.service.impl;

import com.clickless.common.utils.DateUtils;
import com.clickless.workflow.activiti.image.CustomProcessDiagramGenerator;
import com.clickless.workflow.domain.BusinessStatus;
import com.clickless.workflow.domain.ProcessConfig;
import com.clickless.workflow.enums.BusinessStatusEnum;
import com.clickless.workflow.exception.WorkflowException;
import com.clickless.workflow.req.ProcInstREQ;
import com.clickless.workflow.req.StartREQ;
import com.clickless.workflow.service.IBusinessStatusService;
import com.clickless.workflow.service.IProcessConfigService;
import com.clickless.workflow.service.IProcessInstanceService;
import com.clickless.workflow.util.UserUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProcessInstanceService extends ActivitiService implements IProcessInstanceService {

    private static final String FORM_NAME = "formName";

    @Autowired
    IProcessConfigService processConfigService;

    @Autowired
    IBusinessStatusService businessStatusService;

    @Override
    public void startProcess(StartREQ req) throws WorkflowException {
        // 1. 通过业务路由名获取流程配置信息：流程定义key和表单组件名（查询历史审批记录需要）
        ProcessConfig processConfig =
                processConfigService.getByBusinessRoute(req.getBusinessRoute());

        // 说明没有在流程定义上进行流程配置，需要先配置对应的路由和表单
        if (processConfig == null) {
            throw new WorkflowException("当前业务场景未绑定流程定义，请联系系统管理员");
        }

        // 2. 表单组件名设置到流程变量中，后面查询历史审批记录需要
        Map<String, Object> variables = req.getVariables(); // 前端已经传递了当前申请信息｛entity: {业务申请数据}}
        variables.put(FORM_NAME, processConfig.getFormName());

        // 判断办理人为空，则直接结束
        List<String> assignees = req.getAssignees();
        if (CollectionUtils.isEmpty(assignees)) {
            throw new WorkflowException("请指定审批人");
        }

        // 3. 启动流程实例（提交申请）
        Authentication.setAuthenticatedUserId(UserUtils.getUsername());
        ProcessInstance pi =
                runtimeService.startProcessInstanceByKey(processConfig.getProcessKey(),
                        req.getBusinessKey(), variables);

        // 将流程定义名称 作为 流程实例名称
        runtimeService.setProcessInstanceName(pi.getProcessInstanceId(), pi.getProcessDefinitionName());

        // 4. 设置任务办理人
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
        for (Task task : taskList) {
            if (assignees.size() == 1) {
                // 如果只能一个办理人，则直接设置为办理人
                taskService.setAssignee(task.getId(), assignees.get(0));
            } else {
                // 多个办理人，则设置为候选人
                for (String assignee : assignees) {
                    taskService.addCandidateUser(task.getId(), assignee);
                }
            }
        }

        // 5. 更新业务状态为：处理中, 和流程实例id
        businessStatusService.updateState(req.getBusinessKey(), BusinessStatusEnum.PROCESS, pi.getProcessInstanceId());
    }

    @Override
    public void cancel(String businessKey, String procInstId, String message) {
        // 1. 删除当前流程实例
        runtimeService.deleteProcessInstance(procInstId,
                UserUtils.getUsername() + " 主动撤回了当前申请：" + message);

        // 2. 删除历史记录
        historyService.deleteHistoricProcessInstance(procInstId);
        historyService.deleteHistoricTaskInstance(procInstId);

        // 3. 更新业务状态
        businessStatusService.updateState(businessKey, BusinessStatusEnum.CANCEL, "");
    }

    @Override
    public Object getFormNameByProcInstId(String procInstId) {
        // 通过流程实例id获取流程实例相关数据：流程变量
        HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
                .includeProcessVariables()
                .processInstanceId(procInstId).singleResult();

        return hpi.getProcessVariables().get("formName");
    }

    @Override
    public List<Map<String, Object>> getHistoryInfoList(String procInstId) {
        // 查询每任务节点历史办理情况
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(procInstId)
                .orderByHistoricTaskInstanceStartTime()
                .asc()
                .list();

        List<Map<String, Object>> records = new ArrayList<>();
        for (HistoricTaskInstance hti : list) {
            Map<String, Object> result = new HashMap<>();
            result.put("taskId", hti.getId()); // 任务ID
            result.put("taskName", hti.getName()); // 任务名称
            result.put("processInstanceId", hti.getProcessInstanceId()); //流程实例ID
            result.put("startTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, hti.getStartTime())); // 开始时间
            result.put("endTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, hti.getEndTime())); // 结束时间
            result.put("status", hti.getEndTime() == null ? "待处理" : "已处理"); // 状态
            result.put("assignee", hti.getAssignee()); // 办理人

            // 撤回原因
            String message = hti.getDeleteReason();
            if (StringUtils.isEmpty(message)) {
                List<Comment> taskComments = taskService.getTaskComments(hti.getId());
                message = taskComments.stream()
                        .map(m -> m.getFullMessage()).collect(Collectors.joining("。"));
            }
            result.put("message", message);

            records.add(result);
        }

        return records;
    }

    @Override
    public void getHistoryProcessImage(String prodInstId, HttpServletResponse response) {
        InputStream inputStream = null;
        try {
            // 1.查询流程实例历史数据
            HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(prodInstId).singleResult();

            // 2. 查询流程中已执行的节点，按时开始时间降序排列
            List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(prodInstId)
                    .orderByHistoricActivityInstanceStartTime().desc()
                    .list();

            // 3. 单独的提取高亮节点id (绿色）
            List<String> highLightedActivityIdList =
                    historicActivityInstanceList.stream()
                            .map(HistoricActivityInstance::getActivityId).collect(Collectors.toList());

            // 4. 正在执行的节点 （红色）
            List<Execution> runningActivityInstanceList = runtimeService.createExecutionQuery()
                    .processInstanceId(prodInstId).list();

            List<String> runningActivityIdList = new ArrayList<>();
            for (Execution execution : runningActivityInstanceList) {
                if (StringUtils.isNotEmpty(execution.getActivityId())) {
                    runningActivityIdList.add(execution.getActivityId());
                }
            }

            // 获取流程定义Model对象
            BpmnModel bpmnModel = repositoryService.getBpmnModel(instance.getProcessDefinitionId());

            // 实例化流程图生成器
            CustomProcessDiagramGenerator generator = new CustomProcessDiagramGenerator();
            // 获取高亮连线id
            List<String> highLightedFlows = generator.getHighLightedFlows(bpmnModel, historicActivityInstanceList);
            // 生成历史流程图
            inputStream = generator.generateDiagramCustom(bpmnModel, highLightedActivityIdList,
                    runningActivityIdList, highLightedFlows,
                    "宋体", "微软雅黑", "黑体");

            // 响应相关图片
            response.setContentType("image/svg+xml");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Map<String, Object> getProcInstRunning(ProcInstREQ req) {
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
        if (StringUtils.isNotEmpty(req.getProcessName())) {
            query.processInstanceNameLikeIgnoreCase(req.getProcessName());
        }
        if (StringUtils.isNotEmpty(req.getProposer())) {
            query.startedBy(req.getProposer());
        }

        List<ProcessInstance> instanceList = query.listPage(req.getFirstResult(), req.getSize());
        long total = query.count();

        List<Map<String, Object>> records = new ArrayList<>();
        for (ProcessInstance pi : instanceList) {
            Map<String, Object> result = new HashMap<>();
            result.put("processInstanceId", pi.getProcessInstanceId());
            result.put("processInstanceName", pi.getName());
            result.put("processKey", pi.getProcessDefinitionKey());
            result.put("version", pi.getProcessDefinitionVersion());
            result.put("proposer", pi.getStartUserId());
            result.put("processStatus", pi.isSuspended() ? "已暂停" : "已启动");
            result.put("businessKey", pi.getBusinessKey());
            result.put("startTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, pi.getStartTime()));

            // 查询当前实例的当前任务
            List<Task> taskList = taskService.createTaskQuery()
                    .processInstanceId(pi.getProcessInstanceId()).list();
            String currTaskInfo = ""; // 当前任务
            for (Task task : taskList) {
                currTaskInfo += "任务名【" + task.getName() + "】，办理人【" + task.getAssignee() + "】<br>";
            }
            result.put("currTaskInfo", currTaskInfo);

            records.add(result);
        }

        Collections.sort(records, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> m1, Map<String, Object> m2) {
                String date1 = (String) m1.get("startTime");
                String date2 = (String) m2.get("startTime");
                return date2.compareTo(date1);
            }
        });

        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("records", records);

        return result;
    }

    @Override
    public Map<String, Object> getProcInstFinish(ProcInstREQ req) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery()
                .finished() // 已结束的
                .orderByProcessInstanceEndTime().desc();

        if (StringUtils.isNotEmpty(req.getProcessName())) {
            query.processInstanceNameLikeIgnoreCase(req.getProcessName());
        }
        if (StringUtils.isNotEmpty(req.getProposer())) {
            query.startedBy(req.getProposer());
        }

        List<HistoricProcessInstance> instanceList = query.listPage(req.getFirstResult(), req.getSize());
        long total = query.count();

        List<Map<String, Object>> records = new ArrayList<>();
        for (HistoricProcessInstance pi : instanceList) {
            Map<String, Object> result = new HashMap<>();
            result.put("processInstanceId", pi.getId());
            result.put("processInstanceName", pi.getName());
            result.put("processKey", pi.getProcessDefinitionKey());
            result.put("version", pi.getProcessDefinitionVersion());
            result.put("proposer", pi.getStartUserId());
            result.put("businessKey", pi.getBusinessKey());
            result.put("startTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, pi.getStartTime()));
            result.put("endTime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, pi.getEndTime()));
            // 原因
            result.put("deleteReason", pi.getDeleteReason());

            // 业务状态
            BusinessStatus businessStatus = businessStatusService.getById(pi.getBusinessKey());
            if (businessStatus != null) {
                result.put("status", BusinessStatusEnum.getEnumByCode(businessStatus.getStatus()).getDesc());
            }

            records.add(result);
        }


        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("records", records);

        return result;
    }

    @Override
    public void deleteProcInstAndHistory(String procInstId) {
        // 1. 查询历史流程实例
        HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(procInstId).singleResult();

        // 2. 删除历史流程实例
        historyService.deleteHistoricProcessInstance(procInstId);
        historyService.deleteHistoricTaskInstance(procInstId);

        // 3. 更新流程业务状态, 注意：流程实例id传递一个空字符串""，不要是null,不然无法更新到
        businessStatusService.updateState(instance.getBusinessKey(), BusinessStatusEnum.DELETE, "");
    }


}
