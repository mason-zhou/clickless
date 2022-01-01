package com.clickless.common.exception.pagemodel;

/**
 * 异常类
 * <p>
 * 页面模型配置查询不到时抛出异常
 *
 * @author Mason Zhou
 * @version 1.0.0
 * @date 2021/11/22
 */
public class PageModelNotFoundException extends RuntimeException {
    public PageModelNotFoundException(String message) {
        super(message);
    }
}
