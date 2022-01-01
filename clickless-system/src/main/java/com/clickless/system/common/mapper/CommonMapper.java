package com.clickless.system.common.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通用查询 数据访问层
 * <p>
 * description
 *
 * @author Mason Zhou
 * @version 1.0.0
 * @date 2021/11/22
 */
public interface CommonMapper {
    /**
     * 查询表字段 枚举值
     *
     * @param tableName
     * @param dbColumnName
     * @param searchValue
     * @return
     */
    List<Object> selectColumnDistinctValue(@Param("tableName") String tableName,
                                           @Param("dbColumnName") String dbColumnName,
                                           @Param("searchValue") String searchValue);
}
