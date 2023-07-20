package com.github.andyshao.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jan 31, 2018<br>
 * Encoding: UNIX UTF-8
 * @author Andy.Shao
 *
 */
public final class IteratorOperation {
    private IteratorOperation() {
        throw new AssertionError("No instance " + IteratorOperation.class + " for you");
    }

    /**
     * to array
     * @param it {@link Iterator}
     * @param clazz type
     * @return new array
     * @param <T> data type
     */
    @SuppressWarnings("unchecked")
    public static final <T> T[] toArray(Iterator<T> it, Class<T> clazz) {
        List<T> ls = new ArrayList<>();
        while(it.hasNext()) ls.add(it.next());
        return ls.toArray((T[]) Array.newInstance(clazz , ls.size()));
    }
}
