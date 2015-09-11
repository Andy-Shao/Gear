package com.github.andyshao.build.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Title:<br>
 * Descript: If both of name and type have values, process name firstly.<br>
 * Copyright: Copryright(c) Sep 11, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.FIELD , ElementType.METHOD
})
public @interface Injection {
    public boolean isEntityFactory() default false;

    public String[] name();

    public Class<?> type();
}
