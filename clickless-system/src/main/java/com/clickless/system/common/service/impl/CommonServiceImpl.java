package com.clickless.system.common.service.impl;

import com.clickless.system.common.mapper.CommonMapper;
import com.clickless.system.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通用查询 实现类
 * <p>
 * description
 *
 * @author Mason Zhou
 * @version 1.0.0
 * @date 2021/11/22
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private CommonMapper commonMapper;

    @Override
    public List<Object> selectColumnDistinctValue(String tableName, String dbColumnName, String searchValue) {
        return commonMapper.selectColumnDistinctValue(tableName, dbColumnName, searchValue);
    }
}
