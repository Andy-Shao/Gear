package com.github.andyshao.test;

import java.util.Objects;

import com.github.andyshao.lang.ArrayWrapper;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 28, 2018<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public final class CompareOptions {
    private CompareOptions() {}

    @SafeVarargs
    public static final <T> boolean anyOf(T test, T... cases) {
        if(test.getClass().isArray()) throw new IllegalArgumentException("argument must not be array");
        for(T ca : cases) {
            if(Objects.equals(test , ca)) return true;
        }
        return false;
    }
    
    public static final boolean anyArrayOf(ArrayWrapper test, ArrayWrapper... cases) {
        for(ArrayWrapper ca : cases) {
            if(Objects.deepEquals(ca , test)) return true;
        }
        return false;
    }
}
