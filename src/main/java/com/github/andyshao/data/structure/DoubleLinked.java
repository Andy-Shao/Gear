package com.github.andyshao.data.structure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

/**
 * 
 * Title:Double Linked interface<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 9, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 */
public interface DoubleLinked<D> extends Linked<D , DoubleLinked.DoubleLinkedElmt<D>> {
    public interface DoubleLinkedElmt<DATA> extends Linked.LinkedElmt<DATA , DoubleLinkedElmt<DATA>> {
        public static <DATA> DoubleLinkedElmt<DATA> defaultElmt(DATA data) {
            DoubleLinkedElmt<DATA> result = new DoubleLinkedElmt<DATA>() {
                private DATA data;
                private DoubleLinkedElmt<DATA> next;
                private DoubleLinkedElmt<DATA> prev;

                @Override
                public DATA data() {
                    return this.data;
                }

                @SuppressWarnings("unchecked")
                @Override
                public boolean equals(Object obj) {
                    DoubleLinkedElmt<DATA> that;
                    if (obj instanceof DoubleLinkedElmt) {
                        that = (DoubleLinkedElmt<DATA>) obj;
                        return Objects.equals(this.data() , that.data()) && Objects.equals(this.next() , that.next())
                            && Objects.equals(this.getPrev() , that.getPrev());
                    } else return false;
                }

                @Override
                public DoubleLinkedElmt<DATA> getPrev() {
                    return this.prev;
                }

                @Override
                public int hashCode() {
                    return Objects.hash(this.data() , this.getPrev() , this.next());
                }

                @Override
                public DoubleLinkedElmt<DATA> next() {
                    return this.next;
                }

                @Override
                public void setData(DATA data) {
                    this.data = data;
                }

                @Override
                public void setNext(DoubleLinkedElmt<DATA> next) {
                    this.next = next;
                }

                @Override
                public void setPrev(DoubleLinkedElmt<DATA> prev) {
                    this.prev = prev;
                }
            };
            result.setData(data);

            return result;
        }

        public DoubleLinkedElmt<DATA> getPrev();

        public void setPrev(DoubleLinkedElmt<DATA> prev);
    }

    public static <DATA> DoubleLinked<DATA> defaultDoubleLinked() {
        return new DoubleLinked<DATA>() {
            private long actionCount = 0;
            private DoubleLinked.DoubleLinkedElmt<DATA> head;
            private int size;
            private DoubleLinked.DoubleLinkedElmt<DATA> tail;

            @Override
            public void clear() {
                do
                    this.dlist_remove(this.head);
                while (this.size != 0);
            }

            @Override
            public void dlist_ins_prev(DoubleLinked.DoubleLinkedElmt<DATA> element , final DATA data) {
                //Do not allowed a NULL element unless the list is empty.
                if (element == null && this.size() != 0) throw new LinkedOperationException(
                    "Do not allowed a NULL element unless the list is empty.");

                DoubleLinked.DoubleLinkedElmt<DATA> new_element =
                    DoubleLinked.DoubleLinkedElmt.<DATA> defaultElmt(data);

                if (this.size() == 0) {
                    //Handle insertion when the list is empty.
                    this.head = new_element;
                    this.head.setPrev(null);
                    this.head.setNext(null);
                    this.tail = new_element;
                } else {
                    //Handle insertion when the list is not empty.
                    new_element.setNext(element);
                    new_element.setPrev(element.getPrev());

                    if (element.getPrev() == null) this.head = new_element;
                    else element.getPrev().setNext(new_element);

                    element.setPrev(new_element);
                }

                //Adjust the size of list to accoutn for the element.
                this.size++;
                this.actionCount++;
            }

            @Override
            public DATA dlist_remove(DoubleLinked.DoubleLinkedElmt<DATA> element) {
                DATA data = null;

                //Do not allow a NULL element or removal from an empty list.
                if (element == null || this.size() == 0) throw new LinkedOperationException(
                    "Do not allow a NULL element or removal from an empty list.");

                //Remove the element from the list.
                data = element.data();

                if (element.equals(this.head)) {
                    //Handle removal from the head of the list.
                    this.head = element.next();

                    if (this.head == null) this.tail = null;
                    else element.next().setPrev(null);
                }

                //Free the storage allocated by the abstract datatype.
                element.free();

                //Adjust the size of the list to account for the removed element.
                this.size--;
                this.actionCount++;

                return data;
            }

            @SuppressWarnings("unchecked")
            @Override
            public boolean equals(Object obj) {
                DoubleLinked<DATA> that;
                if (obj instanceof DoubleLinked) {
                    that = (DoubleLinked<DATA>) obj;
                    return this.size == that.size() && Objects.equals(this.head() , that.head())
                        && Objects.equals(this.tail() , that.tail());
                } else return false;
            }

            @Override
            public Function<DATA , DoubleLinked.DoubleLinkedElmt<DATA>> getElmtFactory(DATA data) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int hashCode() {
                return Objects.hash(this.head , this.size , this.tail);
            }

            @Override
            public DoubleLinked.DoubleLinkedElmt<DATA> head() {
                return this.head;
            }

            @Override
            public void insNext(DoubleLinked.DoubleLinkedElmt<DATA> element , final DATA data) {
                //Do not allow a NULL element unless the list is empty.
                if (element == null && this.size() != 0) throw new LinkedOperationException(
                    "Do not allow a NULL element unless the list is empty.");

                DoubleLinked.DoubleLinkedElmt<DATA> new_element =
                    DoubleLinked.DoubleLinkedElmt.<DATA> defaultElmt(data);

                if (this.size() == 0) {
                    //Handle insertion when the list is empty.
                    this.head = new_element;
                    this.head.setPrev(null);
                    this.head.setNext(null);
                    this.tail = new_element;
                } else {
                    //Handle insertion when the list is not empty.
                    new_element.setNext(element.next());
                    new_element.setPrev(element);

                    if (element.next() == null) this.tail = new_element;
                    else element.next().setPrev(new_element);

                    element.setNext(new_element);
                }

                //Adjust the size of the list to account for the inserted element.
                this.size++;
                this.actionCount++;

            }

            @Override
            public Iterator<DATA> iterator() {
                // TODO Auto-generated method stub
                return new Iterator<DATA>() {
                    private DoubleLinked.DoubleLinkedElmt<DATA> index = head;
                    private final long myActioncount = actionCount;

                    @Override
                    public boolean hasNext() {
                        if (this.myActioncount != actionCount) throw new ConcurrentModificationException();
                        return this.index != null;
                    }

                    @Override
                    public DATA next() {
                        DoubleLinked.DoubleLinkedElmt<DATA> result = this.index;
                        this.index = this.index.next();
                        return result.data();
                    }
                };
            }

            @Override
            public DATA remNext(DoubleLinkedElmt<DATA> element) {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public int size() {
                return this.size;
            }

            @Override
            public DoubleLinked.DoubleLinkedElmt<DATA> tail() {
                return this.tail;
            }

        };
    }

    public void dlist_ins_prev(DoubleLinkedElmt<D> element , final D data);

    public D dlist_remove(DoubleLinkedElmt<D> element);
}
