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
public interface SingleLinked<D> extends Linked<D , CycleLinkedElmt<D>> , SingleLinkedOperation<D , CycleLinkedElmt<D>> {
    public class MySingleLinked<D> implements SingleLinked<D> {
        private class MyIterator implements Iterator<D> {
            private final long actionCount = MySingleLinked.this.actionCount;
            private volatile CycleLinkedElmt<D> index = MySingleLinked.this.head();

            @Override
            public boolean hasNext() {
                if (this.actionCount != MySingleLinked.this.actionCount) throw new ConcurrentModificationException();
                return this.index != null;
            }

            @Override
            public D next() {
                CycleLinkedElmt<D> result = this.index;
                this.index = this.index.next();
                return result.data();
            }

        }

        private long actionCount = 0;
        private final Function<D , CycleLinkedElmt<D>> cycleLinkedElmt;
        private CycleLinkedElmt<D> head;
        private int size = 0;
        private CycleLinkedElmt<D> tail;

        public MySingleLinked(Function<D , CycleLinkedElmt<D>> cycleLinkedElmt) {
            this.cycleLinkedElmt = cycleLinkedElmt;
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
                } else this.list_rem_next(this.head);
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
        public Function<D , CycleLinkedElmt<D>> getCycleLinkedElmtFactory() {
            return this.cycleLinkedElmt;
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
        public Iterator<D> iterator() {
            return this.new MyIterator();
        }

        @Override
        public void list_ins_next(CycleLinkedElmt<D> element , final D data) {
            CycleLinkedElmt<D> new_element = this.cycleLinkedElmt.apply(data);

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
        public D list_rem_next(CycleLinkedElmt<D> element) {
            CycleLinkedElmt<D> old_element = this.cycleLinkedElmt.apply(null);
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

            //Free the storage allocated by the abstract datatype.
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

    public static <DATA> SingleLinked<DATA> defaultSingleLinked(Function<DATA , CycleLinkedElmt<DATA>> cycleLinkedElmt) {
        return new MySingleLinked<DATA>(cycleLinkedElmt);
    }

    public Function<D , CycleLinkedElmt<D>> getCycleLinkedElmtFactory();

    @Override
    public void list_ins_next(CycleLinkedElmt<D> element , final D data);

    @Override
    public D list_rem_next(CycleLinkedElmt<D> element);
}
