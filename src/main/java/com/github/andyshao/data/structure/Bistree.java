package com.github.andyshao.data.structure;

import java.util.Comparator;

import com.github.andyshao.data.structure.Bitree.BitreeNode;
import com.github.andyshao.lang.Cleanable;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 17, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <DATA> data
 */
public interface Bistree<DATA> extends Cleanable , Tree<Bistree.AvlNode<DATA>> {
    public interface AvlNode<D> {
        public static <DATA> AvlNode<DATA> DEFAULT_AVL_NODE() {
            return new AvlNode<DATA>() {
                private volatile DATA data;
                private volatile int factor;
                private volatile boolean hidden;

                @Override
                public DATA data() {
                    return this.data;
                }

                @Override
                public void date(DATA data) {
                    this.data = data;
                }

                @Override
                public int factor() {
                    return this.factor;
                }

                @Override
                public void factor(int factor) {
                    this.factor = factor;
                }

                @Override
                public boolean hidden() {
                    return this.hidden;
                }

                @Override
                public void hidden(boolean hidden) {
                    this.hidden = hidden;
                }
            };
        }

        public D data();

        public void date(D data);

        public int factor();

        public void factor(int factor);

        public boolean hidden();

        public void hidden(boolean hidden);
    }

    @FunctionalInterface
    public interface AvlNodeFactory<D , T extends AvlNode<D>> {
        public T build();
    }

    public class MyBistree<D> implements Bistree<D> {
        private final AvlNodeFactory<D , AvlNode<D>> avlNodeFactory;
        private final Bitree<AvlNode<D>> bitree;
        private Comparator<D> comparator = (obj1 , obj2) -> {
            return 0;
        };

        public MyBistree(Bitree<AvlNode<D>> bitree , AvlNodeFactory<D , AvlNode<D>> avlNodeFactory) {
            this.avlNodeFactory = avlNodeFactory;
            this.bitree = bitree;
        }

        @Override
        public Ret<D> bistree_insert(D data) {
            return this.insert(this.root() , data);
        }

        @Override
        public Ret<D> bistree_lookup(D data) {
            return this.lookup(this.root() , data);
        }

        @Override
        public Ret<D> bistree_remove(D data) {
            return this.hide(this.root() , data);
        }

        @Override
        public void clear() {
            this.bitree.clear();
        }

        @Override
        public void destroy_left(Bitree.BitreeNode<Bistree.AvlNode<D>> node) {
            this.bitree.remLeft(node);
        }

        @Override
        public void destroy_right(Bitree.BitreeNode<Bistree.AvlNode<D>> node) {
            this.bitree.remRight(node);
        }

        public Ret<D> hide(BitreeNode<AvlNode<D>> node , final D data) {
            Ret<D> result = null;
            int cmpval;

            if (Bitree.isEob(node)) throw new TreeOperationException("the node is not allowed to be null.");

            cmpval = this.comparator.compare(data , node.data().data());
            if (cmpval < 0) //Move to the left.
            result = this.hide(node.left() , data);
            else if (cmpval > 0) //Move to the right.
            result = this.hide(node.right() , data);
            else {
                //Mark the node as hidden.
                node.data().hidden(true);
                result = new Ret<D>();
                result.elmt = node;
                result.data = node.data().data();
            }

            return result;
        }

        public Ret<D> insert(BitreeNode<AvlNode<D>> node , D data) {
            AvlNode<D> avl_data;
            int cmpval;
            Ret<D> result = new Ret<D>();
            result.data = data;
            result.balance = 0;

            //Insert the data into the tree.
            if (Bitree.isEob(node)) {
                //Handle insertion into an empty tree.
                avl_data = this.avlNodeFactory.build();

                avl_data.factor(Bistree.AVL_BALANCED);
                avl_data.hidden(false);
                avl_data.date(data);

                result.balance = 1;
                result.elmt = this.bitree.insLeft(node , avl_data);
                return result;
            } else {
                //Handle insertion into a tree that is not empty.
                cmpval = this.comparator.compare(data , node.data().data());

                if (cmpval < 0) {
                    //Move to the left.
                    if (Bitree.isEob(node.left())) {
                        avl_data = this.avlNodeFactory.build();

                        avl_data.factor(Bistree.AVL_BALANCED);
                        avl_data.hidden(false);
                        avl_data.date(data);

                        result.elmt = this.bitree.insLeft(node , avl_data);
                        result.balance = 0;
                        return result;
                    } else result = this.insert(node.left() , data);

                    //Ensure that the tree remains balanced.
                    if (result.balance != 0) switch (node.data().factor()) {
                    case AVL_LFT_HEAVY:
                        Bistree.rotate_left(node);
                        result.balance = 1;
                        break;
                    case AVL_BALANCED:
                        node.data().factor(Bistree.AVL_LFT_HEAVY);
                        break;
                    case AVL_RGT_HEAVY:
                        node.data().factor(Bistree.AVL_BALANCED);
                        result.balance = 1;
                        break;
                    }
                } else if (cmpval > 0) {
                    //Move to the right
                    if (Bitree.isEob(node.right())) {
                        avl_data = this.avlNodeFactory.build();

                        avl_data.factor(Bistree.AVL_BALANCED);
                        avl_data.hidden(false);
                        avl_data.date(data);

                        result.elmt = this.bitree.insRight(node , avl_data);
                        result.balance = 0;
                    } else result = this.insert(node.right() , data);

                    //Ensure that the tree remains balanced.
                    if (result.balance != 0) switch (node.data().factor()) {
                    case AVL_LFT_HEAVY:
                        node.data().factor(Bistree.AVL_BALANCED);
                        result.balance = 1;
                        break;
                    case AVL_BALANCED:
                        node.data().factor(Bistree.AVL_RGT_HEAVY);
                        break;
                    case AVL_RGT_HEAVY:
                        Bistree.rotate_right(node);
                        result.balance = 1;
                    }
                } else //Handle finding a copy of the data.
                //Do nothing since the data is in the tree and not hidden.
                if (node.data().hidden()) {
                    //Insert the new data and mark it as not hidden.
                    node.data().date(data);
                    node.data().hidden(false);

                    //Do not rebalance because the tree structure is unchanged.
                    result.balance = 1;
                }
            }

            return result;
        }

        public Ret<D> lookup(BitreeNode<AvlNode<D>> node , D data) {
            Ret<D> result = null;
            int cmpval;

            if (Bitree.isEob(node)) throw new TreeOperationException("node is not allowed to be null.");

            cmpval = this.comparator.compare(data , node.data().data());
            if (cmpval < 0) //Move to the left.
            result = this.lookup(node.left() , data);
            else if (cmpval > 0) //Move to the right.
            result = this.lookup(node.right() , data);
            else if (!node.data().hidden()) {
                result = new Ret<D>();
                result.data = node.data().data();
                result.elmt = node;
            }

            return result;
        }

        @Override
        public BitreeNode<Bistree.AvlNode<D>> root() {
            return this.bitree.root();
        }

        @Override
        public void setComparator(Comparator<D> comparator) {
            this.comparator = comparator;
        }

        @Override
        public int size() {
            return this.bitree.size();
        }

    }

    public class Ret<D> {
        public int balance;
        public D data;
        public BitreeNode<AvlNode<D>> elmt;
    }

    /** balanced */
    public static final int AVL_BALANCED = 0;

    /** left heavy */
    public static final int AVL_LFT_HEAVY = 1;

    /** right heavy */
    public static final int AVL_RGT_HEAVY = -1;

    public static <D> Bistree<D> defaultBistree(
        Bitree<AvlNode<D>> bitree , AvlNodeFactory<D , AvlNode<D>> avlNodeFactory , Comparator<D> comparator) {
        Bistree<D> bistree = new Bistree.MyBistree<>(bitree , avlNodeFactory);
        bistree.setComparator(comparator);
        return bistree;
    }

    public static <D> BitreeNode<AvlNode<D>> rotate_left(BitreeNode<AvlNode<D>> node) {
        BitreeNode<AvlNode<D>> left , grandchild;

        left = node.left();
        if (left.data().factor() == Bistree.AVL_LFT_HEAVY) {
            //Perform an LL rotation.
            node.left(left.right());
            left.right(node);
            node.data().factor(Bistree.AVL_BALANCED);
            left.data().factor(Bistree.AVL_BALANCED);
            node = left;
        } else {
            //Perform an LR rotation.
            grandchild = left.right();
            left.right(grandchild.left());
            grandchild.left(left);
            node.left(grandchild.right());
            grandchild.right(node);

            switch (grandchild.data().factor()) {
            case AVL_LFT_HEAVY:
                node.data().factor(Bistree.AVL_RGT_HEAVY);
                left.data().factor(Bistree.AVL_BALANCED);
                break;
            case AVL_BALANCED:
                node.data().factor(Bistree.AVL_BALANCED);
                left.data().factor(Bistree.AVL_BALANCED);
                break;
            case AVL_RGT_HEAVY:
                node.data().factor(Bistree.AVL_BALANCED);
                left.data().factor(Bistree.AVL_LFT_HEAVY);
            }

            grandchild.data().factor(Bistree.AVL_BALANCED);
            node = grandchild;
        }

        return node;
    }

    public static <D> BitreeNode<AvlNode<D>> rotate_right(BitreeNode<AvlNode<D>> node) {
        BitreeNode<AvlNode<D>> right , grandchild;
        right = node.right();

        if (right.data().factor() == Bistree.AVL_RGT_HEAVY) {
            //Perform an RR rotation
            node.right(right.left());
            right.left(node);
            node.data().factor(Bistree.AVL_BALANCED);
            right.data().factor(Bistree.AVL_BALANCED);
            node = right;
        } else {
            //Perform an RL rotation
            grandchild = right.left();
            right.left(grandchild.right());
            grandchild.right(right);
            node.right(grandchild.left());
            grandchild.left(node);

            switch (grandchild.data().factor()) {
            case AVL_LFT_HEAVY:
                node.data().factor(Bistree.AVL_BALANCED);
                right.data().factor(Bistree.AVL_RGT_HEAVY);
                break;
            case AVL_BALANCED:
                node.data().factor(Bistree.AVL_BALANCED);
                right.data().factor(Bistree.AVL_BALANCED);
                break;
            case AVL_RGT_HEAVY:
                node.data().factor(Bistree.AVL_LFT_HEAVY);
                right.data().factor(Bistree.AVL_BALANCED);
                break;
            }
            grandchild.data().factor(Bistree.AVL_BALANCED);
            node = grandchild;
        }

        return node;
    }

    /**
     * Insert a data.<br>
     * Check the tree. If the tree is empty then insert and set the balance is
     * AVL_BALANCED. If it isn't empty. First compare with occurrent node and
     * decide which side we should move(left or right). If find out the right
     * site
     * then create a new {@link AvlNode} and insert the {@link AvlNode}. If the
     * old site
     * has the old data and it is hidden, then insert the new data. If the old
     * site is not
     * hidden do nothing.<br>
     * After the insert, check out the balance. if the balance over absolute
     * number 2 then it
     * should take a rotation.<br>
     * 
     * @param data the date
     * @return return the balance.
     */
    public Ret<DATA> bistree_insert(final DATA data);

    public Ret<DATA> bistree_lookup(final DATA data);

    /**
     * if the data is removed. The node of {@link AvlNode#hidden()} is true.<br>
     * the size of tree will not be reduced. but you can't lookup the node which
     * is removed.
     * 
     * @param data the data which should be removed.
     * @return the node which is removed.
     */
    public Ret<DATA> bistree_remove(final DATA data);

    public void destroy_left(BitreeNode<AvlNode<DATA>> node);

    public void destroy_right(BitreeNode<AvlNode<DATA>> node);

    public void setComparator(Comparator<DATA> comparator);

    @Override
    public int size();
}
