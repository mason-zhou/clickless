package com.clickless.workflow.controller;

import com.clickless.common.core.domain.AjaxResult;
import com.clickless.workflow.enums.BusinessStatusEnum;
import com.clickless.workflow.exception.WorkflowException;
import com.clickless.workflow.req.ProcInstREQ;
import com.clickless.workflow.req.StartREQ;
import com.clickless.workflow.service.IBusinessStatusService;
import com.clickless.workflow.service.IProcessInstanceService;
import com.clickless.workflow.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Api
@Slf4j
@RestController
@RequestMapping("/instance")
public class ProcessInstanceController {

    @Autowired
    private IProcessInstanceService processInstanceService;

    @Autowired
    private IBusinessStatusService businessStatusService;

    @Autowired
    private RuntimeService runtimeService;

    @ApiOperation("提交申请，启动流程实例")
    @PostMapping("/start")
    public AjaxResult start(@RequestBody StartREQ req) {
        try {
            processInstanceService.startProcess(req);
            return AjaxResult.success();
        } catch (WorkflowException e) {
            e.printStackTrace();
            return AjaxResult.error("提交失败:" + e.getMessage());
        }

    }

    @ApiOperation("返回申请")
    @DeleteMapping("/cancel/apply")
    public AjaxResult cancelApply(@RequestParam String businessKey,
                                  @RequestParam String procInstId,
                                  @RequestParam(defaultValue = "返回成功") String message) {
        processInstanceService.cancel(businessKey, procInstId, message);
        return AjaxResult.success();
    }

    @ApiOperation("通过流程实例id获取申请表单组件名")
    @GetMapping("/form/name/{procInstId}")
    public AjaxResult getFormName(@PathVariable String procInstId) {
        return AjaxResult.success("查询成功", processInstanceService.getFormNameByProcInstId(procInstId));
    }

    @ApiOperation("通过流程实例id获取任务办理历史记录")
    @GetMapping("/history/list") // ?procInstId=xxx
    public AjaxResult getHistoryInfoList(@RequestParam String procInstId) {
        return AjaxResult.success(processInstanceService.getHistoryInfoList(procInstId));
    }

    @ApiOperation("通过流程实例id获取历史流程图")
    @GetMapping("/history/image") // ?procInstId=xxx
    public void getHistoryProcessImage(@RequestParam String procInstId,
                                       HttpServletResponse response) {
        processInstanceService.getHistoryProcessImage(procInstId, response);
    }

    @ApiOperation("查询正在运行中的流程实例")
    @PostMapping("/list/running")
    public AjaxResult getProcInstRunning(@RequestBody ProcInstREQ req) {
        return AjaxResult.success(processInstanceService.getProcInstRunning(req));
    }

    @ApiOperation("挂起或激活流程实例")
    @PutMapping("/state/{procInstId}")
    public AjaxResult updateProcInstState(@PathVariable String procInstId) {
        // 1. 查询指定流程实例的数据
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(procInstId)
                .singleResult();

        // 2. 判断当前流程实例的状态
        if (processInstance.isSuspended()) {
            // 如果是已挂起，则更新为激活状态
            runtimeService.activateProcessInstanceById(procInstId);
        } else {
            // 如果是已激活，则更新为挂起状态
            runtimeService.suspendProcessInstanceById(procInstId);
        }

        return AjaxResult.success();
    }


    @ApiOperation("作废流程实例，不会删除历史记录")
    @DeleteMapping("/{procInstId}")
    public AjaxResult deleteProcInst(@PathVariable String procInstId) {
        // 1. 查询流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(procInstId).singleResult();

        // 2. 删除流程实例
        runtimeService.deleteProcessInstance(procInstId,
                UserUtils.getUsername() + "作废了当前流程申请");

        // 3. 更新业务状态
        businessStatusService.updateState(processInstance.getBusinessKey(),
                BusinessStatusEnum.INVALID);

        return AjaxResult.success();
    }

    @ApiOperation("查询已结束的流程实例")
    @PostMapping("/list/finish")
    public AjaxResult getProcInstFinish(@RequestBody ProcInstREQ req) {
        return AjaxResult.success(processInstanceService.getProcInstFinish(req));
    }

    @ApiOperation("删除已结束流程实例和历史记录")
    @DeleteMapping("/history/{procInstId}")
    public AjaxResult deleteProcInstAndHistory(@PathVariable String procInstId) {
        processInstanceService.deleteProcInstAndHistory(procInstId);
        return AjaxResult.success();
    }


}
