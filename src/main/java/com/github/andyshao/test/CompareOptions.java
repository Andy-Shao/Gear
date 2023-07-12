package com.github.andyshao.test;

import com.github.andyshao.lang.ArrayWrapper;

import java.util.Objects;

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

    /**
     * any of
     * @param test test
     * @param cases cases
     * @return does any case matches
     * @param <T> data type
     */
    @SafeVarargs
    public static final <T> boolean anyOf(T test, T... cases) {
        if(test.getClass().isArray()) throw new IllegalArgumentException("argument must not be array");
        for(T ca : cases) {
            if(Objects.equals(test , ca)) return true;
        }
        return false;
    }

    /**
     * any array of
     * @param test a array
     * @param cases a array cases
     * @return does any case matches
     */
    public static final boolean anyArrayOf(ArrayWrapper test, ArrayWrapper... cases) {
        for(ArrayWrapper ca : cases) {
            if(Objects.deepEquals(ca , test)) return true;
        }
        return false;
    }
}
