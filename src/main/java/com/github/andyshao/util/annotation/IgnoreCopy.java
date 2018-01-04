package com.github.andyshao.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jan 4, 2018<br>
 * Encoding: UNIX UTF-8
 * @author weichuang.shao
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreCopy {

}
