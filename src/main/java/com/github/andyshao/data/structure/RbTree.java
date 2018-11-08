package com.github.andyshao.data.structure;

import java.util.Objects;

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
        public void key(KEY key);
        public VALUE value();
        public void value(VALUE value);
        public int numberOfSubtree();
        public void numberOfSubtree(int numberOfSubtree);
        
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
        
        public static <K,V> RbTreeNode<K,V> defaultNode() {
            return new MyRbTreeNode<K,V>();
        }
    }
    
    public enum NodeColor {
        RED,BLACK;
    }
    
    public BitreeNode<RbTreeNode<K , V>> find(K key);
    public void add(K key, V value);
    public void remove(K key);
}
