package com.clickless.common.annotation.pagemodel;

import java.lang.annotation.*;

/**
 * 页面模型-列表模型 注解
 * 列表
 *
 * @author clickless
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageModel {
    /**
     * 页面模型编码(选填)
     * 如果注解没有指定key值，则以相应的类名作为key
     */
    String key() default "";

    /**
     * 列表模型名称
     */
    String name() default "";

    /**
     * 列表模型数据库表名
     */
    String dbTableName() default "";
}
