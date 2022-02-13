package com.clickless.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clickless.workflow.domain.ProcessConfig;

public interface IProcessConfigService extends IService<ProcessConfig> {

    /**
     * 查询流程配置数据
     *
     * @param processKey 流程定义key
     * @return
     */
    ProcessConfig getByProcessKey(String processKey);

    /**
     * 删除流程配置数据
     *
     * @param processKey 流程定义key
     * @return
     */
    void deleteByProcessKey(String processKey);

    /**
     * 通过业务路由名查询流程定义配置信息（目的查询获取流程定义key)
     *
     * @param businessRoute
     * @return
     */
    ProcessConfig getByBusinessRoute(String businessRoute);

}
