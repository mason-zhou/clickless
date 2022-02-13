package com.clickless.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("流程实例条件请求类")
@Data
public class ProcInstREQ extends BaseRequest{

    @ApiModelProperty("流程名称")
    private String processName;

    @ApiModelProperty("任务发起人")
    private String proposer;

}
