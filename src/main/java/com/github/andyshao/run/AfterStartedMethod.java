package com.github.andyshao.run;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 29, 2017<br>
 * Encoding:UNIX UTF-8
 * @author andy.shao
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AfterStartedMethod {
    /**
     * the bean generation sequence.
     * From small to big.
     * @return the number of the order
     */
    int order() default 0;
}
