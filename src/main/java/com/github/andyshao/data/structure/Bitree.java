package com.github.andyshao.data.structure;

import com.github.andyshao.data.structure.Bitree.BitreeNode;
import com.github.andyshao.lang.Cleanable;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 
 * Title: Binary tree<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 11, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 */
public interface Bitree<D> extends Cleanable , Tree<D,BitreeNode<D>> {
    /**
     * Binary tree node
     * @param <DATA> data type
     */
    public interface BitreeNode<DATA> extends TreeNode<DATA>{
        /**
         * Build the default binary tree node
         * @return {@link BitreeNode}
         * @param <D> data type
         */
        public static <D> BitreeNode<D> defaultBitreeNode() {
            return new BitreeNode<D>() {
                private D data;
                private BitreeNode<D> left;
                private BitreeNode<D> right;
                private BitreeNode<D> parent;

                @Override
                public D data() {
                    return this.data;
                }

                @Override
                public void data(D data) {
                    this.data = data;
                }

                @Override
                public BitreeNode<D> left() {
                    return this.left;
                }

                @Override
                public void left(BitreeNode<D> left) {
                    this.left = left;
                }

                @Override
                public BitreeNode<D> right() {
                    return this.right;
                }

                @Override
                public void right(BitreeNode<D> right) {
                    this.right = right;
                }

                @Override
                public BitreeNode<D> parent() {
                    return this.parent;
                }

                @Override
                public void parent(BitreeNode<D> parent) {
                    this.parent = parent;
                }
            };
        }

        /**
         * Get data
         * @return the data
         */
        public DATA data();

        /**
         * Set data
         * @param data the data
         */
        public void data(DATA data);

        /**
         * Get left Node
         * @return {@link BitreeNode}
         */
        public BitreeNode<DATA> left();

        /**
         * Set the left node
         * @param left {@link BitreeNode}
         */
        public void left(BitreeNode<DATA> left);

        /**
         * Get right node
         * @return {@link BitreeNode}
         */
        public BitreeNode<DATA> right();

        /**
         * Set right node
         * @param right {@link BitreeNode}
         */
        public void right(BitreeNode<DATA> right);

        /**
         * The parent node
         * @return {@link BitreeNode}
         */
        public BitreeNode<DATA> parent();

        /**
         * Set parent node
         * @param parent {@link BitreeNode}
         */
        public void parent(BitreeNode<DATA> parent);
    }

    /**
     * The criteria binary tree
     * @param <DATA> data type
     */
    public class MyBitree<DATA> implements Bitree<DATA> {
        protected BitreeNode<DATA> root;
        protected int size;
        protected final Supplier<BitreeNode<DATA>> treeNodeFactory;

        /**
         * build the default binary tree
         * @param treeNodeFactory {@link BitreeNode}
         */
        public MyBitree(Supplier<BitreeNode<DATA>> treeNodeFactory) {
            this.treeNodeFactory = treeNodeFactory;
        }

        /**
         * Build default binary tree
         * @param treeNodeFactory {@link BitreeNode}
         * @param left {@link Bitree}
         * @param right {@link Bitree}
         * @param data the data
         */
        public MyBitree(Supplier<BitreeNode<DATA>> treeNodeFactory , Bitree<DATA> left , Bitree<DATA> right , DATA data) {
            this.treeNodeFactory = treeNodeFactory;
            this.insLeft(null , data);

            //Merge the two binary trees into a single binary tree.
            this.root.left(left.root());
            this.root.right(right.root());

            //Adjust the size of the new binary tree.
            this.size = this.size + left.size() + right.size();
        }

        @Override
        public Bitree<DATA> bitreeMeger(Bitree<DATA> left , Bitree<DATA> right , DATA data) {
            return new MyBitree<DATA>(this.getTreeNodeFactory() , left , right , data);
        }

        @Override
        public void clear() {
            this.root = null;
            this.size = 0;
        }

        @Override
        public Supplier<BitreeNode<DATA>> getTreeNodeFactory() {
            return this.treeNodeFactory;
        }

        @Override
        public BitreeNode<DATA> insLeft(BitreeNode<DATA> node , DATA data) throws TreeOperationException {
            BitreeNode<DATA> new_node;

            //Determine where to insert the node.
            if (node == null) {
                //Allow insertion at the root only in an empty tree.
                if (this.size > 0) throw new TreeOperationException("node is null & the size of tree bigger than 0.");
            } else //Normally allow insertion only at the end of a branch.
            if (node.left() != null) throw new TreeChildNodeNotEmptyException("the left of node's is not empty");

            //Allocate storage for the node.
            new_node = this.treeNodeFactory.get();

            //Insert the node into the tree.
            new_node.data(data);
            new_node.left(null);
            new_node.right(null);
            if (node == null) this.root = new_node;
            else {
                node.left(new_node);
                new_node.parent(node);
            }

            //Adjust the size of the tree to account for the inserted node.
            this.size++;

            return new_node;
        }

        @Override
        public BitreeNode<DATA> insRight(BitreeNode<DATA> node , DATA data) {
            BitreeNode<DATA> new_node;

            if (node == null) {
                if (this.size > 0) throw new TreeOperationException("node is null & the size of tree bigger than 0.");
            } else if (node.right() != null) throw new TreeChildNodeNotEmptyException("the right child of node's is not null");

            //Allocate storage for the node.
            new_node = this.treeNodeFactory.get();
            new_node.data(data);
            new_node.left(null);
            new_node.right(null);
            if (node == null) this.root = new_node;
            else {
                node.right(new_node);
                new_node.parent(node);
            }

            //Adjust the size of the tree to account for the inserted node.
            this.size++;

            return new_node;
        }

        @Override
        public void remLeft(BitreeNode<DATA> node) {
            BitreeNode<DATA> position;

            //Do not allow removal from an empty tree.
            if (this.size == 0) throw new TreeIsEmptyException();

            //Determine where to remove nodes.
            if (node == null) position = this.root;
            else position = node.left();

            if (position != null) {
                this.remLeft(position);
                this.remRight(position);
                if (node == null) this.root = null;
                else node.left(null);
                position.parent(null);
            }

            //Adjust the size of the tree to account for the removed node.
            this.size--;
        }

        @Override
        public void remRight(BitreeNode<DATA> node) {
            BitreeNode<DATA> position;

            //Do not allow removal from an empty tree.
            if (this.size == 0) throw new TreeIsEmptyException();

            //Determine where to remove nodes.
            if (node == null) position = this.root;
            else position = node.right();

            //Remove the nodes.
            if (position != null) {
                this.remLeft(position.left());
                this.remRight(position.right());
                if (node == null) this.root = null;
                else node.right(null);
                position.parent(null);
            }

            //Adjust the size of the tree to account for the removed node.
            this.size--;
        }

        @Override
        public BitreeNode<DATA> root() {
            return this.root;
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public void root(BitreeNode<DATA> node) {
            this.root = node;
            final AtomicInteger numberOfTree = new AtomicInteger();
            Bitree.postorder(this.root , n -> numberOfTree.incrementAndGet());
            this.size = numberOfTree.intValue();
        }

    }

    /**
     * Get default binary tree
     * @return {@link Bitree}
     * @param <DATA> data type
     */
    public static <DATA> Bitree<DATA> defaultBitTree() {
        return Bitree.defaultBitTree(BitreeNode::defaultBitreeNode);
    }

    /**
     * Get default binary tree
     * @param treeNodeFactory {@link BitreeNode}
     * @return {@link Bitree}
     * @param <DATA> data type
     */
    public static <DATA> Bitree<DATA> defaultBitTree(Supplier<BitreeNode<DATA>> treeNodeFactory) {
        return new Bitree.MyBitree<>(treeNodeFactory);
    }

    /**
     * inorder foreach in {@link Bitree}
     * @param node {@link Bitree}
     * @param result the result {@link Collection}
     * @return {@link Collection}
     * @param <DATA> data type
     */
    public static <DATA> Collection<DATA> inorder(final BitreeNode<DATA> node , final Collection<DATA> result) {
        inorder(node , item -> result.add(item.data()));
        return result;
    }

    /**
     * inorder foreach the {@link Bitree}
     * @param node {@link Bitree}
     * @param consumer the consumer of the tree node
     * @param <DATA> data type
     */
    public static <DATA> void inorder(final BitreeNode<DATA> node, Consumer<BitreeNode<DATA>> consumer) {
        //Load the list with an inorder listing of the tree.
        if (!Bitree.isEob(node)) {
            if (!Bitree.isEob(node.left())) Bitree.inorder(node.left() , consumer);
            consumer.accept(node);
            if (!Bitree.isEob(node.right())) Bitree.inorder(node.right() , consumer);
        }
    }

    /**
     * is Eob
     * @param node {@link BitreeNode}
     * @return true, if it is Eob
     * @param <DATA> data type
     */
    public static <DATA> boolean isEob(BitreeNode<DATA> node) {
        return node == null;
    }

    /**
     * is leaf
     * @param node {@link BitreeNode}
     * @return true, if it is leaf
     * @param <DATA> data type
     */
    public static <DATA> boolean isLeaf(BitreeNode<DATA> node) {
        return node.left() == null && node.right() == null;
    }

    /**
     * post order foreach the {@link Bitree}
     * @param node {@link Bitree}
     * @param result the search sequence {@link Collection}
     * @return {@link Collection}
     * @param <DATA> data type
     */
    public static <DATA> Collection<DATA> postorder(final BitreeNode<DATA> node , final Collection<DATA> result) {
        postorder(node , item -> result.add(item.data()));
        return result;
    }

    /**
     * post order foreach the {@link Bitree}
     * @param node {@link Bitree}
     * @param consumer the consumer of the {@link Bitree} node
     * @param <DATA> data type
     */
    public static <DATA> void postorder(final BitreeNode<DATA> node , Consumer<BitreeNode<DATA>> consumer) {
        if (!Bitree.isEob(node)) {
            if (!Bitree.isEob(node.left())) Bitree.postorder(node.left() , consumer);
            if (!Bitree.isEob(node.right())) Bitree.postorder(node.right() , consumer);
            consumer.accept(node);
        }
    }

    /**
     * preorder foreach the {@link Bitree}
     * @param node {@link Bitree}
     * @param result the foreach sequence {@link Collection}
     * @return {@link Collection}
     * @param <DATA> data type
     */
    public static <DATA> Collection<DATA> preorder(final BitreeNode<DATA> node , final Collection<DATA> result) {
        preorder(node , item -> result.add(item.data()));
        return result;
    }

    /**
     * preorder foreach the {@link Bitree}
     * @param node {@link Bitree}
     * @param consumer the consumer of the {@link Bitree} node
     * @param <DATA> data type
     */
    public static <DATA> void preorder(BitreeNode<DATA> node , Consumer<BitreeNode<DATA>> consumer) {
        if (!Bitree.isEob(node)) {
            consumer.accept(node);
            if (!Bitree.isEob(node.left())) Bitree.preorder(node.left() , consumer);
            if (!Bitree.isEob(node.right())) Bitree.preorder(node.right() , consumer);
        }
    }

    /**
     * merge two {@link Bitree}s
     * @param left the left tree
     * @param right the right tree
     * @param data the root position data
     * @return new {@link Bitree}
     */
    public Bitree<D> bitreeMeger(Bitree<D> left , Bitree<D> right , D data);

    @Override
    public default void clear() {
        //Remove all the nodes from the tree.
        this.remLeft(null);
    }

    /**
     * Get tree node factory
     * @return {@link BitreeNode}
     */
    public Supplier<BitreeNode<D>> getTreeNodeFactory();

    /**
     * add a left for node<br>
     * if node is null, input a new data for root of tree's.
     * 
     * @param node bit tree node
     * @param data data
     * @return the left child of node's
     * @throws TreeChildNodeNotEmptyException if the left of node's is not
     *             empty.
     * @throws TreeOperationException others operation exception of the action's
     */
    public BitreeNode<D> insLeft(BitreeNode<D> node , D data) throws TreeOperationException;

    /**
     * add a right child for node<br>
     * if node is null, input a new data for root of tree's.
     * 
     * @param node bit tree node
     * @param data data
     * @return the right child of nod's
     * @throws TreeChildNodeNotEmptyException if the right child of node's is
     *             not empty.
     * @throws TreeOperationException other operation exception of the action's
     */
    public BitreeNode<D> insRight(BitreeNode<D> node , D data) throws TreeOperationException;

    /**
     * remove the left child of node's.
     * 
     * @param node bit tree node
     * @throws TreeIsEmptyException if the tree is empty
     * @throws TreeOperationException others exception of action'
     */
    public void remLeft(BitreeNode<D> node) throws TreeOperationException;

    /**
     * remove the right child of node's.
     * 
     * @param node bit tree node
     * @throws TreeIsEmptyException if the tree is empty
     * @throws TreeOperationException other exception of action'
     */
    public void remRight(BitreeNode<D> node) throws TreeOperationException;

    @Override
    public BitreeNode<D> root();
    
    @Override
    public int size();
}
