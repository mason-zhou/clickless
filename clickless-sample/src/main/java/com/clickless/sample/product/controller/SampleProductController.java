package com.clickless.sample.product.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.clickless.common.annotation.Log;
import com.clickless.common.core.controller.BaseController;
import com.clickless.common.core.domain.AjaxResult;
import com.clickless.common.enums.BusinessType;
import com.clickless.sample.product.domain.SampleProduct;
import com.clickless.sample.product.service.SampleProductService;
import com.clickless.common.utils.poi.ExcelUtil;
import com.clickless.common.core.page.TableDataInfo;

/**
 * 商品数据Controller
 * 
 * @author clickless
 * @date 2021-11-26
 */
@RestController
@RequestMapping("/sample/product")
public class SampleProductController extends BaseController
{
    @Autowired
    private SampleProductService sampleProductService;

    /**
     * 查询商品数据列表
     */
    @PreAuthorize("@ss.hasPermi('sample:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(SampleProduct sampleProduct)
    {
        startPage();
        List<SampleProduct> list = sampleProductService.selectSampleProductList(sampleProduct);
        return getDataTable(list);
    }

    /**
     * 查询商品数据 字段枚举值
     */
    @PreAuthorize("@ss.hasPermi('sample:product:list')")
    @GetMapping("/field-enums")
    public AjaxResult getFieldEnums(SampleProduct sampleProduct) {
        startPage();
        return AjaxResult.success(sampleProductService.selectSampleProductFieldEnumsList(sampleProduct));
    }

    /**
     * 导出商品数据列表
     */
    @PreAuthorize("@ss.hasPermi('sample:product:export')")
    @Log(title = "商品数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SampleProduct sampleProduct)
    {
        List<SampleProduct> list = sampleProductService.selectSampleProductList(sampleProduct);
        ExcelUtil<SampleProduct> util = new ExcelUtil<SampleProduct>(SampleProduct.class);
        util.exportExcel(response, list, "商品数据数据");
    }

    /**
     * 获取商品数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('sample:product:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sampleProductService.selectSampleProductById(id));
    }

    /**
     * 新增商品数据
     */
    @PreAuthorize("@ss.hasPermi('sample:product:add')")
    @Log(title = "商品数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SampleProduct sampleProduct)
    {
        return toAjax(sampleProductService.insertSampleProduct(sampleProduct));
    }

    /**
     * 修改商品数据
     */
    @PreAuthorize("@ss.hasPermi('sample:product:edit')")
    @Log(title = "商品数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SampleProduct sampleProduct)
    {
        return toAjax(sampleProductService.updateSampleProduct(sampleProduct));
    }

    /**
     * 删除商品数据
     */
    @PreAuthorize("@ss.hasPermi('sample:product:remove')")
    @Log(title = "商品数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sampleProductService.deleteSampleProductByIds(ids));
    }
}
