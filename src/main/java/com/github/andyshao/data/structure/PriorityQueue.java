package com.github.andyshao.data.structure;

import com.github.andyshao.data.structure.Heap.MyHeap;
import com.github.andyshao.lang.Cleanable;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 18, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 */
public interface PriorityQueue<D> extends Cleanable {

    /**
     * Default priority queue
     * @param <DATA> data type
     */
    public class MyPriorityQueue<DATA> extends MyHeap<DATA> implements PriorityQueue<DATA> {

        @Override
        public DATA peek() {
            return this.tree.size() == 0 ? null : this.tree.get(0);
        }

    }

    /**
     * GET the top element from the {@link PriorityQueue} and remove it
     * @return data
     */
    public D extract();

    /**
     * Insert data into the {@link PriorityQueue}
     * @param data data
     */
    public void insert(D data);

    /**
     * Get the top element from the {@link PriorityQueue}, but
     * do not omit it
     * @return data
     */
    public D peek();

    /**
     * The size of the {@link PriorityQueue}
     * @return the size number
     */
    public int size();
}
