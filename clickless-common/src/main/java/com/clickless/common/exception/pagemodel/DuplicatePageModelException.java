package com.clickless.common.exception.pagemodel;

/**
 * 模型重复异常
 * <p>
 * description
 *
 * @author Mason Zhou
 * @version 1.0.0
 * @date 2021/11/24
 */
public class DuplicatePageModelException extends RuntimeException{
    public DuplicatePageModelException(String message) {
        super(message);
    }
}
