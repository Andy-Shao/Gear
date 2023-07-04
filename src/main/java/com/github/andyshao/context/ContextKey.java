package com.github.andyshao.context;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 5, 2018<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 * @param <E> value type
 */
public interface ContextKey<E> {
    /**
     * the name of the key
     * @return key name
     */
    String keyName();
}
