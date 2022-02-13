package com.clickless.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clickless.workflow.domain.Leave;
import com.clickless.workflow.exception.WorkflowException;
import com.clickless.workflow.req.LeaveREQ;

import java.util.List;

public interface ILeaveService extends IService<Leave> {

    void add(Leave leave);

    /**
     * 查询请假列表
     *
     * @param req
     * @return
     */
    List<Leave> list(LeaveREQ req);

    void update(Leave leave) throws WorkflowException;
}
