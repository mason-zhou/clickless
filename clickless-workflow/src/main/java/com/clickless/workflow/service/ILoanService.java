package com.clickless.workflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.clickless.workflow.domain.Loan;
import com.clickless.workflow.exception.WorkflowException;
import com.clickless.workflow.req.LoanREQ;

import java.util.List;

public interface ILoanService extends IService<Loan> {

    void add(Loan loan);

    /**
     * 查询借款列表
     *
     * @param req
     * @return
     */
    List<Loan> list(LoanREQ req);

    /**
     * 更新借款申请
     * @param loan
     * @throws WorkflowException
     */
    void update(Loan loan) throws WorkflowException;
}
