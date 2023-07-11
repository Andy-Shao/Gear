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
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodInfo {
    /**
     * method generic info
     * @return {@link Generic}
     */
    Generic methodGenericInfo() default @Generic
    ;

    /**
     * method return type generic info
     * @return {@link Generic}
     */
    Generic returnGenericInfo() default @Generic
    ;
}
