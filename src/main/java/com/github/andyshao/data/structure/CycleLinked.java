package com.github.andyshao.data.structure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.function.Function;

/**
 * 
 * Title: Cycle Linked interface<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 9, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <DATA> data
 */
public class CycleLinked<DATA> implements Linked<DATA , CycleLinkedElmt<DATA>> {
    class MyIterator implements Iterator<DATA> {
        private final long actionCount = CycleLinked.this.actionAcount;
        private volatile CycleLinkedElmt<DATA> index = CycleLinked.this.head();
        private volatile int position = 0;

        @Override
        public boolean hasNext() {
            if (this.actionCount != CycleLinked.this.actionAcount) throw new ConcurrentModificationException();
            return this.position < CycleLinked.this.size();
        }

        @Override
        public DATA next() {
            CycleLinkedElmt<DATA> result = this.index;
            this.index = this.index.next();
            this.position++;
            return result.data();
        }

    }

    public static <DATA> CycleLinked<DATA> defaultCycleLinked() {
        return CycleLinked.defaultCycleLinked(CycleLinkedElmt::defaultElmt);
    }

    public static <DATA> CycleLinked<DATA> defaultCycleLinked(Function<DATA , CycleLinkedElmt<DATA>> elmtFactory) {
        return new CycleLinked<DATA>(elmtFactory);
    }

    private long actionAcount = 0;

    private final Function<DATA , CycleLinkedElmt<DATA>> elmtFactory;

    private CycleLinkedElmt<DATA> head;

    private int size;

    public CycleLinked(Function<DATA , CycleLinkedElmt<DATA>> elmtFactory) {
        this.elmtFactory = elmtFactory;
    }

    @Override
    public void clear() {
        do
            if (this.size == 0) return;
            else if (this.size == 1) {
                this.head.free();
                this.head = null;
                this.size = 0;
                return;
            } else this.remNext(this.head);
        while (this.size != 0);
    }

    @Override
    public Function<DATA , CycleLinkedElmt<DATA>> getElmtFactory() {
        return this.elmtFactory;
    }

    @Override
    public CycleLinkedElmt<DATA> head() {
        return this.head;
    }

    @Override
    public void insNext(CycleLinkedElmt<DATA> element , final DATA data) {
        CycleLinkedElmt<DATA> new_element = this.getElmtFactory().apply(data);

        if (this.size == 0) {
            //Handle insertion when the list is empty.
            new_element.setNext(new_element);
            this.head = new_element;
        } else if (element == null) {
            new_element.setNext(this.head);
            this.head = new_element;
        } else {
            //Handle insertion when the list is not empty.
            new_element.setNext(element.next());
            element.setNext(new_element);
        }

        //Adjust the size of the list to account for the inserted element.
        this.size++;
        this.actionAcount++;
    }

    @Override
    public Iterator<DATA> iterator() {
        return new MyIterator();
    }

    @Override
    public DATA remNext(CycleLinkedElmt<DATA> element) {
        CycleLinkedElmt<DATA> old_element = this.getElmtFactory().apply(null);
        DATA data = null;

        //Do not allow removal from an empty list.
        if (this.size == 0) throw new LinkedOperationException("Do not allow removal from an empty list.");
        if (element == null) element = this.head;

        //Remove the element from the list.
        data = element.next().data();
        if (element.next() == element) {
            //Handle removing the last element.
            old_element = element.next();
            this.head = null;
        } else {
            //Handle removing other than the last element.
            old_element = element.next();
            element.setNext(element.next().next());
            if (old_element == this.head) this.head = old_element.next();
        }

        old_element.free();

        //Adjust the size of the list to account for the removed element.
        this.size--;
        this.actionAcount++;

        return data;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public CycleLinkedElmt<DATA> tail() {
        return this.head;
    }
}
