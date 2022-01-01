package com.clickless.common.core.domain.pagemodel;

import com.clickless.common.exception.pagemodel.PageModelNotFoundException;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 页面模型
 * <p>
 *
 * @author Mason Zhou
 * @version 1.0.0
 * @date 2021/11/22
 */
@Data
public class PageModelInfo implements Serializable {

    private static final long serialVersionUID = 4359709211352400087L;

    /**
     * 模型编码
     */
    private String key;
    /**
     * 模型显示名
     */
    private String name;
    /**
     * 数据库表名
     */
    private String dbTableName;
    /**
     * 模型字段集合
     */
    private List<PageModelFieldInfo> fields;

    public PageModelFieldInfo getFieldByKey(String fieldKey) {
        for (PageModelFieldInfo field : fields) {
            if (fieldKey.equals(field.getKey())) {
                return field;
            }
        }
        throw new PageModelNotFoundException("模型key:" + key + ",未查询到字段fieldKey:" + fieldKey);
    }
}
