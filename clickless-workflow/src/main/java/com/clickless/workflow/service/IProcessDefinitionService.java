package com.clickless.workflow.service;

import com.clickless.workflow.req.ProcDefiREQ;

import java.util.Map;

public interface IProcessDefinitionService {

    /**
     * 条件分页查询流程定义列表数据
     *
     * @param req
     * @return
     */
    Map<String, Object> getProcDefiList(ProcDefiREQ req);

    /**
     * 更新流程状态：激活或者挂起
     *
     * @param ProcDefiId
     * @return
     */
    void updateProcDefState(String ProcDefiId);

    /**
     * 删除流程定义
     *
     * @param deploymentId 部署id
     * @param key          流程定义key
     * @return
     */
    void deleteDeployment(String deploymentId, String key);


}
