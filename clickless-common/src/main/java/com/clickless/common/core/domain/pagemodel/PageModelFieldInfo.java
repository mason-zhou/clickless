package com.clickless.common.core.domain.pagemodel;

import lombok.Data;

import java.io.Serializable;

/**
 * 页面模型字段
 * <p>
 *
 * @author Mason Zhou
 * @version 1.0.0
 * @date 2021/11/22
 */
@Data
public class PageModelFieldInfo implements Serializable {

    private static final long serialVersionUID = 5359709211352400087L;
    /**
     * 字段编码
     */
    private String key;
    /**
     * 字段显示名
     */
    private String name;
    /**
     * 数据库列名
     */
    private String dbColumnName;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 是否主键
     */
    private Boolean isPk;
}
