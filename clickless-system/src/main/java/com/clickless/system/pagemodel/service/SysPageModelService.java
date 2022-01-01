package com.clickless.system.pagemodel.service;

import com.clickless.common.core.domain.pagemodel.PageModelFieldInfo;
import com.clickless.common.core.domain.pagemodel.PageModelInfo;
import com.clickless.system.pagemodel.domain.SysPageModel;

import java.util.List;

/**
 * 页面模型Service接口
 *
 * @author clickless
 * @date 2021-11-26
 */
public interface SysPageModelService {
    /**
     * 查询页面模型
     *
     * @param id 页面模型主键
     * @return 页面模型
     */
    SysPageModel selectSysPageModelById(Long id);

    /**
     * 获取页面模型信息
     *
     * @param pageModelKey 模型编码
     * @return
     */
    PageModelInfo getPageModelByKey(String pageModelKey);

    /**
     * 从数据库查询页面模型
     *
     * @param modelKey 模型编码
     * @return
     */
    PageModelInfo getPageModelFromDatabase(String modelKey);

    /**
     * 查询用户选择字段
     *
     * @param userId   用户ID
     * @param modelKey 模型编码
     * @return
     */
    List<PageModelFieldInfo> selectUserFields(Long userId, String modelKey);

    /**
     * 保存用户选择字段
     *
     * @param userId
     * @param modelKey
     * @param fieldKeys
     */
    void saveUserFields(Long userId, String modelKey, List<String> fieldKeys);

    /**
     * 查询页面模型列表
     *
     * @param sysPageModel 页面模型
     * @return 页面模型集合
     */
    List<SysPageModel> selectSysPageModelList(SysPageModel sysPageModel);

    /**
     * 新增页面模型
     *
     * @param sysPageModel 页面模型
     * @return 结果
     */
    int insertSysPageModel(SysPageModel sysPageModel);

    /**
     * 修改页面模型
     *
     * @param sysPageModel 页面模型
     * @return 结果
     */
    int updateSysPageModel(SysPageModel sysPageModel);

    /**
     * 批量删除页面模型
     *
     * @param ids 需要删除的页面模型主键集合
     * @return 结果
     */
    int deleteSysPageModelByIds(Long[] ids);

    /**
     * 删除页面模型信息
     *
     * @param id 页面模型主键
     * @return 结果
     */
    int deleteSysPageModelById(Long id);

    /**
     * 查询页面模型 字段枚举值
     *
     * @param sysPageModel 页面模型
     * @return 字段枚举值
     */
    List<Object> selectSysPageModelFieldEnumsList(SysPageModel sysPageModel);
}
