package com.clickless.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询借款列表条件")
public class LoanREQ {

    @ApiModelProperty("用途")
    private String purpose;

    @ApiModelProperty("业务状态")
    private Integer status;

    @ApiModelProperty("所属的用户名")
    private String username;
}
