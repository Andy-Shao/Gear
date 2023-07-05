package com.github.andyshao.data.structure;

import com.github.andyshao.lang.Cleanable;

import java.io.Serializable;

/**
 * Tree
 * @param <D> data type
 * @param <N> Node type
 */
public interface Tree<D,N extends Tree.TreeNode<D>> extends Cleanable, Serializable {
    /**
     * Tree node
     * @param <DATA> data type
     */
    public interface TreeNode<DATA> extends Serializable {
        /**
         * Get the data
         * @return the data
         */
        public DATA data();

        /**
         * Set the data
         * @param data the data
         */
        public void data(DATA data);
    }

    @Override
    public abstract void clear();

    /**
     * The root {@link TreeNode}
     * @return {@link TreeNode}
     */
    public abstract N root();

    /**
     * Set teh root {@link TreeNode}
     * @param node {@link TreeNode}
     */
    public abstract void root(N node);

    /**
     * Get the size number of the {@link Tree}
     * @return the size number
     */
    public abstract int size();
}