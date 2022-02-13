package com.clickless.workflow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@ApiModel("完成任务请求类")
public class TaskCompleteREQ implements Serializable {

    @ApiModelProperty("任务ID")
    private String taskId;

    @ApiModelProperty("审批意见")
    private String message;

    @ApiModelProperty("下一个节点审批，key: 节点id, value：审批人集合")
    private Map<String, List<String>> assigneeMap;

    public String getMessage() {
        return StringUtils.isEmpty(message) ? "审批通过" : message;
    }

    /**
     * 通过节点id获取审批人集合
     *
     * @param key
     * @return
     */
    public String[] getAssignees(String key) {
        if (assigneeMap == null) {
            return null;
        }
        List<String> assignees = assigneeMap.get(key);
        return assignees.toArray(new String[assignees.size()]);
    }

}
