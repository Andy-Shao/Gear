package com.github.andyshao.reflect;

import java.lang.annotation.Annotation;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jan 24, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class AnnotationOperation {
    /**
     * 
     * @param target the object which has define and value about field
     * @param clazz the type of object
     * @param <T> the type of return
     * @return all of annotation
     * @see Class#getAnnotation(Class)
     */
    public static <T extends Annotation> T superGetAnnotation(Class<? extends Object> target , Class<T> clazz) {
        T annotation = target.getAnnotation(clazz);
        if (annotation != null) return annotation;

        if (target.isInterface()) {
            Class<?>[] interfaces = target.getInterfaces();
            for (Class<?> it : interfaces) {
                annotation = AnnotationOperation.superGetAnnotation(it , clazz);
                if (annotation != null) return annotation;
            }
        } else {
            Class<? extends Object> superClass = target.getSuperclass();
            if (superClass != null) {
                annotation = AnnotationOperation.superGetAnnotation(superClass , clazz);
                if (annotation != null) return annotation;
            }

            Class<?>[] interfaces = target.getInterfaces();
            for (Class<?> it : interfaces) {
                annotation = AnnotationOperation.superGetAnnotation(it , clazz);
                if (annotation != null) return annotation;
            }
        }

        return annotation;
    }

    private AnnotationOperation() {
        throw new AssertionError("No support instance " + AnnotationOperation.class + " for you!");
    }
}
