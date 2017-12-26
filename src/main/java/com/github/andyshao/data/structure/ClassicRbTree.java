package com.github.andyshao.data.structure;

import java.util.function.Supplier;

import com.github.andyshao.data.structure.Bitree.BitreeNode;

/**
 * 
 * Title: classic red-black tree <br>
 * Descript:<br>
 * Copyright: Copryright(c) 11 Aug 2017<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <K> key type
 * @param <V> value type
 */
class ClassicRbTree<K extends Comparable<K> , V> implements RbTree<K , V> {
    static final <KEY extends Comparable<KEY> , VALUE> void addFixUp(Bitree<RbTreeNode<KEY , VALUE>> t , BitreeNode<RbTreeNode<KEY , VALUE>> z) {
        while (z.parent() != null && z.parent().data().color() == NodeColor.RED) {
            if (z.parent().parent() == null) break;
            if (z.parent() == z.parent().parent().left()) {
                BitreeNode<RbTreeNode<KEY , VALUE>> y = z.parent().parent().right();
                if (y.data().color() == NodeColor.RED) {
                    z.parent().data().color(NodeColor.BLACK);
                    y.data().color(NodeColor.BLACK);
                    z.parent().parent().data().color(NodeColor.RED);
                    z = z.parent().parent();
                } else if (z == z.parent().right()) {
                    z = z.parent();
                    ClassicRbTree.leftRotate(t , z);
                }
                z.parent().data().color(NodeColor.BLACK);
                z.parent().parent().data().color(NodeColor.RED);
                ClassicRbTree.rightRotate(t , z.parent().parent());
            } else {
                //TODO
            }
        }
        t.root().data().color(NodeColor.BLACK);
    }

    static final <KEY extends Comparable<KEY> , VALUE> void leftRotate(Bitree<RbTreeNode<KEY , VALUE>> t , BitreeNode<RbTreeNode<KEY , VALUE>> x) {
        BitreeNode<RbTreeNode<KEY , VALUE>> y = x.right(); //set y
        x.right(y.left()); //turn y's left subtree into x's right subtree
        if (Bitree.isLeaf(y.left())) y.left().parent(x);
        y.parent(x.parent()); //link x's parent to y
        if (Bitree.isEob(x.parent())) t.root(y);
        else if (x == x.parent().left()) x.parent().left(y);
        else x.parent().right(y);
        y.left(x); //put x on y's left
        x.parent(y);
    }

    static final <KEY extends Comparable<KEY> , VALUE> void rightRotate(Bitree<RbTreeNode<KEY , VALUE>> t , BitreeNode<RbTreeNode<KEY , VALUE>> y) {
        BitreeNode<RbTreeNode<KEY , VALUE>> x = y.left();
        y.left(x.right());
        if (Bitree.isLeaf(x.right())) x.right().parent(y);
        x.parent(y.parent());
        if (Bitree.isEob(y.parent())) t.root(x);
        else if (y == y.parent().left()) y.parent().left(x);
        else y.parent().right(x);
        x.right(y);
        y.parent(x);
    }

    private final Bitree<RbTreeNode<K , V>> biTree;

    private final Supplier<RbTreeNode<K , V>> nodeFactory;

    public ClassicRbTree() {
        this(Bitree.defaultBitTree() , RbTreeNode::defaultNode);
    }

    public ClassicRbTree(Bitree<RbTreeNode<K , V>> biTree , Supplier<RbTreeNode<K , V>> nodeFactory) {
        this.biTree = biTree;
        this.nodeFactory = nodeFactory;
    }

    BitreeNode<RbTreeNode<K , V>> add(BitreeNode<RbTreeNode<K , V>> bn , RbTreeNode<K , V> newNode) {
        K bnKey = bn.data().key();
        K key = newNode.key();
        int comp = bnKey.compareTo(key);
        if (comp == 0) {
            if (bnKey.equals(key)) {
                bn.data().value(newNode.value());
                return bn;
            } else {
                BitreeNode<RbTreeNode<K , V>> left = bn.left();
                if (left != null) return this.add(left , newNode);
                else return this.biTree.insLeft(bn , newNode);
            }
        } else if (comp < 0) {
            BitreeNode<RbTreeNode<K , V>> left = bn.left();
            if (left != null) return this.add(left , newNode);
            else return this.biTree.insLeft(bn , newNode);
        } else {
            BitreeNode<RbTreeNode<K , V>> right = bn.right();
            if (right != null) return this.add(right , newNode);
            else return this.biTree.insRight(bn , newNode);
        }
    }

    @Override
    public void add(K key , V value) {
        RbTreeNode<K , V> newNode = this.nodeFactory.get();
        newNode.color(NodeColor.RED);
        newNode.key(key);
        newNode.value(value);
        ClassicRbTree.addFixUp(this.biTree , this.add(this.root() , newNode));
    }

    @Override
    public void clear() {
        this.biTree.clear();
    }

    private BitreeNode<RbTreeNode<K , V>> find(BitreeNode<RbTreeNode<K , V>> node , K key) {
        if (node == null) return null;
        K nKey = node.data().key();
        int comp = nKey.compareTo(key);
        if (comp == 0) {
            if (nKey.equals(key)) return node;
            else return this.find(node.left() , key);
        } else if (comp < 0) return this.find(node.left() , key);
        else return this.find(node.right() , key);
    }

    @Override
    public BitreeNode<RbTreeNode<K , V>> find(K key) {
        return this.find(this.root() , key);
    }

    @Override
    public void remove(K key) {
        // TODO Auto-generated method stub

    }

    @Override
    public BitreeNode<RbTreeNode<K , V>> root() {
        return this.biTree.root();
    }

    @Override
    public void root(BitreeNode<RbTreeNode<K , V>> node) {
        this.biTree.root(node);
    }

    @Override
    public int size() {
        return this.biTree.size();
    }
}