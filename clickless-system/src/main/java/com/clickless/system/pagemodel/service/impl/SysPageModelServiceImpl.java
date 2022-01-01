package com.clickless.system.pagemodel.service.impl;

import com.clickless.common.constant.RedisKeyConstants;
import com.clickless.common.core.domain.pagemodel.PageModelFieldInfo;
import com.clickless.common.core.domain.pagemodel.PageModelInfo;
import com.clickless.common.core.pagemodel.PageModelRegistry;
import com.clickless.common.exception.pagemodel.PageModelNotFoundException;
import com.clickless.common.utils.DateUtils;
import com.clickless.common.utils.StringUtils;
import com.clickless.common.utils.spring.SpringUtils;
import com.clickless.system.pagemodel.domain.SysPageModel;
import com.clickless.system.pagemodel.domain.SysPageModelField;
import com.clickless.system.pagemodel.domain.SysUserField;
import com.clickless.system.pagemodel.mapper.SysPageModelMapper;
import com.clickless.system.pagemodel.service.SysPageModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 页面模型Service业务层处理
 *
 * @author clickless
 * @date 2021-11-26
 */
@Service
public class SysPageModelServiceImpl implements SysPageModelService {
    @Autowired
    private SysPageModelMapper sysPageModelMapper;

    @Autowired
    private PageModelRegistry pageModelRegistry;

    /**
     * 查询页面模型
     *
     * @param id 页面模型主键
     * @return 页面模型
     */
    @Override
    public SysPageModel selectSysPageModelById(Long id) {
        return sysPageModelMapper.selectSysPageModelById(id);
    }

    @Override
    public PageModelInfo getPageModelByKey(String modelKey) {
        // 先从数据库查询模型信息 数据库优先级最高
        PageModelInfo pageModel = SpringUtils.getBean(SysPageModelService.class).getPageModelFromDatabase(modelKey);
        // 查不到再从本地注册中心查询
        if (pageModel == null) {
            pageModel = pageModelRegistry.getPageModelByKey(modelKey);
        }

        if (pageModel == null) {
            throw new PageModelNotFoundException("未查询到模型配置,模型编码key:" + modelKey);
        }
        return pageModel;
    }

    /**
     * 从数据库查询页面模型
     *
     * @param modelKey 模型编码
     * @return
     */

    @Override
    @Cacheable(RedisKeyConstants.PAGE_MODEL)
    public PageModelInfo getPageModelFromDatabase(String modelKey) {
        SysPageModel dbModel = sysPageModelMapper.selectSysPageModelByKey(modelKey);
        if (dbModel != null) {
            PageModelInfo model = new PageModelInfo();
            model.setKey(dbModel.getModelKey());
            model.setName(dbModel.getModelName());
            model.setDbTableName(dbModel.getDbTableName());
            List<SysPageModelField> dbModelFields = dbModel.getSysPageModelFieldList();
            if (dbModelFields != null) {
                List<PageModelFieldInfo> modelFieldList = new ArrayList<>();
                for (SysPageModelField dbField : dbModelFields) {
                    PageModelFieldInfo modelField = new PageModelFieldInfo();
                    modelField.setKey(dbField.getFieldKey());
                    modelField.setName(dbField.getFieldName());
                    modelField.setDbColumnName(dbField.getDbColumnName());
                    modelField.setDictType(dbField.getDictType());
                    modelFieldList.add(modelField);
                }
                model.setFields(modelFieldList);
            }
            return model;
        }
        return null;
    }

    /**
     * 查询页面模型列表
     *
     * @param sysPageModel 页面模型
     * @return 页面模型
     */
    @Override
    public List<SysPageModel> selectSysPageModelList(SysPageModel sysPageModel) {
        return sysPageModelMapper.selectSysPageModelList(sysPageModel);
    }

    /**
     * 新增页面模型
     *
     * @param sysPageModel 页面模型
     * @return 结果
     */
    @Transactional
    @CacheEvict(RedisKeyConstants.PAGE_MODEL)
    @Override
    public int insertSysPageModel(SysPageModel sysPageModel) {
        sysPageModel.setCreateTime(DateUtils.getNowDate());
        int rows = sysPageModelMapper.insertSysPageModel(sysPageModel);
        insertSysPageModelField(sysPageModel);
        return rows;
    }

    /**
     * 修改页面模型
     *
     * @param sysPageModel 页面模型
     * @return 结果
     */
    @Transactional
    @CacheEvict(RedisKeyConstants.PAGE_MODEL)
    @Override
    public int updateSysPageModel(SysPageModel sysPageModel) {
        sysPageModel.setUpdateTime(DateUtils.getNowDate());
        sysPageModelMapper.deleteSysPageModelFieldByModelId(sysPageModel.getId());
        insertSysPageModelField(sysPageModel);
        return sysPageModelMapper.updateSysPageModel(sysPageModel);
    }

    /**
     * 批量删除页面模型
     *
     * @param ids 需要删除的页面模型主键
     * @return 结果
     */
    @Transactional
    @CacheEvict(RedisKeyConstants.PAGE_MODEL)
    @Override
    public int deleteSysPageModelByIds(Long[] ids) {
        sysPageModelMapper.deleteSysPageModelFieldByModelIds(ids);
        return sysPageModelMapper.deleteSysPageModelByIds(ids);
    }

    /**
     * 删除页面模型信息
     *
     * @param id 页面模型主键
     * @return 结果
     */
    @CacheEvict(RedisKeyConstants.PAGE_MODEL)
    @Override
    public int deleteSysPageModelById(Long id) {
        sysPageModelMapper.deleteSysPageModelFieldByModelId(id);
        return sysPageModelMapper.deleteSysPageModelById(id);
    }

    /**
     * 查询页面模型 字段枚举值
     *
     * @param sysPageModel 页面模型
     * @return 字段枚举值
     */
    @Override
    public List<Object> selectSysPageModelFieldEnumsList(SysPageModel sysPageModel) {
        return sysPageModelMapper.selectSysPageModelDistinctColumnList(sysPageModel);
    }


    /**
     * 新增页面模型字段信息
     *
     * @param sysPageModel 页面模型对象
     */
    @CacheEvict(RedisKeyConstants.PAGE_MODEL)
    public void insertSysPageModelField(SysPageModel sysPageModel) {
        List<SysPageModelField> sysPageModelFieldList = sysPageModel.getSysPageModelFieldList();
        Long id = sysPageModel.getId();
        if (StringUtils.isNotNull(sysPageModelFieldList)) {
            List<SysPageModelField> list = new ArrayList<SysPageModelField>();
            for (SysPageModelField sysPageModelField : sysPageModelFieldList) {
                sysPageModelField.setModelId(id);
                list.add(sysPageModelField);
            }
            if (list.size() > 0) {
                sysPageModelMapper.batchSysPageModelField(list);
            }
        }
    }

    @Override
    public List<PageModelFieldInfo> selectUserFields(Long userId, String modelKey) {

        PageModelInfo model = this.getPageModelByKey(modelKey);
        Map<String, PageModelFieldInfo> fieldInfoMap = model.getFields().stream().collect(Collectors.toMap(PageModelFieldInfo::getKey, fieldInfo -> fieldInfo));

        List<SysUserField> userFieldList = sysPageModelMapper.selectUserFields(userId, modelKey);

        List<PageModelFieldInfo> result = new ArrayList<>();

        userFieldList.forEach(
                userField -> {
                    PageModelFieldInfo fieldInfo = fieldInfoMap.get(userField.getFieldKey());
                    result.add(fieldInfo);
                }
        );
        return result;
    }

    @Override
    @Transactional
    public void saveUserFields(Long userId, String modelKey, List<String> fieldKeys) {
        // 1.删除用户字段
        sysPageModelMapper.deleteUserFields(userId, modelKey);
        // 2.重新插入用户字段
        if (!CollectionUtils.isEmpty(fieldKeys)) {
            sysPageModelMapper.insertUserFields(userId, modelKey, fieldKeys);
        }
    }
}
