package com.github.andyshao.reflect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 4, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
@Documented
@Deprecated
@Target({ ElementType.FIELD , ElementType.TYPE , ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Generic {
    /**
     * component type
     * @return type definites
     */
    String[] componentTypes() default {};

    /**
     * is generic
     * @return if it is generic then true
     */
    boolean isGeneric() default false;
}
