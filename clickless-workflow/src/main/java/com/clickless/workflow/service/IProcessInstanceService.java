package com.clickless.workflow.service;


import com.clickless.workflow.exception.WorkflowException;
import com.clickless.workflow.req.ProcInstREQ;
import com.clickless.workflow.req.StartREQ;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IProcessInstanceService {

    /**
     * 提交申请启动实例
     *
     * @param req
     * @return
     */
    void startProcess(StartREQ req) throws WorkflowException;

    /**
     * 撤回申请
     *
     * @param businessKey
     * @param procInstId
     * @param message
     * @return
     */
    void cancel(String businessKey, String procInstId, String message);

    /**
     * 通过流程实例id查询流程变量formName值
     *
     * @param procInstId
     * @return
     */
    Object getFormNameByProcInstId(String procInstId);

    List<Map<String, Object>> getHistoryInfoList(String procInstId);

    /**
     * 查询流程实例审批历史流程图
     *
     * @param prodInstId
     * @param response
     */
    void getHistoryProcessImage(String prodInstId, HttpServletResponse response);

    /**
     * 查询正在运行的流程实例
     *
     * @param req
     * @return
     */
    Map<String, Object> getProcInstRunning(ProcInstREQ req);

    /**
     * 查询已结束的流程实例
     *
     * @param req
     * @return
     */
    Map<String, Object> getProcInstFinish(ProcInstREQ req);

    /**
     * 删除流程实例与历史记录
     *
     * @param procInstId
     * @return
     */
    void deleteProcInstAndHistory(String procInstId);
}
