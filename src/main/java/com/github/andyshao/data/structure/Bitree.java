package com.github.andyshao.data.structure;

import com.github.andyshao.lang.Cleanable;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 11, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 */
public interface Bitree<D> extends Cleanable{
    public interface BitreeNode<DATA>{
        public DATA data();

        public void data(DATA data);

        public BitreeNode<DATA> left();

        public void left(BitreeNode<DATA> left);

        public BitreeNode<DATA> right();

        public void right(BitreeNode<DATA> right);
        
        public static <D> BitreeNode<D> DEFAULT_BITREE_NODE() {
            return new BitreeNode<D>() {
                private D data;
                private BitreeNode<D> left;
                private BitreeNode<D> right;

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
            };
        }
    }

    public class MyBitree<DATA> implements Bitree<DATA> {
        protected BitreeNode<DATA> root;
        protected int size;
        protected final TreeNodeFactory<DATA , BitreeNode<DATA>> treeNodeFactory;

        public MyBitree(TreeNodeFactory<DATA , BitreeNode<DATA>> treeNodeFactory) {
            this.treeNodeFactory = treeNodeFactory;
        }

        public MyBitree(
            TreeNodeFactory<DATA , BitreeNode<DATA>> treeNodeFactory , Bitree<DATA> left , Bitree<DATA> right , DATA data) {
            this.treeNodeFactory = treeNodeFactory;
            this.bitree_ins_left(null , data);

            //Merge the two binary trees into a single binary tree.
            this.root.left(left.root());
            this.root.right(right.root());

            //Adjust the size of the new binary tree.
            this.size = this.size + left.size() + right.size();
        }

        @Override
        public BitreeNode<DATA> bitree_ins_left(BitreeNode<DATA> node , DATA data) throws TreeOperationException {
            BitreeNode<DATA> new_node;

            //Determine where to insert the node.
            if (node == null) {
                //Allow insertion at the root only in an empty tree.
                if (this.size > 0) throw new TreeOperationException("node is null & the size of tree bigger than 0.");
            } else {
                //Normally allow insertion only at the end of a branch.
                if (node.left() != null) throw new TreeChildNodeNotEmptyException("the left of node's is not empty");
            }

            //Allocate storage for the node.
            new_node = this.treeNodeFactory.build();

            //Insert the node into the tree.
            new_node.data(data);
            new_node.left(null);
            new_node.right(null);
            if (node == null) this.root = new_node;
            else node.left(new_node);

            //Adjust the size of the tree to account for the inserted node.
            this.size++;

            return new_node;
        }

        @Override
        public BitreeNode<DATA> bitree_ins_right(BitreeNode<DATA> node , DATA data) {
            BitreeNode<DATA> new_node;

            if (node == null) {
                if (this.size > 0) throw new TreeOperationException("node is null & the size of tree bigger than 0.");
            } else {
                if (node.right() != null) throw new TreeChildNodeNotEmptyException(
                    "the right child of node's is not null");
            }

            //Allocate storage for the node.
            new_node = this.treeNodeFactory.build();
            new_node.data(data);
            new_node.left(null);
            new_node.right(null);
            if (node == null) this.root = new_node;
            else node.right(new_node);

            //Adjust the size of the tree to account for the inserted node.
            this.size++;

            return new_node;
        }

        @Override
        public void bitree_rem_left(BitreeNode<DATA> node) {
            BitreeNode<DATA> position;

            //Do not allow removal from an empty tree.
            if (this.size == 0) throw new TreeIsEmptyException();

            //Determine where to remove nodes.
            if (node == null) position = this.root;
            else position = node.left();

            if (position != null) {
                this.bitree_rem_left(position);
                this.bitree_rem_right(position);
                if (node == null) this.root = null;
                else node.left(null);
            }

            //Adjust the size of the tree to account for the removed node.
            this.size--;
        }

        @Override
        public void bitree_rem_right(BitreeNode<DATA> node) {
            BitreeNode<DATA> position;

            //Do not allow removal from an empty tree.
            if (this.size == 0) throw new TreeIsEmptyException();

            //Determine where to remove nodes.
            if (node == null) position = this.root;
            else position = node.right();

            //Remove the nodes.
            if (position != null) {
                this.bitree_rem_left(position.left());
                this.bitree_rem_right(position.right());
                if (node == null) this.root = null;
                else node.right(null);
            }

            //Adjust the size of the tree to account for the removed node.
            this.size--;
        }

        @Override
        public void clean() {
            this.root = null;
            this.size = 0;
        }

        @Override
        public BitreeNode<DATA> root() {
            return this.root;
        }

        @Override
        public int size() {
            return this.size;
        }

    }

    public static <DATA> Bitree<DATA> BITREE_MERGE(
        TreeNodeFactory<DATA , BitreeNode<DATA>> treeNodeFactory , Bitree<DATA> left , Bitree<DATA> right , DATA data) {
        return new Bitree.MyBitree<>(treeNodeFactory , left , right , data);
    }

    public static <DATA> Bitree<DATA> DEFAULT_BIT_TREE(TreeNodeFactory<DATA , BitreeNode<DATA>> treeNodeFactory) {
        return new Bitree.MyBitree<>(treeNodeFactory);
    }

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
    public BitreeNode<D> bitree_ins_left(BitreeNode<D> node , D data) throws TreeOperationException;

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
    public BitreeNode<D> bitree_ins_right(BitreeNode<D> node , D data) throws TreeOperationException;

    /**
     * remove the left child of node's.
     * 
     * @param node bit tree node
     * @throws TreeIsEmptyException if the tree is empty
     * @throws TreeOperationException others exception of action'
     */
    public void bitree_rem_left(BitreeNode<D> node) throws TreeOperationException;

    /**
     * remove the right child of node's.
     * 
     * @param node bit tree node
     * @throws TreeIsEmptyException if the tree is empty
     * @throws TreeOperationException other exception of action'
     */
    public void bitree_rem_right(BitreeNode<D> node) throws TreeOperationException;
    
    public BitreeNode<D> root();

    public int size();

    @Override
    public default void clean() {
        //Remove all the nodes from the tree.
        this.bitree_rem_left(null);
    }

    public static
        <DATA , E extends Linked.LinkedElmt<DATA , E> , T extends Linked<DATA , E> & SingleLinkedOperation<DATA , E>>
        Linked<DATA , E> preorder(final BitreeNode<DATA> node , final T linked) {
        //Load the list with a preorder listing of the tree.
        if (!Bitree.bitree_is_eob(node)) {
            linked.list_ins_next(linked.tail() , node.data());
            if (!Bitree.bitree_is_eob(node.left())) Bitree.preorder(node.left() , linked);
            if (!Bitree.bitree_is_eob(node.right())) Bitree.preorder(node.right() , linked);
        }
    
        return linked;
    }

    public static
        <DATA , E extends Linked.LinkedElmt<DATA , E> , T extends Linked<DATA , E> & SingleLinkedOperation<DATA , E>>
        Linked<DATA , E> postorder(final BitreeNode<DATA> node , final T linked) {
        //Load the list with an inorder listing of the tree.
        if (!Bitree.bitree_is_eob(node)) {
            if (!Bitree.bitree_is_eob(node.left())) Bitree.postorder(node.left() , linked);
            if (!Bitree.bitree_is_eob(node.right())) Bitree.postorder(node.right() , linked);
            linked.list_ins_next(linked.tail() , node.data());
        }
    
        return linked;
    }

    public static
        <DATA , E extends Linked.LinkedElmt<DATA , E> , T extends Linked<DATA , E> & SingleLinkedOperation<DATA , E>>
        Linked<DATA , E> inorder(final BitreeNode<DATA> node , final T linked) {
        //Load the list with an inorder listing of the tree.
        if (!Bitree.bitree_is_eob(node)) {
            if (!Bitree.bitree_is_eob(node.left())) Bitree.inorder(node.left() , linked);
            linked.list_ins_next(linked.tail() , node.data());
            if (!Bitree.bitree_is_eob(node.right())) Bitree.inorder(node.right() , linked);
        }
    
        return linked;
    }

    public static <DATA> boolean bitree_is_leaf(BitreeNode<DATA> node) {
        return node.left() == null && node.right() == null;
    }

    public static <DATA> boolean bitree_is_eob(BitreeNode<DATA> node) {
        return node == null;
    }
}
