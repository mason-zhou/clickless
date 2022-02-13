package com.clickless.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clickless.workflow.domain.Loan;
import com.clickless.workflow.req.LoanREQ;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoanMapper extends BaseMapper<Loan> {
    /**
     * 查询借款申请列表数据
     * @param req
     * @return
     */
    List<Loan> getLoanAndStatusList(@Param("req") LoanREQ req);

}
