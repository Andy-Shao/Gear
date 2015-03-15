package com.github.andyshao.data.structure;

import java.util.function.Function;

import com.github.andyshao.lang.Cleanable;

/**
 * 
 * Title: Linked list interface<br>
 * Descript: Because of it is a linked list, you should use the {@link #clear()}
 * method when you
 * want to remove this list.<br>
 * Copyright: Copryright(c) Feb 8, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 * @param <T> element type
 */
public interface Linked<D , T extends Linked.LinkedElmt<D , T>> extends Cleanable , Iterable<D> {

    public interface LinkedElmt<DATA , T extends Linked.LinkedElmt<DATA , T>> {

        public abstract DATA data();

        public default void free() {
            this.setData(null);
            this.setNext(null);
        }

        public abstract T next();

        public abstract void setData(DATA data);

        public abstract void setNext(T next);
    }

    public Function<D , T> getElmtFactory(D data);

    public T head();

    public void list_ins_next(T element , final D data);

    /**
     * Remove the next element.<br>
     * If the element is the tail, Then it won't remove anything.
     * 
     * @param element the item of linked's
     * @return if something is removed return data. If it doesn't return null.
     * @throws LinkedOperationException the operation of remove node
     */
    public D list_rem_next(T element);

    public int size();

    public T tail();
}
