package com.github.andyshao.test;

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

    @SafeVarargs
    public static final <T> boolean anyOf(T test, T... cases) {
        for(T ca : cases) {
            if(Objects.equals(test , ca)) return true;
        }
        return false;
    }
}
