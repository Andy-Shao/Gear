package com.github.andyshao.data.structure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

/**
 * 
 * Title: Single Linked interface<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 8, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 */
public class SingleLinked<D> implements Linked<D , CycleLinkedElmt<D>> {
    private class MyIterator implements Iterator<D> {
        private final long actionCount = SingleLinked.this.actionCount;
        private volatile CycleLinkedElmt<D> index = SingleLinked.this.head();

        @Override
        public boolean hasNext() {
            if (this.actionCount != SingleLinked.this.actionCount) throw new ConcurrentModificationException();
            return this.index != null;
        }

        @Override
        public D next() {
            CycleLinkedElmt<D> result = this.index;
            this.index = this.index.next();
            return result.data();
        }

    }

    public static <DATA> SingleLinked<DATA> defaultSingleLinked() {
        return SingleLinked.defaultSingleLinked(CycleLinkedElmt::defaultElmt);
    }

    public static <DATA> SingleLinked<DATA>
        defaultSingleLinked(Function<DATA , CycleLinkedElmt<DATA>> cycleLinkedElmt) {
        return new SingleLinked<DATA>(cycleLinkedElmt);
    }

    private long actionCount = 0;
    private final Function<D , CycleLinkedElmt<D>> cycleLinkedElmtFactory;
    private CycleLinkedElmt<D> head;
    private int size = 0;

    private CycleLinkedElmt<D> tail;

    public SingleLinked(Function<D , CycleLinkedElmt<D>> cycleLinkedElmtFactory) {
        this.cycleLinkedElmtFactory = cycleLinkedElmtFactory;
    }

    @Override
    public void clear() {
        do
            if (this.size == 0) return;
            else if (this.size == 1) {
                this.head.free();
                this.head = null;
                this.tail = null;
                this.size = 0;
                return;
            } else this.remNext(this.head);
        while (this.size != 0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        SingleLinked<D> that;
        if (obj instanceof SingleLinked) {
            that = (SingleLinked<D>) obj;
            return this.size() == that.size() && Objects.equals(this.head() , that.head())
                && Objects.equals(this.tail() , that.tail());
        } else return false;
    }

    @Override
    public Function<D , CycleLinkedElmt<D>> getElmtFactory() {
        return this.cycleLinkedElmtFactory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.size() , this.head() , this.tail());
    }

    @Override
    public CycleLinkedElmt<D> head() {
        return this.head;
    }

    @Override
    public void insNext(CycleLinkedElmt<D> element , final D data) {
        CycleLinkedElmt<D> new_element = this.cycleLinkedElmtFactory.apply(data);

        if (element == null) {
            //Handle insertion at the head of the list.
            if (this.size() == 0) this.tail = new_element;

            new_element.setNext(this.head);
            this.head = new_element;
        } else {
            //Handle insertion somewhere other than at the head.
            if (element.next() == null) this.tail = new_element;

            new_element.setNext(element.next());
            element.setNext(new_element);
        }

        //Adjust the size of the list to account for the inserted element.
        this.size++;
        this.actionCount++;
    }

    @Override
    public Iterator<D> iterator() {
        return this.new MyIterator();
    }

    @Override
    public D remNext(CycleLinkedElmt<D> element) {
        CycleLinkedElmt<D> old_element = this.cycleLinkedElmtFactory.apply(null);
        D data = null;

        if (this.size() == 0) throw new LinkedOperationException("Do not allow removal from an empty list.");

        //Remove the element from the list.
        if (element == null) {
            //Handle removal from the head of the list.
            data = this.head.data();
            old_element = this.head;
            this.head = this.head.next();

            if (this.size() == 1) this.tail = null;
        } else {
            if (element.next() == null) return null;

            data = element.next().data();
            old_element = element.next();
            element.setNext(element.next().next());

            if (element.next() == null) this.tail = element;
        }

        old_element.free();

        //Adjust the size of the list of account for the removed element.
        this.size--;
        this.actionCount++;

        return data;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public CycleLinkedElmt<D> tail() {
        return this.tail;
    }
}
