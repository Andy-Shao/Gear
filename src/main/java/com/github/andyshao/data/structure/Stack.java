package com.github.andyshao.data.structure;

import com.github.andyshao.util.CollectionModel;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

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
public interface Stack<D> extends CollectionModel<D> {
    /**
     * Default stack
     * @param <DATA> data type
     * @param <E> {@link com.github.andyshao.data.structure.Linked.LinkedElmt} type
     */
    public class MyStack<DATA , E extends Linked.LinkedElmt<DATA , E>> implements Stack<DATA> {
        class MyIterator implements Iterator<DATA>, Serializable {
            @Serial
            private static final long serialVersionUID = 594173755450325084L;
            private final DATA[] data = initArray();
            private int index = 0;

            @SuppressWarnings("unchecked")
            private DATA[] initArray() {
                if(MyStack.this.linked.size() == 0) return null;
                return MyStack.this.linked.toArray((DATA[]) Array.newInstance(MyStack.this.linked.head().data().getClass() , MyStack.this.linked.size()));
            }
            @Override
            public boolean hasNext() {
                return data == null ? false : this.index < MyStack.this.linked.size();
            }

            @Override
            public DATA next() {
                return this.data[this.index++];
            }

        }

        /**linked*/
        private final Linked<DATA , E> linked;

        /**
         * build {@link MyStack}
         * @param linked {@link Linked}
         */
        public MyStack(Linked<DATA , E> linked) {
            this.linked = linked;
        }

        @Override
        public boolean add(DATA e) {
            this.push(e);
            return true;
        }

        @Override
        public void clear() {
            this.linked.clear();
        }

        @Override
        public Iterator<DATA> iterator() {
            return new MyIterator();
        }

        @Override
        public DATA peek() {
            return this.linked.head() == null ? null : this.linked.head().data();
        }

        @Override
        public DATA pop() {
            return this.linked.remNext(null);
        }

        @Override
        public void push(DATA data) {
            this.linked.insNext(null , data);
        }

        @Override
        public boolean remove(Object o) {
            return this.linked.remove(o);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return this.linked.retainAll(c);
        }

        @Override
        public int size() {
            return this.linked.size();
        }
    }

    /**
     * Get the default {@link Stack}
     * @return {@link Stack}
     * @param <DATA> data type
     * @param <E> {@link com.github.andyshao.data.structure.Linked.LinkedElmt} type
     */
    public static <DATA , E extends Linked.LinkedElmt<DATA , E>> Stack<DATA> defaultStack() {
        return Stack.defaultStack(SingleLinked.defaultSingleLinked());
    }

    /**
     * Get the default {@link Stack} from the {@link Linked}
     * @param linked {@link Linked}
     * @return {@link Stack}
     * @param <DATA> data type
     * @param <E> {@link com.github.andyshao.data.structure.Linked.LinkedElmt} type
     */
    public static <DATA , E extends Linked.LinkedElmt<DATA , E>> Stack<DATA> defaultStack(Linked<DATA , E> linked) {
        return new MyStack<>(linked);
    }

    /**
     * Get the element but does not eliminate it
     * @return the data
     */
    public D peek();

    /**
     * Get the element and omit it
     * @return the data
     */
    public D pop();

    /**
     * Add the element
     * @param data the data
     */
    public void push(final D data);

    @Override
    public int size();

}
