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
    /**
     * get the bean names by annotation
     * @param annotation the annotation which stores the bean names
     * @return bean name list
     */
    List<String> getBeanNamesForAnnotation(Class<? extends Annotation> annotation);

    /**
     * get the bean object by bean name
     * @param beanName the name of the bean's
     * @param beanClass the bean type
     * @return the bean object
     * @param <E> the type of the bean
     */
    <E> E getBean(String beanName, Class<E> beanClass);

    /**
     * get the bean object by bean name
     * @param beanName the name of the bean's
     * @return the bean object
     */
    Object getBean(String beanName);
}