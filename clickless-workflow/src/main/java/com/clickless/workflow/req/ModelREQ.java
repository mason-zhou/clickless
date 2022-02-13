package com.clickless.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询模型条件请求类")
public class ModelREQ extends BaseRequest {

    @ApiModelProperty("模型名称")
    private String name;

    @ApiModelProperty("模型标识key")
    private String key;

}
