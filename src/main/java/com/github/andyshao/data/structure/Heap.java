package com.github.andyshao.data.structure;

import java.util.Comparator;

import com.github.andyshao.lang.AutoIncreaseArray;
import com.github.andyshao.lang.Cleanable;

/**
 * 
 * Title:<br>
 * Descript: the default sequence is from the smallest item to the biggest item.<br>
 * Copyright: Copryright(c) Feb 17, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 */
public interface Heap<D> extends Cleanable {
    public class MyHeap<DATA> implements Heap<DATA> {
        private Comparator<DATA> comparator = (obj1 , obj2) -> {
            return 0;
        };
        protected AutoIncreaseArray<DATA> tree = new AutoIncreaseArray<DATA>();

        @Override
        public void clear() {
            this.tree.clear();
        }

        @Override
        public DATA heap_extract() {
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
            lpos = Heap.heap_left(ipos);
            rpos = Heap.heap_right(ipos);

            while (true) {
                //Select the child to swap with the current node.
                lpos = Heap.heap_left(ipos);
                rpos = Heap.heap_right(ipos);

                if (lpos < this.size() && this.comparator.compare(this.tree.get(lpos) , this.tree.get(ipos)) < 0) mpos =
                    lpos;
                else mpos = ipos;

                if (rpos < this.size() && this.comparator.compare(this.tree.get(rpos) , this.tree.get(mpos)) < 0) mpos =
                    rpos;

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
        public void heap_insert(DATA data) {
            int ipos , ppos;
            DATA temp;

            //Insert the node after the last node.
            ipos = this.tree.addTail(data);

            //Heapify the tree by pushing the contents of the new node upward.
            ppos = Heap.heap_parent(ipos);

            while (ipos > 0 && this.comparator.compare(this.tree.get(ppos) , this.tree.get(ipos)) > 0) {
                //Swap the contents of the current node and its parent.
                temp = this.tree.get(ppos);
                this.tree.set(this.tree.get(ipos) , ppos);
                this.tree.set(temp , ipos);

                //Move up one level in the tree to continue heapifying.
                ipos = ppos;
                ppos = Heap.heap_parent(ipos);
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

    public static <DATA> Heap<DATA> defaultHeap(Comparator<DATA> comparator) {
        Heap<DATA> result = new MyHeap<DATA>();
        result.setComparator(comparator);
        return result;
    }

    public static int heap_left(int npos) {
        return (npos * 2) + 1;
    }

    public static int heap_parent(int npos) {
        return (npos - 1) / 2;
    }

    public static int heap_right(int npos) {
        return (npos * 2) + 2;
    }

    public D heap_extract();

    public void heap_insert(final D data);

    public void setComparator(Comparator<D> comparator);

    public int size();
}
