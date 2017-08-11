package com.github.andyshao.data.structure;

import java.util.function.Supplier;

import com.github.andyshao.data.structure.Bitree.BitreeNode;

/**
 * 
 * Title: classic red-black tree <br>
 * Descript:<br>
 * Copyright: Copryright(c) 11 Aug 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 * @param <K> key type
 * @param <V> value type
 */
public class ClassicRbTree<K extends Comparable<K> , V> implements RbTree<K , V> {
    private final Bitree<RbTreeNode<K , V>> biTree;
    private final Supplier<RbTreeNode<K , V>> nodeFactory;
    public ClassicRbTree(Bitree<RbTreeNode<K , V>> biTree, Supplier<RbTreeNode<K , V>> nodeFactory) {
        this.biTree = biTree;
        this.nodeFactory = nodeFactory;
    }
    public ClassicRbTree() {
        this(Bitree.defaultBitTree(), () -> new RbTreeNode<K , V>() {
            private NodeColor color;
            private K key;
            private V value;

            @Override
            public com.github.andyshao.data.structure.RbTree.NodeColor color() {
                return this.color;
            }

            @Override
            public void color(com.github.andyshao.data.structure.RbTree.NodeColor color) {
                this.color = color;
            }

            @Override
            public K key() {
                return this.key;
            }

            @Override
            public void key(K data) {
                this.key = data;
            }

            @Override
            public V value() {
                return this.value;
            }

            @Override
            public void value(V value) {
                this.value = value;
            }
        });
    }

    @Override
    public void clear() {
        this.biTree.clear();
    }

    @Override
    public int size() {
        return this.biTree.size();
    }

    @Override
    public BitreeNode<RbTreeNode<K , V>> find(K key) {
        return find(this.root(), key);
    }

    private BitreeNode<RbTreeNode<K , V>> find(BitreeNode<RbTreeNode<K , V>> node , K key) {
        if(node == null) return null;
        K nKey = node.data().key();
        int comp = nKey.compareTo(key);
        if(comp == 0) {
            if(nKey.equals(key)) return node;
            else return find(node.left(), key);
        } else if(comp < 0) return find(node.left(), key);
        else return find(node.right(), key);
    }
    
    @Override
    public void add(K key, V value) {
        RbTreeNode<K , V> newNode = this.nodeFactory.get();
        newNode.color(NodeColor.RED);
        newNode.key(key);
        newNode.value(value);
        BitreeNode<RbTreeNode<K , V>> newBn = add(this.root(), newNode);
        // TODO Auto-generated method stub
    }

    private BitreeNode<RbTreeNode<K , V>> add(BitreeNode<RbTreeNode<K , V>> bn , RbTreeNode<K , V> newNode) {
        K bnKey = bn.data().key();
        K key = newNode.key();
        int comp = bnKey.compareTo(key);
        if(comp == 0) {
            if(bnKey.equals(key)) {
                bn.data().value(newNode.value());
                return bn;
            } else {
                BitreeNode<com.github.andyshao.data.structure.RbTree.RbTreeNode<K , V>> left = bn.left();
                if(left != null) return add(left, newNode);
                else return this.biTree.insLeft(bn , newNode);
            }
        } else if(comp < 0) {
            BitreeNode<com.github.andyshao.data.structure.RbTree.RbTreeNode<K , V>> left = bn.left();
            if(left != null) return add(left, newNode);
            else return this.biTree.insLeft(bn , newNode);
        } else {
            BitreeNode<com.github.andyshao.data.structure.RbTree.RbTreeNode<K , V>> right = bn.right();
            if(right != null) return add(right, newNode);
            else return this.biTree.insRight(bn , newNode);
        }
    }
    
    @Override
    public BitreeNode<RbTreeNode<K , V>> root() {
        return this.biTree.root();
    }

    @Override
    public void remove(K key) {
        // TODO Auto-generated method stub

    }

}
