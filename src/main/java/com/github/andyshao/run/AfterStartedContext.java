package com.github.andyshao.run;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Dec 13, 2017<br>
 * Encoding:UNIX UTF-8
 * @author andy.shao
 *
 */
public interface AfterStartedContext {
    List<String> getBeanNamesForAnnotation(Class<? extends Annotation> annotation);
    <E> E getBean(String beanName, Class<E> beanClass);
    Object getBean(String beanName);
}