package com.clickless.system.pagemodel.domain;

import com.clickless.common.annotation.Excel;
import com.clickless.common.annotation.pagemodel.PageModel;
import com.clickless.common.annotation.pagemodel.PageModelField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.clickless.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 用户选择字段对象 sys_user_field
 * 
 * @author clickless
 * @date 2021-11-26
 */
@PageModel(name = "用户选择字段", dbTableName = "sys_user_field")
public class SysUserField extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 代理主键 */
    @PageModelField(name = "代理主键", dbColumnName = "id", sort = 1)
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    @PageModelField(name = "用户ID", dbColumnName = "user_id", sort = 2)
    private Long userId;

    /** 模型编码 */
    @Excel(name = "模型编码")
    @PageModelField(name = "模型编码", dbColumnName = "model_key", sort = 3)
    private String modelKey;

    /** 字段编码 */
    @Excel(name = "字段编码")
    @PageModelField(name = "字段编码", dbColumnName = "field_key", sort = 4)
    private String fieldKey;

    /** 字段排序 */
    @Excel(name = "字段排序")
    @PageModelField(name = "字段排序", dbColumnName = "field_sort", sort = 5)
    private Integer fieldSort;

    /** 创建者 */
    @PageModelField(name = "创建者", dbColumnName = "create_by", sort = 6)
    private String createBy;

    /** 创建时间 */
    @PageModelField(name = "创建时间", dbColumnName = "create_time", sort = 7)
    private Date createTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setModelKey(String modelKey) 
    {
        this.modelKey = modelKey;
    }

    public String getModelKey() 
    {
        return modelKey;
    }
    public void setFieldKey(String fieldKey) 
    {
        this.fieldKey = fieldKey;
    }

    public String getFieldKey() 
    {
        return fieldKey;
    }
    public void setFieldSort(Integer fieldSort) 
    {
        this.fieldSort = fieldSort;
    }

    public Integer getFieldSort() 
    {
        return fieldSort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("modelKey", getModelKey())
            .append("fieldKey", getFieldKey())
            .append("fieldSort", getFieldSort())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
