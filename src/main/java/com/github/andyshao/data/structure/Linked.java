package com.github.andyshao.data.structure;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

import com.github.andyshao.util.CollectionModel;

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
public interface Linked<D , T extends Linked.LinkedElmt<D , T>> extends CollectionModel<D> {

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

    @Override
    public default boolean add(D e) {
        Linked.this.insNext(Linked.this.tail() , e);
        return true;
    }

    public Function<D , T> getElmtFactory(D data);

    public T head();

    public void insNext(T element , final D data);

    /**
     * Remove the next element.<br>
     * If the element is the tail, Then it won't remove anything.
     * 
     * @param element the item of linked's
     * @return if something is removed return data. If it doesn't return null.
     * @throws LinkedOperationException the operation of remove node
     */
    public D remNext(T element);

    @Override
    public default boolean remove(Object o) {
        T prev = null;
        for (T element = Linked.this.head() ; element != null ; element = element.next()) {
            if (Objects.equals(element.data() , o)) {
                Linked.this.remNext(prev);
                break;
            }
            prev = element;
        }
        return true;
    }

    @Override
    public default boolean retainAll(Collection<?> c) {
        T prev = null;
        for (T element = Linked.this.head() ; element != null ; element = element.next())
            if (!c.contains(element.data())) Linked.this.remNext(prev);
            else prev = element;
        return true;
    }

    @Override
    public int size();

    public T tail();

}
