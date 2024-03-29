package com.github.andyshao.data.structure;

import java.io.Serial;
import java.io.Serializable;
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
public interface DoubleLinked<D> extends Linked<D , DoubleLinked.DoubleLinkedElmt<D>>, Serializable {
    /**
     * Double linked element
     * @param <DATA> data type
     */
    public interface DoubleLinkedElmt<DATA> extends Linked.LinkedElmt<DATA , DoubleLinkedElmt<DATA>>, Serializable {
        /**
         * Get default {@link DoubleLinkedElmt}
         * @param data data
         * @return {@link DoubleLinkedElmt}
         * @param <DATA> data type
         */
        public static <DATA> DoubleLinkedElmt<DATA> defaultElmt(DATA data) {
            DoubleLinkedElmt<DATA> result = new DoubleLinkedElmt<DATA>() {
                @Serial
                private static final long serialVersionUID = -107307947887970784L;
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
                        return Objects.equals(this.data() , that.data()) && Objects.equals(this.next() , that.next()) && Objects.equals(this.getPrev() , that.getPrev());
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

        /**
         * Get previous {@link DoubleLinkedElmt}
         * @return {@link DoubleLinkedElmt}
         */
        public DoubleLinkedElmt<DATA> getPrev();

        /**
         * Set previous {@link DoubleLinkedElmt}
         * @param prev {@link DoubleLinkedElmt}
         */
        public void setPrev(DoubleLinkedElmt<DATA> prev);
    }

    /**
     * Default {@link DoubleLinked}
     * @param <DATA> data type
     */
    public class MyDoubleLinked<DATA> implements DoubleLinked<DATA> {
        @Serial
        private static final long serialVersionUID = 8162911921677856435L;
        /**action count*/
        private long actionCount = 0;
        /**element factory*/
        private final Function<DATA , DoubleLinked.DoubleLinkedElmt<DATA>> elmtFactory;
        /**head of the elements*/
        private DoubleLinked.DoubleLinkedElmt<DATA> head;
        /**size*/
        private int size;

        /**tail of the elements*/
        private DoubleLinked.DoubleLinkedElmt<DATA> tail;

        /**
         * Build {@link MyDoubleLinked}
         * @param elmtFactory {@link DoubleLinkedElmt} factory
         */
        public MyDoubleLinked(Function<DATA , DoubleLinked.DoubleLinkedElmt<DATA>> elmtFactory) {
            this.elmtFactory = elmtFactory;
        }

        @Override
        public void clear() {
            do
                this.remove(this.head);
            while (this.size != 0);
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object obj) {
            DoubleLinked<DATA> that;
            if (obj instanceof DoubleLinked) {
                that = (DoubleLinked<DATA>) obj;
                return this.size == that.size() && Objects.equals(this.head() , that.head()) && Objects.equals(this.tail() , that.tail());
            } else return false;
        }

        @Override
        public Function<DATA , DoubleLinked.DoubleLinkedElmt<DATA>> getElmtFactory() {
            return this.elmtFactory;
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
            if (element == null && this.size() != 0) throw new LinkedOperationException("Do not allow a NULL element unless the list is empty.");

            DoubleLinked.DoubleLinkedElmt<DATA> new_element = this.getElmtFactory().apply(data);

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
        public void insPrev(DoubleLinked.DoubleLinkedElmt<DATA> element , final DATA data) {
            //Do not allowed a NULL element unless the list is empty.
            if (element == null && this.size() != 0) throw new LinkedOperationException("Do not allowed a NULL element unless the list is empty.");

            DoubleLinked.DoubleLinkedElmt<DATA> new_element = this.getElmtFactory().apply(data);

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
        public Iterator<DATA> iterator() {
            return new Iterator<DATA>() {
                private DoubleLinked.DoubleLinkedElmt<DATA> index = MyDoubleLinked.this.head;
                private final long myActioncount = MyDoubleLinked.this.actionCount;

                @Override
                public boolean hasNext() {
                    if (this.myActioncount != MyDoubleLinked.this.actionCount) throw new ConcurrentModificationException();
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
            DoubleLinkedElmt<DATA> old_element = this.getElmtFactory().apply(null);
            DATA data = null;

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
        public DATA remove(DoubleLinked.DoubleLinkedElmt<DATA> element) {
            DATA data = null;

            //Do not allow a NULL element or removal from an empty list.
            if (element == null || this.size() == 0) throw new LinkedOperationException("Do not allow a NULL element or removal from an empty list.");

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

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public DoubleLinked.DoubleLinkedElmt<DATA> tail() {
            return this.tail;
        }
    }

    /**
     * Get default {@link DoubleLinked}
     * @return {@link DoubleLinked}
     * @param <DATA> data type
     */
    public static <DATA> DoubleLinked<DATA> defaultDoubleLinked() {
        return DoubleLinked.defaultDoubleLinked(DoubleLinkedElmt::defaultElmt);
    }

    /**
     * Get default {@link DoubleLinked}
     * @param elmtFactory {@link DoubleLinkedElmt} factory
     * @return {@link DoubleLinked}
     * @param <DATA> data type
     */
    public static <DATA> DoubleLinked<DATA> defaultDoubleLinked(Function<DATA , DoubleLinked.DoubleLinkedElmt<DATA>> elmtFactory) {
        return new MyDoubleLinked<>(elmtFactory);
    }

    /**
     * Insert in the previous position of the {@link DoubleLinkedElmt}
     * @param element {@link DoubleLinkedElmt}
     * @param data the data
     */
    public void insPrev(DoubleLinkedElmt<D> element , final D data);

    /**
     * Remove the {@link DoubleLinkedElmt}
     * @param element {@link DoubleLinkedElmt}
     * @return the data which is omitted
     */
    public D remove(DoubleLinkedElmt<D> element);
}
