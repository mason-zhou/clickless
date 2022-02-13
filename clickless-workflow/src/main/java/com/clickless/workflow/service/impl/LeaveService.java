package com.clickless.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clickless.workflow.domain.Leave;
import com.clickless.workflow.exception.WorkflowException;
import com.clickless.workflow.mapper.LeaveMapper;
import com.clickless.workflow.req.LeaveREQ;
import com.clickless.workflow.service.IBusinessStatusService;
import com.clickless.workflow.service.ILeaveService;
import com.clickless.workflow.util.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LeaveService extends ServiceImpl<LeaveMapper, Leave> implements ILeaveService {

    @Autowired
    IBusinessStatusService businessStatusService;

    @Override
    public void add(Leave leave) {
        // 1. 新增请假信息
        // 当前登录用户即为申请人
        leave.setUsername(UserUtils.getUsername());
        int size = baseMapper.insert(leave);
        // 2. 新增请假业务状态：待提交
        if (size == 1) {
            businessStatusService.add(leave.getId());
        }
    }

    @Override
    public List<Leave> list(LeaveREQ req) {
        if (StringUtils.isEmpty(req.getUsername())) {
            req.setUsername(UserUtils.getUsername());
        }
        List<Leave> page = baseMapper.getLeaveAndStatusList(req);
        return page;
    }

    @Override
    public void update(Leave leave) throws WorkflowException {
        if (leave == null || StringUtils.isEmpty(leave.getId())) {
            throw new WorkflowException("数据不合法");
        }
        // 查询原数据
        Leave entity = baseMapper.selectById(leave.getId());
        // 拷贝新数据
        BeanUtils.copyProperties(leave, entity);
        entity.setUpdateTime(new Date());
        baseMapper.updateById(entity);
    }

}
