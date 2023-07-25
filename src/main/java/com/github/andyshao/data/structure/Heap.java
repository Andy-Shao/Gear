package com.github.andyshao.data.structure;

import com.github.andyshao.lang.AutoIncreaseArray;
import com.github.andyshao.lang.Cleanable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;

/**
 * 
 * Title:<br>
 * Descript: the default sequence is from the smallest item to the biggest item.
 * <br>
 * Copyright: Copryright(c) Feb 17, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 */
public interface Heap<D> extends Cleanable , Serializable {
    /**
     * Default heap
     * @param <DATA> data type
     */
    public class MyHeap<DATA> implements Heap<DATA> {
        @Serial
        private static final long serialVersionUID = -4077792585328556219L;
        /**{@link Comparator}*/
        private Comparator<DATA> comparator = (obj1 , obj2) -> {
            return 0;
        };
        /**tree*/
        protected AutoIncreaseArray<DATA> tree = new AutoIncreaseArray<DATA>();

        @Override
        public void clear() {
            this.tree.clear();
        }

        @Override
        public DATA extract() {
            int ipos , lpos , rpos , mpos;
            DATA result = null , save , temp;

            if (this.size() == 0) throw new HeapOperationException("Do not allow extraction from an empty heap.");

            //Extract the node at the top of the heap.
            result = this.tree.get(0);

            //Ajust the storage used by the heap.
            if (this.tree.size() - 1 > 0) save = this.tree.remove(this.tree.size() - 1);
            //Manage the heap when extracting the last node.
            else {
                save = this.tree.get(0);
                this.clear();
                return save;
            }

            //Copy the last node to the top.
            this.tree.set(save , 0);

            //Heapify the tree by pushing the contents of the new top downward.
            ipos = 0;

            while (true) {
                //Select the child to swap with the current node.
                lpos = Heap.left(ipos);
                rpos = Heap.right(ipos);

                if (lpos < this.size() && this.comparator.compare(this.tree.get(lpos) , this.tree.get(ipos)) < 0) mpos = lpos;
                else mpos = ipos;

                if (rpos < this.size() && this.comparator.compare(this.tree.get(rpos) , this.tree.get(mpos)) < 0) mpos = rpos;

                //When mpos is ipos, the heap property has been restored.
                if (mpos == ipos) break;
                else {
                    //Swap the contents of the current node and the selected child.
                    temp = this.tree.get(mpos);
                    this.tree.set(this.tree.get(ipos) , mpos);
                    this.tree.set(temp , ipos);

                    //Move down one level in the tree to continue heapifying.
                    ipos = mpos;
                }
            }

            return result;
        }

        @Override
        public void insert(DATA data) {
            int ipos , ppos;
            DATA temp;

            //Insert the node after the last node.
            ipos = this.tree.addTail(data);

            //Heapify the tree by pushing the contents of the new node upward.
            ppos = Heap.parent(ipos);

            while (ipos > 0 && this.comparator.compare(this.tree.get(ppos) , this.tree.get(ipos)) > 0) {
                //Swap the contents of the current node and its parent.
                temp = this.tree.get(ppos);
                this.tree.set(this.tree.get(ipos) , ppos);
                this.tree.set(temp , ipos);

                //Move up one level in the tree to continue heapifying.
                ipos = ppos;
                ppos = Heap.parent(ipos);
            }
        }

        @Override
        public void setComparator(Comparator<DATA> comparator) {
            this.comparator = comparator;
        }

        @Override
        public int size() {
            return this.tree.size();
        }

    }

    /**
     * Get the default {@link Heap}
     * @param comparator {@link Comparator}
     * @return {@link Heap}
     * @param <DATA> data type
     */
    public static <DATA> Heap<DATA> defaultHeap(Comparator<DATA> comparator) {
        Heap<DATA> result = new MyHeap<DATA>();
        result.setComparator(comparator);
        return result;
    }

    /**
     * the left position of node
     * @param npos the node position
     * @return the left position of the node
     */
    public static int left(int npos) {
        return (npos * 2) + 1;
    }

    /**
     * the parent position of the node
     * @param npos the position of the node
     * @return the parent position
     */
    public static int parent(int npos) {
        return (npos - 1) / 2;
    }

    /**
     * the right position of the node
     * @param npos the node position
     * @return the right position
     */
    public static int right(int npos) {
        return (npos * 2) + 2;
    }

    /**
     * get the data from the top
     * @return the data
     */
    public D extract();

    /**
     * insert the data
     * @param data the data
     */
    public void insert(final D data);

    /**
     * Set {@link Comparator}
     * @param comparator {@link Comparator}
     */
    public void setComparator(Comparator<D> comparator);

    /**
     * Get the size of the {@link Graph}
     * @return size number
     */
    public int size();
}
