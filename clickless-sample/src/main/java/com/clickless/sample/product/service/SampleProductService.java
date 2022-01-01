package com.clickless.sample.product.service;

import java.util.List;
import com.clickless.sample.product.domain.SampleProduct;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品数据Service接口
 * 
 * @author clickless
 * @date 2021-11-26
 */
public interface SampleProductService extends IService<SampleProduct>
{
    /**
     * 查询商品数据
     * 
     * @param id 商品数据主键
     * @return 商品数据
     */
    SampleProduct selectSampleProductById(Long id);

    /**
     * 查询商品数据列表
     * 
     * @param sampleProduct 商品数据
     * @return 商品数据集合
     */
    List<SampleProduct> selectSampleProductList(SampleProduct sampleProduct);

    /**
     * 新增商品数据
     * 
     * @param sampleProduct 商品数据
     * @return 结果
     */
    int insertSampleProduct(SampleProduct sampleProduct);

    /**
     * 修改商品数据
     * 
     * @param sampleProduct 商品数据
     * @return 结果
     */
    int updateSampleProduct(SampleProduct sampleProduct);

    /**
     * 批量删除商品数据
     * 
     * @param ids 需要删除的商品数据主键集合
     * @return 结果
     */
    int deleteSampleProductByIds(Long[] ids);

    /**
     * 删除商品数据信息
     * 
     * @param id 商品数据主键
     * @return 结果
     */
    int deleteSampleProductById(Long id);

    /**
     * 查询商品数据 字段枚举值
     * @param sampleProduct 商品数据
     * @return 字段枚举值
     */
    List<Object> selectSampleProductFieldEnumsList(SampleProduct sampleProduct);
}
