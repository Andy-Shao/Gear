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

    public static
        <DATA , E extends Linked.LinkedElmt<DATA , E> , T extends Linked<DATA , E> & SingleLinkedOperation<DATA , E>>
        Stack<DATA> DEFAULT_STACK(T linked) {
        return new Stack<DATA>() {

            @Override
            public void clean() {
                linked.clean();
            }

            @Override
            public Iterator<DATA> iterator() {
                return linked.iterator();
            }

            @Override
            public DATA peek() {
                return linked.head() == null ? null : linked.head().list_Data();
            }

            @Override
            public DATA pop() {
                return linked.list_rem_next(null);
            }

            @Override
            public void push(DATA data) {
                linked.list_ins_next(null , data);
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
