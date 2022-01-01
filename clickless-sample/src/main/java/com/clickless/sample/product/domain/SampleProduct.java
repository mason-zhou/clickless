package com.clickless.sample.product.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.clickless.common.annotation.Excel;
import com.clickless.common.annotation.pagemodel.PageModel;
import com.clickless.common.annotation.pagemodel.PageModelField;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.clickless.common.core.domain.BaseEntity;

/**
 * 商品数据对象 sample_product
 * 
 * @author clickless
 * @date 2021-11-26
 */
@PageModel(name = "商品数据", dbTableName = "sample_product")
public class SampleProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 代理主键 */
    @PageModelField(name = "代理主键", dbColumnName = "id", sort = 1)
    private Long id;

    /** 商品编号 */
    @Excel(name = "商品编号")
    @PageModelField(name = "商品编号", dbColumnName = "product_no", sort = 2)
    private String productNo;

    /** 商品名称 */
    @Excel(name = "商品名称")
    @PageModelField(name = "商品名称", dbColumnName = "product_name", sort = 3)
    private String productName;

    /** 商品重量 */
    @Excel(name = "商品重量")
    @PageModelField(name = "商品重量", dbColumnName = "weight", sort = 4)
    private Long weight;

    /** 上市日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上市日期", width = 30, dateFormat = "yyyy-MM-dd")
    @PageModelField(name = "上市日期", dbColumnName = "launch_date", sort = 5)
    private Date launchDate;

    /** 适用性别（1男 0女） */
    @Excel(name = "适用性别", readConverterExp = "1=男,0=女", dictType = "sys_user_sex")
    @PageModelField(name = "适用性别", dbColumnName = "sex", dictType = "sys_user_sex", sort = 6)
    private Integer sex;

    /** 创建时间 */
    @PageModelField(name = "创建时间", dbColumnName = "create_time", sort = 7)
    private Date createTime;

    /** 是否禁用（0正常 1禁用） */
    @Excel(name = "是否禁用", readConverterExp = "0=正常,1=禁用", dictType = "sys_normal_disable")
    @PageModelField(name = "是否禁用", dbColumnName = "is_disabled", dictType = "sys_normal_disable", sort = 8)
    private Integer isDisabled;

    /** 逻辑删除（0正常 1删除） */
    @PageModelField(name = "逻辑删除", dbColumnName = "is_deleted", dictType = "sys_yes_no", sort = 9)
    private Integer isDeleted;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProductNo(String productNo) 
    {
        this.productNo = productNo;
    }

    public String getProductNo() 
    {
        return productNo;
    }
    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }
    public void setWeight(Long weight) 
    {
        this.weight = weight;
    }

    public Long getWeight() 
    {
        return weight;
    }
    public void setLaunchDate(Date launchDate) 
    {
        this.launchDate = launchDate;
    }

    public Date getLaunchDate() 
    {
        return launchDate;
    }
    public void setSex(Integer sex) 
    {
        this.sex = sex;
    }

    public Integer getSex() 
    {
        return sex;
    }
    public void setIsDisabled(Integer isDisabled) 
    {
        this.isDisabled = isDisabled;
    }

    public Integer getIsDisabled() 
    {
        return isDisabled;
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
            .append("productNo", getProductNo())
            .append("productName", getProductName())
            .append("weight", getWeight())
            .append("launchDate", getLaunchDate())
            .append("sex", getSex())
            .append("createTime", getCreateTime())
            .append("isDisabled", getIsDisabled())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
