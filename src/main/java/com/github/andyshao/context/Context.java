package com.github.andyshao.context;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 5, 2018<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 */
public interface Context extends ConcurrentMap<String , Object>, CommonContext {
    @SuppressWarnings("unchecked")
    default <T> T setByType(ContextKey<T> key, T value) {
        return (T) put(key.keyName() , value);
    }
    
    @SuppressWarnings("unchecked")
    default <T> T computeIfAbsent(ContextKey<T> key, Function<ContextKey<T> , T> function) {
        return (T) computeIfAbsent(key.keyName() , k -> function.apply(key));
    }
    
    default <T> boolean containsKey(ContextKey<T> key) {
        return containsKey(key.keyName());
    }
    
    @SuppressWarnings("unchecked")
    default <T> T getByType(ContextKey<T> key) {
        return (T) get(key.keyName());
    }
    
    default <T> T getByTypeWithDefault(ContextKey<T> key, T defaultValue) {
        T ret = getByType(key);
        if(ret == null) return defaultValue;
        return defaultValue;
    }
}
