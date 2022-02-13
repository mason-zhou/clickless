package com.clickless.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clickless.workflow.domain.BusinessStatus;
import com.clickless.workflow.enums.BusinessStatusEnum;

public interface IBusinessStatusService extends IService<BusinessStatus> {

    /**
     * 新增数据：状态 WAIT(1, "待提交")
     *
     * @param businessKey
     * @return
     */
    int add(String businessKey);

    /**
     * 更新业务状态
     *
     * @param businessKey 业务id
     * @param statusEnum  状态值
     * @param procInstId  流程实例id
     * @return
     */
    void updateState(String businessKey, BusinessStatusEnum statusEnum, String procInstId);

    /**
     * 更新业务状态
     *
     * @param businessKey 业务id
     * @param statusEnum  状态值
     * @return
     */
    void updateState(String businessKey, BusinessStatusEnum statusEnum);

}
