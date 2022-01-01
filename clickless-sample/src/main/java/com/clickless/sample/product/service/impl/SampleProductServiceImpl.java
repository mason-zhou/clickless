package com.clickless.sample.product.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clickless.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.clickless.sample.product.mapper.SampleProductMapper;
import com.clickless.sample.product.domain.SampleProduct;
import com.clickless.sample.product.service.SampleProductService;

/**
 * 商品数据Service业务层处理
 * 
 * @author clickless
 * @date 2021-11-26
 */
@Service
public class SampleProductServiceImpl extends ServiceImpl<SampleProductMapper, SampleProduct> implements SampleProductService
{
    @Autowired
    private SampleProductMapper sampleProductMapper;

    /**
     * 查询商品数据
     * 
     * @param id 商品数据主键
     * @return 商品数据
     */
    @Override
    public SampleProduct selectSampleProductById(Long id)
    {
        return sampleProductMapper.selectSampleProductById(id);
    }

    /**
     * 查询商品数据列表
     * 
     * @param sampleProduct 商品数据
     * @return 商品数据
     */
    @Override
    public List<SampleProduct> selectSampleProductList(SampleProduct sampleProduct)
    {
        return sampleProductMapper.selectSampleProductList(sampleProduct);
    }

    /**
     * 新增商品数据
     * 
     * @param sampleProduct 商品数据
     * @return 结果
     */
    @Override
    public int insertSampleProduct(SampleProduct sampleProduct)
    {
        sampleProduct.setCreateTime(DateUtils.getNowDate());
        return sampleProductMapper.insertSampleProduct(sampleProduct);
    }

    /**
     * 修改商品数据
     * 
     * @param sampleProduct 商品数据
     * @return 结果
     */
    @Override
    public int updateSampleProduct(SampleProduct sampleProduct)
    {
        return sampleProductMapper.updateSampleProduct(sampleProduct);
    }

    /**
     * 批量删除商品数据
     * 
     * @param ids 需要删除的商品数据主键
     * @return 结果
     */
    @Override
    public int deleteSampleProductByIds(Long[] ids)
    {
        return sampleProductMapper.deleteSampleProductByIds(ids);
    }

    /**
     * 删除商品数据信息
     * 
     * @param id 商品数据主键
     * @return 结果
     */
    @Override
    public int deleteSampleProductById(Long id)
    {
        return sampleProductMapper.deleteSampleProductById(id);
    }

    /**
     * 查询商品数据 字段枚举值
     * @param sampleProduct 商品数据
     * @return 字段枚举值
     */
    @Override
    public List<Object> selectSampleProductFieldEnumsList(SampleProduct sampleProduct){
        return sampleProductMapper.selectSampleProductDistinctColumnList(sampleProduct);
    }

}
