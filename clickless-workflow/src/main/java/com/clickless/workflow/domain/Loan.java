package com.clickless.workflow.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.clickless.workflow.enums.BusinessStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("借款申请实体")
@TableName("wkfl_biz_loan")
public class Loan implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("申请人ID")
    private String userId;

    @ApiModelProperty("申请人昵称")
    private String nickName;

    @ApiModelProperty("借款金额")
    private Double money;

    @ApiModelProperty("借款用途")
    private String purpose;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("借款日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date loanDate;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

}
