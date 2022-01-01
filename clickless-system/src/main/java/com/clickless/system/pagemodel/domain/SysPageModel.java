package com.clickless.system.pagemodel.domain;

import java.util.Date;
import java.util.List;
import com.clickless.common.annotation.Excel;
import com.clickless.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.clickless.common.annotation.pagemodel.PageModel;
import com.clickless.common.annotation.pagemodel.PageModelField;

/**
 * 页面模型对象 sys_page_model
 * 
 * @author clickless
 * @date 2021-11-26
 */
@PageModel(name = "页面模型", dbTableName = "sys_page_model")
public class SysPageModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 代理主键 */
    @Excel(name = "代理主键")
    @PageModelField(name = "代理主键", dbColumnName = "id", sort = 1)
    private Long id;

    /** 模型编码 */
    @Excel(name = "模型编码")
    @PageModelField(name = "模型编码", dbColumnName = "model_key", sort = 2)
    private String modelKey;

    /** 模型名称 */
    @Excel(name = "模型名称")
    @PageModelField(name = "模型名称", dbColumnName = "model_name", sort = 3)
    private String modelName;

    /** 数据库表名 */
    @Excel(name = "数据库表名")
    @PageModelField(name = "数据库表名", dbColumnName = "db_table_name", sort = 4)
    private String dbTableName;

    /** 创建者 */
    @PageModelField(name = "创建者", dbColumnName = "create_by", sort = 5)
    private String createBy;

    /** 创建时间 */
    @PageModelField(name = "创建时间", dbColumnName = "create_time", sort = 6)
    private Date createTime;

    /** 更新者 */
    @PageModelField(name = "更新者", dbColumnName = "update_by", sort = 7)
    private String updateBy;

    /** 更新时间 */
    @PageModelField(name = "更新时间", dbColumnName = "update_time", sort = 8)
    private Date updateTime;

    /** 删除标记（0正常 1删除） */
    @PageModelField(name = "删除标记", dbColumnName = "is_deleted", dictType = "sys_yes_no", sort = 9)
    private Integer isDeleted;

    /** 页面模型字段信息 */
    private List<SysPageModelField> sysPageModelFieldList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setModelKey(String modelKey) 
    {
        this.modelKey = modelKey;
    }

    public String getModelKey() 
    {
        return modelKey;
    }
    public void setModelName(String modelName) 
    {
        this.modelName = modelName;
    }

    public String getModelName() 
    {
        return modelName;
    }
    public void setDbTableName(String dbTableName) 
    {
        this.dbTableName = dbTableName;
    }

    public String getDbTableName() 
    {
        return dbTableName;
    }
    public void setIsDeleted(Integer isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() 
    {
        return isDeleted;
    }

    public List<SysPageModelField> getSysPageModelFieldList()
    {
        return sysPageModelFieldList;
    }

    public void setSysPageModelFieldList(List<SysPageModelField> sysPageModelFieldList)
    {
        this.sysPageModelFieldList = sysPageModelFieldList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("modelKey", getModelKey())
            .append("modelName", getModelName())
            .append("dbTableName", getDbTableName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .append("sysPageModelFieldList", getSysPageModelFieldList())
            .toString();
    }
}
