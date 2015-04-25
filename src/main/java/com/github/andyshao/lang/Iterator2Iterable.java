package com.github.andyshao.lang;

import java.util.Iterator;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 25, 2015<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 * @param <E> element type
 */
public class Iterator2Iterable<E> implements Convert<Iterator<E> , Iterable<E>> {
    @Override
    public Iterable<E> convert(Iterator<E> in) {
        return () -> in;
    }

}
