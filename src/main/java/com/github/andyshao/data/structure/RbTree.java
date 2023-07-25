package com.github.andyshao.data.structure;

import com.github.andyshao.data.structure.Bitree.BitreeNode;

import java.util.Objects;

/**
 * 
 * Title: red-black tree<br>
 * Descript:<br>
 * Copyright: Copryright(c) 11 Aug 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 * @param <K> key type
 * @param <V> value type
 */
public interface RbTree<K extends Comparable<K>,V> extends Tree<RbTree.RbTreeNode<K , V> , BitreeNode<RbTree.RbTreeNode<K,V>>> {
    /**
     * The default red-black tree
     * @param <KEY> the key
     * @param <VALUE> the value
     */
    public interface RbTreeNode<KEY,VALUE> {
        /**
         * color
         * @return {@link NodeColor}
         */
        public NodeColor color();

        /**
         * set color
         * @param color {@link NodeColor}
         */
        public void color(NodeColor color);

        /**
         * get key
         * @return key
         */
        public KEY key();

        /**
         * set key
         * @param key key
         */
        public void key(KEY key);

        /**
         * get value
         * @return value
         */
        public VALUE value();

        /**
         * set value
         * @param value value
         */
        public void value(VALUE value);

        /**
         * number of subtree
         * @return number of subtree
         */
        public int numberOfSubtree();

        /**
         * set number of subtree
         * @param numberOfSubtree number of subtree
         */
        public void numberOfSubtree(int numberOfSubtree);

        /**
         * default red-black tree node
         * @param <K> key type
         * @param <V> value type
         */
        public class MyRbTreeNode<K, V> implements RbTreeNode<K , V> {
            private NodeColor color;
            private K key;
            private V value;
            private int numberOfSubtree;
            
            @Override
            public NodeColor color() {
                return this.color;
            }
            
            @Override
            public void color(NodeColor color) {
                this.color = color;
            }
            
            @Override
            public K key() {
                return this.key;
            }
            
            @Override
            public void key(K key) {
                this.key = key;
            }
            
            @Override
            public V value() {
                return this.value;
            }
            
            @Override
            public void value(V value) {
                this.value = value;
            }
            
            @SuppressWarnings("unchecked")
            @Override
            public boolean equals(Object obj) {
                if(obj instanceof MyRbTreeNode) {
                    MyRbTreeNode<K , V> that = (MyRbTreeNode<K , V>) obj;
                    return Objects.equals(this.key , that.key);
                } else return false;
            }
            
            @Override
            public int hashCode() {
                return Objects.hashCode(this.key);
            }
            
            @Override
            public String toString() {
                return "[" + this.key + "-" + this.value + ":" + this.color + "]";
            }

            @Override
            public int numberOfSubtree() {
                return this.numberOfSubtree;
            }

            @Override
            public void numberOfSubtree(int numberOfSubtree) {
                this.numberOfSubtree = numberOfSubtree;
            }
        }

        /**
         * build {@link RbTreeNode}
         * @return {@link RbTreeNode}
         * @param <K> key type
         * @param <V> value type
         */
        public static <K,V> RbTreeNode<K,V> defaultNode() {
            return new MyRbTreeNode<K,V>();
        }
    }

    /**
     * Node color
     */
    public enum NodeColor {
        /**red*/
        RED,
        /**black*/
        BLACK;
    }

    /**
     * Find {@link RbTreeNode} by key
     * @param key the key
     * @return {@link RbTreeNode}
     */
    public BitreeNode<RbTreeNode<K , V>> find(K key);

    /**
     * Add element
     * @param key the key
     * @param value the value
     */
    public void add(K key, V value);

    /**
     * Remove element
     * @param key the key of the element
     */
    public void remove(K key);
    @Override
    public default int size() {
        if(this.root() == null || this.root().data() == null) return 0;
        return this.root().data().numberOfSubtree();
    }

    /**
     * is empty
     * @return if it is empty then true
     */
    public default boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * The size of the {@link RbTree}
     * @param n the root node of the {@link RbTree}
     * @return the size number
     */
    public default int size(BitreeNode<RbTreeNode<K , V>> n) {
        if(n == null || n.data() == null) return 0;
        return n.data().numberOfSubtree();
    }
}
