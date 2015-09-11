package com.github.andyshao.build.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.github.andyshao.build.Construction;
import com.github.andyshao.build.Entity;
import com.github.andyshao.build.Injection;
import com.github.andyshao.reflect.ArrayOperation;
import com.github.andyshao.reflect.Reflects;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 11, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class Extracts {
    public static final Construction[] extractConstruction(Class<?> clazz) {
        //TODO
        return null;
    }

    public static final Entity extractEntity(Class<?> clazz) {
        final com.github.andyshao.build.annotation.Entity entity =
            clazz.getAnnotation(com.github.andyshao.build.annotation.Entity.class);
        return new Entity() {
            @Override
            public boolean isSingle() {
                return entity.isSingle();
            }

            @Override
            public String[] name() {
                return entity.name();
            }
        };
    }

    public static final Map<Field , Injection> extractInjectionFromField(Class<?> clazz) {
        final Field[] fields = Reflects.superGetDeclaredFields(clazz);
        final Map<Field , Injection> result = new HashMap<Field , Injection>();
        for (Field field : fields) {
            final com.github.andyshao.build.annotation.Injection injection =
                field.getAnnotation(com.github.andyshao.build.annotation.Injection.class);
            if (injection != null) result.put(field , new Injection() {
                @Override
                public boolean isEntityFactory() {
                    return injection.isEntityFactory();
                }

                @Override
                public String[] name() {
                    return injection.name();
                }

                @Override
                public Class<?> type() {
                    return injection.type();
                }
            });
        }
        return result;
    }

    public static final Map<Method , Injection> extractInjectionFromMethod(Class<?> clazz) {
        Map<Method , Injection> result = new HashMap<Method , Injection>();
        Map<Method2 , Injection> tmp = new HashMap<Method2 , Injection>();
        Extracts.extractInjectionFromMethod(clazz , tmp);
        for (Entry<Method2 , Injection> entry : tmp.entrySet())
            result.put(entry.getKey().getMethod() , entry.getValue());
        return result;
    }

    private static final void extractInjectionFromMethod(Class<?> clazz , Map<Method2 , Injection> map) {
        Method[] methods = ArrayOperation.mergeArray(Method[].class , clazz.getMethods() , clazz.getDeclaredMethods());
        for (Method method : methods) {
            final com.github.andyshao.build.annotation.Injection injection =
                method.getAnnotation(com.github.andyshao.build.annotation.Injection.class);
            if (injection != null) map.put(new Method2(method) , new Injection() {
                @Override
                public boolean isEntityFactory() {
                    return injection.isEntityFactory();
                }

                @Override
                public String[] name() {
                    return injection.name();
                }

                @Override
                public Class<?> type() {
                    return injection.type();
                }
            });
        }
        if (clazz.getSuperclass() != null) Extracts.extractInjectionFromMethod(clazz.getSuperclass() , map);
    }

    private Extracts() {
        throw new AssertionError("No " + Extracts.class + " instanment for you!");
    }
}
