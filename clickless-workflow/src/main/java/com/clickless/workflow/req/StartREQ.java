package com.clickless.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ApiModel("启动流程实例请求类（提交申请)")
public class StartREQ implements Serializable {

    @ApiModelProperty("业务列表页面的前端路由名")
    private String businessRoute;

    @ApiModelProperty("业务唯一主键")
    private String businessKey;

    @ApiModelProperty("节点任务办理人一位或多位")
    private List<String> assignees;

    @ApiModelProperty("流程变量，前端会提交一个元素{'entity': {业务详情数据对象}}")
    private Map<String, Object> variables;

    public Map<String, Object> getVariables() {
        return variables == null ? new HashMap<>() : variables;
    }

}
