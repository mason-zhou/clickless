package com.clickless.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.clickless.common.core.domain.pagemodel.PageModelFieldInfo;
import com.clickless.common.core.domain.pagemodel.PageModelInfo;
import com.clickless.system.pagemodel.model.SelectedFieldForm;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.clickless.common.annotation.Log;
import com.clickless.common.core.controller.BaseController;
import com.clickless.common.core.domain.AjaxResult;
import com.clickless.common.enums.BusinessType;
import com.clickless.system.pagemodel.domain.SysPageModel;
import com.clickless.system.pagemodel.service.SysPageModelService;
import com.clickless.common.utils.poi.ExcelUtil;
import com.clickless.common.core.page.TableDataInfo;

/**
 * 页面模型Controller
 *
 * @author clickless
 * @date 2021-11-26
 */
@RestController
@RequestMapping("/system/page-model")
public class SysPageModelController extends BaseController {
    @Autowired
    private SysPageModelService sysPageModelService;

    /**
     * 查询页面模型列表
     */
    @PreAuthorize("@ss.hasPermi('system:pageModel:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPageModel sysPageModel) {
        startPage();
        List<SysPageModel> list = sysPageModelService.selectSysPageModelList(sysPageModel);
        return getDataTable(list);
    }

    /**
     * 查询页面模型 字段枚举值
     */
    @PreAuthorize("@ss.hasPermi('system:pageModel:list')")
    @GetMapping("/field-enums")
    public AjaxResult getFieldEnums(SysPageModel sysPageModel) {
        startPage();
        return AjaxResult.success(sysPageModelService.selectSysPageModelFieldEnumsList(sysPageModel));
    }

    /**
     * 导出页面模型列表
     */
    @PreAuthorize("@ss.hasPermi('system:pageModel:export')")
    @Log(title = "页面模型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPageModel sysPageModel) {
        List<SysPageModel> list = sysPageModelService.selectSysPageModelList(sysPageModel);
        ExcelUtil<SysPageModel> util = new ExcelUtil<SysPageModel>(SysPageModel.class);
        util.exportExcel(response, list, "页面模型数据");
    }

    /**
     * 获取页面模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:pageModel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysPageModelService.selectSysPageModelById(id));
    }

    /**
     * 获取页面模型信息
     *
     * @param modelKey 模型编码
     * @return
     */
    @GetMapping
    public AjaxResult getPageModelInfo(@RequestParam String modelKey) {
        PageModelInfo pageModel = sysPageModelService.getPageModelByKey(modelKey);
        return AjaxResult.success(pageModel);
    }

    /**
     * 查询用户选择字段
     */
    @GetMapping("/user-fields")
    public AjaxResult getUserFields(@RequestParam String modelKey) {
        List<PageModelFieldInfo> fieldList = sysPageModelService.selectUserFields(getUserId(), modelKey);
        return AjaxResult.success(fieldList);
    }

    /**
     * 保存用户选择字段
     */
    @PostMapping("/user-fields")
    public AjaxResult saveUserFields(@Valid @RequestBody SelectedFieldForm form) {
        sysPageModelService.saveUserFields(getUserId(), form.getModelKey(), form.getFieldKeys());
        return AjaxResult.success("保存成功");
    }

    /**
     * 新增页面模型
     */
    @PreAuthorize("@ss.hasPermi('system:pageModel:add')")
    @Log(title = "页面模型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysPageModel sysPageModel) {
        return toAjax(sysPageModelService.insertSysPageModel(sysPageModel));
    }

    /**
     * 修改页面模型
     */
    @PreAuthorize("@ss.hasPermi('system:pageModel:edit')")
    @Log(title = "页面模型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysPageModel sysPageModel) {
        return toAjax(sysPageModelService.updateSysPageModel(sysPageModel));
    }

    /**
     * 删除页面模型
     */
    @PreAuthorize("@ss.hasPermi('system:pageModel:remove')")
    @Log(title = "页面模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysPageModelService.deleteSysPageModelByIds(ids));
    }
}
