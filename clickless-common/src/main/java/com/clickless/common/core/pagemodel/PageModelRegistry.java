package com.clickless.common.core.pagemodel;

import com.clickless.common.core.domain.pagemodel.PageModelInfo;
import com.clickless.common.exception.pagemodel.DuplicatePageModelException;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面模型注册中心
 * <p>
 * description
 *
 * @author Mason Zhou
 * @version 1.0.0
 * @date 2021/11/24
 */
public class PageModelRegistry {

    private Map<String, PageModelInfo> cache = new HashMap<>();

    public PageModelInfo getPageModelByKey(String modelKey) {
        return cache.get(modelKey);
    }

    public void register(String modelKey, PageModelInfo pageModelInfo) {
        if (cache.containsKey(modelKey)) {
            throw new DuplicatePageModelException("模型编码重复,key:" + modelKey);
        }
        cache.put(modelKey, pageModelInfo);
    }
}
