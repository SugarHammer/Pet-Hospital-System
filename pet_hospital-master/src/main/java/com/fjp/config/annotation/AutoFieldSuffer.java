package com.fjp.config.annotation;

import com.fjp.enums.AutoFieldType;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFieldSuffer {
    AutoFieldType value();
}
