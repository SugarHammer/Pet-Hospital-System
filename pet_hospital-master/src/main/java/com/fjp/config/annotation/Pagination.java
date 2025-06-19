package com.fjp.config.annotation;

import java.lang.annotation.*;

/**
 * 分页注解，适用于方法只有一个PageQuery参数
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Pagination {
}
