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
 * Copyright: Copryright(c) Nov 30, 2017<br>
 * Encoding:UNIX UTF-8
 * @author andy.shao
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface AfterStartedOpsBean {
    /**
     * run bean's initialization after other beans' initializations.
     * @return the bean name list
     */
    String[] runAfter() default {};

    /**
     * run bean's initialization before other beans' initializations.
     * @return the bean name list
     */
    String[] runBefore() default {};
}
