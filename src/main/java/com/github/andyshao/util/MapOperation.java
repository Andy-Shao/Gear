package com.github.andyshao.util;

import java.util.Map;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 26, 2018<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public final class MapOperation {
    private MapOperation() {
    }
    
    public static final <K,V> boolean isEmptyOrNull(Map<K, V> map) {
        return map == null || map.isEmpty();
    }
}
