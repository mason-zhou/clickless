package com.clickless.system.pagemodel.mapper;

import java.util.List;

import com.clickless.common.core.domain.pagemodel.PageModelFieldInfo;
import com.clickless.system.pagemodel.domain.SysPageModel;
import com.clickless.system.pagemodel.domain.SysPageModelField;
import com.clickless.system.pagemodel.domain.SysUserField;
import org.apache.ibatis.annotations.Param;

/**
 * 页面模型Mapper接口
 *
 * @author clickless
 * @date 2021-11-26
 */
public interface SysPageModelMapper {
    /**
     * 查询页面模型
     *
     * @param id 页面模型主键
     * @return 页面模型
     */
    SysPageModel selectSysPageModelById(Long id);

    /**
     * 查询页面模型
     *
     * @param modelKey 页面模型编码
     * @return 页面模型
     */
    SysPageModel selectSysPageModelByKey(String modelKey);

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
     * 删除页面模型
     *
     * @param id 页面模型主键
     * @return 结果
     */
    int deleteSysPageModelById(Long id);

    /**
     * 批量删除页面模型
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSysPageModelByIds(Long[] ids);

    /**
     * 查询页面模型 字段枚举值
     *
     * @param sysPageModel 页面模型
     * @return 字段枚举值
     */
    List<Object> selectSysPageModelDistinctColumnList(SysPageModel sysPageModel);

    /**
     * 批量删除页面模型字段
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSysPageModelFieldByModelIds(Long[] ids);

    /**
     * 批量新增页面模型字段
     *
     * @param sysPageModelFieldList 页面模型字段列表
     * @return 结果
     */
    int batchSysPageModelField(List<SysPageModelField> sysPageModelFieldList);


    /**
     * 通过页面模型主键删除页面模型字段信息
     *
     * @param id 页面模型ID
     * @return 结果
     */
    int deleteSysPageModelFieldByModelId(Long id);

    /**
     * 查询用户列表模型字段
     *
     * @param userId
     * @param modelKey
     * @return
     */
    List<SysUserField> selectUserFields(@Param("userId") Long userId, @Param("modelKey") String modelKey);

    /**
     * 删除用户字段
     *
     * @param userId
     * @param modelKey
     */
    void deleteUserFields(@Param("userId") Long userId, @Param("modelKey") String modelKey);

    /**
     * 保存用户字段
     *
     * @param userId
     * @param modelKey
     * @param fieldKeys
     */
    void insertUserFields(@Param("userId") Long userId, @Param("modelKey") String modelKey, @Param("fieldKeys") List<String> fieldKeys);
}
