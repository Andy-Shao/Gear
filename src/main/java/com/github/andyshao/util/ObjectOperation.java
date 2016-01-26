package com.github.andyshao.util;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jan 26, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ObjectOperation {
    public static final <T> T valueOrNull(T value , T nullDefault) {
        if (value == null) return nullDefault;
        else return value;
    }

    private ObjectOperation() {
        throw new AssertionError("No instance " + ObjectOperation.class + " for you");
    }
}
