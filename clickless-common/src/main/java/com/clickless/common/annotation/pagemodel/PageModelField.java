package com.clickless.common.annotation.pagemodel;

import java.lang.annotation.*;

/**
 * 页面模型-列表模型字段 注解
 *
 * @author clickless
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageModelField {
    /**
     * 字段编码key(选填)
     * 如果注解没有指定key值，则以类的字段名作为key
     */
    String key() default "";

    /**
     * 显示名称
     */
    String name() default "";

    /**
     * 数据库列名
     * @return
     */
    String dbColumnName() default "";

    /**
     * 字典类型
     * @return
     */
    String dictType() default "";

    /**
     * 字段显示顺序
     * @return
     */
    int sort() default Integer.MAX_VALUE;
}
