package com.clickless.system.pagemodel.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 用户选择字段 前台数据传输表单对象
 * <p>
 *
 * @author Mason Zhou
 * @version 1.0.0
 * @date 2021/11/22
 */
@Data
public class SelectedFieldForm {
    @NotBlank(message = " 模型编码modelKey不允许为空")
    private String modelKey;
    private List<String> fieldKeys;
}
