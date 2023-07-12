package com.github.andyshao.util.annotation;

import com.github.andyshao.util.EntityOperation.PropertyConvert;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CopyConvertor {
    /**{@link PropertyConvert}*/
    Class<PropertyConvert> convertor(); 
}
