package com.clickless.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("新增模型请求类")
public class ModelAddREQ extends ModelREQ {

    @ApiModelProperty("描述")
    private String description;

}
