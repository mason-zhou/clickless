package com.clickless.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clickless.workflow.domain.Leave;
import com.clickless.workflow.req.LeaveREQ;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface LeaveMapper extends BaseMapper<Leave> {

    /**
     * 查询请假申请列表数据
     *
     * @return
     */
    List<Leave> getLeaveAndStatusList(@Param("req") LeaveREQ req);

}
