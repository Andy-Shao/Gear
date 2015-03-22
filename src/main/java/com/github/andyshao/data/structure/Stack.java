package com.github.andyshao.data.structure;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

import com.github.andyshao.util.CollectionModel;

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
    public class MyStack<DATA , E extends Linked.LinkedElmt<DATA , E>> implements Stack<DATA> {
        private final Linked<DATA , E> linked;

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
        
        class MyIterator implements Iterator<DATA>{
            private int index = 0;
            @SuppressWarnings("unchecked")
            private final DATA[] data = linked.toArray((DATA[]) Array.newInstance(linked.head().data().getClass() , linked.size()));

            @Override
            public boolean hasNext() {
                return this.index < linked.size();
            }

            @Override
            public DATA next() {
                return this.data[this.index++];
            }
            
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

    public static <DATA , E extends Linked.LinkedElmt<DATA , E>> Stack<DATA> defaultStack(Linked<DATA , E> linked) {
        return new MyStack<>(linked);
    }

    public D peek();

    public D pop();

    public void push(final D data);

    @Override
    public int size();

}
