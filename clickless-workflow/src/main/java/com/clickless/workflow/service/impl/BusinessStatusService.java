package com.clickless.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clickless.workflow.domain.BusinessStatus;
import com.clickless.workflow.enums.BusinessStatusEnum;
import com.clickless.workflow.mapper.BusinessStatusMapper;
import com.clickless.workflow.service.IBusinessStatusService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BusinessStatusService extends ServiceImpl<BusinessStatusMapper, BusinessStatus>
        implements IBusinessStatusService {


    @Override
    public int add(String businessKey) {
        BusinessStatus bs = new BusinessStatus();
        // 待提交
        bs.setStatus(BusinessStatusEnum.WAIT.getCode());
        bs.setBusinessKey(businessKey);
        return baseMapper.insert(bs);
    }

    @Override
    public void updateState(String businessKey, BusinessStatusEnum statusEnum, String procInstId) {
        // 1. 查询当前数据
        BusinessStatus bs = baseMapper.selectById(businessKey);
        // 2. 设置状态值
        bs.setStatus(statusEnum.getCode());
        bs.setUpdateTime(new Date());

        // 只要判断不为null,就更新，因为后面有个地方传递“”
        if (procInstId != null) {
            bs.setProcessInstanceId(procInstId);
        }

        // 3. 更新操作
        baseMapper.updateById(bs);
    }

    @Override
    public void updateState(String businessKey, BusinessStatusEnum statusEnum) {
        updateState(businessKey, statusEnum, null);
    }

}
