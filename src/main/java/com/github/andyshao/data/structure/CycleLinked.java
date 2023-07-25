package com.github.andyshao.data.structure;

import java.io.Serial;
import java.io.Serializable;
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
    @Serial
    private static final long serialVersionUID = -1242792864988506815L;

    /**
     * Default Iterator
     */
    class MyIterator implements Iterator<DATA>, Serializable {
        @Serial
        private static final long serialVersionUID = -4771582456239944351L;
        private final long actionCount = CycleLinked.this.actionAccount;
        private volatile CycleLinkedElmt<DATA> index = CycleLinked.this.head();
        private volatile int position = 0;

        @Override
        public boolean hasNext() {
            if (this.actionCount != CycleLinked.this.actionAccount) throw new ConcurrentModificationException();
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

    /**
     * Get default {@link CycleLinked}
     * @return {@link CycleLinked}
     * @param <DATA> data type
     */
    public static <DATA> CycleLinked<DATA> defaultCycleLinked() {
        return CycleLinked.defaultCycleLinked(CycleLinkedElmt::defaultElmt);
    }

    /**
     * Get default {@link CycleLinked}
     * @param elmtFactory {@link CycleLinkedElmt} factory
     * @return {@link CycleLinked}
     * @param <DATA> data type
     */
    public static <DATA> CycleLinked<DATA> defaultCycleLinked(Function<DATA , CycleLinkedElmt<DATA>> elmtFactory) {
        return new CycleLinked<DATA>(elmtFactory);
    }

    /**action account*/
    private long actionAccount = 0;

    /**element factory*/
    private final Function<DATA , CycleLinkedElmt<DATA>> elmtFactory;

    /**head of the elements*/
    private CycleLinkedElmt<DATA> head;

    /**size*/
    private int size;

    /**
     * Build {@link CycleLinked}
     * @param elmtFactory {@link CycleLinkedElmt} factory
     */
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
        this.actionAccount++;
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
        this.actionAccount++;

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
