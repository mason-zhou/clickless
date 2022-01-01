package com.clickless.system.common.service;

import java.util.List;

/**
 * 通用查询接口
 * <p>
 * description
 *
 * @author Mason Zhou
 * @version 1.0.0
 * @date 2021/11/22
 */
public interface CommonService {
    /**
     * 查询表字段 枚举值
     *
     * @param tableName
     * @param dbColumnName
     * @param searchValue
     * @return
     */
    List<Object> selectColumnDistinctValue(String tableName, String dbColumnName, String searchValue);
}
