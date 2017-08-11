package com.github.andyshao.data.structure;

import com.github.andyshao.data.structure.Bitree.BitreeNode;

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
    public interface RbTreeNode<KEY,VALUE> {
        public NodeColor color();
        public void color(NodeColor color);
        public KEY key();
        public void key(KEY data);
        public VALUE value();
        public void value(VALUE value);
    }
    
    public enum NodeColor {
        RED,BLACK;
    }
    
    public BitreeNode<RbTreeNode<K , V>> find(K key);
    public V add(K key, V value);
    public void remove(K key);
}
