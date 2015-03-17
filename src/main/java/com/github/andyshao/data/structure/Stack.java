package com.github.andyshao.data.structure;

import java.util.Iterator;

import com.github.andyshao.lang.Cleanable;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 10, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 */
public interface Stack<D> extends Cleanable , Iterable<D> {

    public static <DATA , E extends Linked.LinkedElmt<DATA , E>> Stack<DATA> defaultStack(Linked<DATA , E> linked) {
        return new Stack<DATA>() {

            @Override
            public void clear() {
                linked.clear();
            }

            @Override
            public Iterator<DATA> iterator() {
                return linked.iterator();
            }

            @Override
            public DATA peek() {
                return linked.head() == null ? null : linked.head().data();
            }

            @Override
            public DATA pop() {
                return linked.remNext(null);
            }

            @Override
            public void push(DATA data) {
                linked.insNext(null , data);
            }

            @Override
            public int size() {
                return linked.size();
            }
        };
    }

    public D peek();

    public D pop();

    public void push(final D data);

    public int size();

}
