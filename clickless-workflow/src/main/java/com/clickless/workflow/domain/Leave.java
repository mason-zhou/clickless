package com.clickless.workflow.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.clickless.workflow.enums.BusinessStatusEnum;
import com.clickless.workflow.enums.LeaveTypeEnum;
import com.clickless.workflow.util.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("请假申请实体")
@TableName("wkfl_biz_leave")
public class Leave implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("申请人用户名")
    private String username;

    @ApiModelProperty("请假类型：1病假，2事假，3年假，4婚假，5产假，6丧假，7探亲，8调休，9其他")
    private Integer leaveType;

    /**
     * 用于前端展示名称
     */
    public String getLeaveTypeStr() {
        if (this.leaveType == null) {
            return "";
        }
        return LeaveTypeEnum.getEnumByCode(this.leaveType).getDesc();
    }


    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("请假事由")
    private String leaveReason;

    @ApiModelProperty("请假开始时间")
    private Date startDate;

    @ApiModelProperty("请假结束时间")
    private Date endDate;

    @ApiModelProperty("请假时长，单位：天")
    private Double duration;

    @ApiModelProperty("应急工作委托人")
    private String principal;

    @ApiModelProperty("休息期间联系人电话")
    private String contactPhone;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @TableField(exist = false)
    @ApiModelProperty("流程实例id")
    private String processInstanceId;

    @TableField(exist = false)
    @ApiModelProperty("流程状态")
    private Integer status;

    public String getStatusStr() {
        if (this.status == null) {
            return "";
        }
        return BusinessStatusEnum.getEnumByCode(this.status).getDesc();
    }

    public String getStartDateStr() {
        if (startDate == null) {
            return "";
        }
        return DateUtils.format(startDate);
    }

    public String getEndDateStr() {
        if (endDate == null) {
            return "";
        }
        return DateUtils.format(endDate);
    }

    public String getCreateDateStr() {
        if (createTime == null) {
            return "";
        }
        return DateUtils.format(createTime);
    }

    public String getUpdateDateStr() {
        if (updateTime == null) {
            return "";
        }
        return DateUtils.format(updateTime);
    }

}
