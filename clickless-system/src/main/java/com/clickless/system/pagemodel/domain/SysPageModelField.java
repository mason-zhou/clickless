package com.clickless.system.pagemodel.domain;

import com.clickless.common.annotation.Excel;
import com.clickless.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 页面模型字段对象 sys_page_model_field
 * 
 * @author clickless
 * @date 2021-11-26
 */
public class SysPageModelField extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 代理主键 */
    private Long id;

    /** 模型ID */
    @Excel(name = "模型ID")
    private Long modelId;

    /** 字段编码 */
    @Excel(name = "字段编码")
    private String fieldKey;

    /** 字段名称 */
    @Excel(name = "字段名称")
    private String fieldName;

    /** 数据库列名 */
    @Excel(name = "数据库列名")
    private String dbColumnName;

    /** 数据库列名 */
    @Excel(name = "数据库列名")
    private String dictType;

    /** 字段排序 */
    @Excel(name = "字段排序")
    private Integer fieldSort;

    /** 删除标记（0正常 1删除） */
    private Integer isDeleted;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setModelId(Long modelId) 
    {
        this.modelId = modelId;
    }

    public Long getModelId() 
    {
        return modelId;
    }
    public void setFieldKey(String fieldKey) 
    {
        this.fieldKey = fieldKey;
    }

    public String getFieldKey() 
    {
        return fieldKey;
    }
    public void setFieldName(String fieldName) 
    {
        this.fieldName = fieldName;
    }

    public String getFieldName() 
    {
        return fieldName;
    }
    public void setDbColumnName(String dbColumnName) 
    {
        this.dbColumnName = dbColumnName;
    }

    public String getDbColumnName() 
    {
        return dbColumnName;
    }
    public void setDictType(String dictType) 
    {
        this.dictType = dictType;
    }

    public String getDictType() 
    {
        return dictType;
    }
    public void setFieldSort(Integer fieldSort) 
    {
        this.fieldSort = fieldSort;
    }

    public Integer getFieldSort() 
    {
        return fieldSort;
    }
    public void setIsDeleted(Integer isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() 
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("modelId", getModelId())
            .append("fieldKey", getFieldKey())
            .append("fieldName", getFieldName())
            .append("dbColumnName", getDbColumnName())
            .append("dictType", getDictType())
            .append("fieldSort", getFieldSort())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
