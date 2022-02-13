package com.clickless.workflow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clickless.workflow.domain.Loan;
import com.clickless.workflow.exception.WorkflowException;
import com.clickless.workflow.mapper.LoanMapper;
import com.clickless.workflow.req.LoanREQ;
import com.clickless.workflow.service.IBusinessStatusService;
import com.clickless.workflow.service.ILoanService;
import com.clickless.workflow.util.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoanService extends ServiceImpl<LoanMapper, Loan> implements ILoanService {

    @Autowired
    IBusinessStatusService businessStatusService;

    @Override
    public void add(Loan loan) {
        // 1. 新增借款信息
        // 当前登录用户即为申请人
        loan.setUserId(UserUtils.getUsername());
        int size = baseMapper.insert(loan);
        // 2. 新增借款业务状态：待提交
        if (size == 1) {
            businessStatusService.add(loan.getId());
        }
    }

    @Override
    public List<Loan> list(LoanREQ req) {
        if (StringUtils.isEmpty(req.getUsername())) {
            req.setUsername(UserUtils.getUsername());
        }
        List<Loan> page = baseMapper.getLoanAndStatusList(req);
        return page;
    }

    @Override
    public void update(Loan loan) throws WorkflowException {
        if (loan == null || StringUtils.isEmpty(loan.getId())) {
            throw new WorkflowException("数据不合法");
        }
        // 查询原数据
        Loan entity = baseMapper.selectById(loan.getId());
        // 拷贝新数据
        BeanUtils.copyProperties(loan, entity);
        entity.setUpdateTime(new Date());
        baseMapper.updateById(entity);
    }

}
