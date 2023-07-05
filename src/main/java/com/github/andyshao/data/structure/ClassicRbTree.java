package com.github.andyshao.data.structure;

import com.github.andyshao.data.structure.Bitree.BitreeNode;

import java.io.Serial;
import java.util.function.Supplier;

/**
 * 
 * Title:Classical red-black tree<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 9, 2018<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 * @param <K> key type
 * @param <V> value type
 */
public class ClassicRbTree<K extends Comparable<K> , V> implements RbTree<K , V> {
    @Serial
    private static final long serialVersionUID = -2644066545159104607L;
    private final Supplier<RbTreeNode<K , V>> nodeFactory;
    protected final Supplier<BitreeNode<RbTreeNode<K , V>>> treeNodeFactory;
    private volatile BitreeNode<RbTreeNode<K , V>> root;

    /**
     * Build {@link ClassicRbTree}
     */
    public ClassicRbTree() {
        this(RbTreeNode::defaultNode, BitreeNode::defaultBitreeNode);
    }

    /**
     * Build {@link ClassicRbTree}
     * @param nodeFactory {@link com.github.andyshao.data.structure.RbTree.RbTreeNode} factory
     * @param treeNodeFactory {@link BitreeNode} factory
     */
    public ClassicRbTree(Supplier<RbTreeNode<K , V>> nodeFactory, Supplier<BitreeNode<RbTreeNode<K , V>>> treeNodeFactory) {
        this.nodeFactory = nodeFactory;
        this.treeNodeFactory = treeNodeFactory;
    }

    @Override
    public void clear() {
        this.root = null;
    }

    @Override
    public BitreeNode<RbTreeNode<K , V>> root() {
        return this.root;
    }

    @Override
    public void root(BitreeNode<RbTreeNode<K , V>> node) {
        this.root = node;
    }

    @Override
    public BitreeNode<RbTreeNode<K , V>> find(K key) {
        return find(this.root(), key);
    }
    
    BitreeNode<RbTreeNode<K , V>> find(BitreeNode<RbTreeNode<K , V>> node , K key) {
        if (node == null) return null;
        K nKey = node.data().key();
        int comp = nKey.compareTo(key);
        if (comp == 0) return node;
        else if (comp < 0) return this.find(node.left() , key);
        else return this.find(node.right() , key);
    }

    @Override
    public void add(K key , V value) {
        // Search for key. Update value if found; grow table if new.
        this.root(put(this.root() , key , value));
        this.root().data().color(NodeColor.BLACK);
    }
    
    BitreeNode<RbTreeNode<K , V>> put(BitreeNode<RbTreeNode<K , V>> h, K key, V val){
        // Do standard insert, with red link to parent.
        if(h == null) return createNode(key , val , 1 , NodeColor.RED); 
        
        int cmp = key.compareTo(h.data().key());
        if(cmp < 0) h.left(put(h.left(), key, val));
        else if(cmp > 0) h.right(put(h.right() , key , val));
        else h.data().value(val);
        
        if(isRed(h.right()) && !isRed(h.left())) h = rotateLeft(h);
        if(isRed(h.left()) && isRed(h.left().left())) h = rotateRight(h);
        if(isRed(h.left()) && isRed(h.right())) flipColors(h);
        
        h.data().numberOfSubtree(size(h.left()) + size(h.right()) + 1);
        return h;
    }
    
    boolean isRed(BitreeNode<RbTreeNode<K , V>> node) {
        if(node == null) return false;
        return node.data().color() == NodeColor.RED;
    }
    
    BitreeNode<RbTreeNode<K , V>> createNode(K key, V val, int numberOfSubtree, NodeColor color){
        RbTreeNode<K , V> newNode = this.nodeFactory.get();
        newNode.color(color);
        newNode.key(key);
        newNode.value(val);
        newNode.numberOfSubtree(numberOfSubtree);
        BitreeNode<RbTreeNode<K , V>> result = treeNodeFactory.get();
        result.data(newNode);
        return result;
    }
    
    BitreeNode<RbTreeNode<K , V>> rotateLeft(BitreeNode<RbTreeNode<K , V>> h){
        BitreeNode<RbTreeNode<K , V>> x = h.right();
        h.right(x.left());
        x.left(h);
        x.data().color(h.data().color());
        h.data().color(NodeColor.RED);
        x.data().numberOfSubtree(h.data().numberOfSubtree());
        h.data().numberOfSubtree(1 + size(h.left()) + size(h.right()));
        return x;
    }
    
    BitreeNode<RbTreeNode<K , V>> rotateRight(BitreeNode<RbTreeNode<K , V>> h){
        BitreeNode<RbTreeNode<K , V>> x = h.left();
        h.left(x.left());
        x.right(h);
        x.data().color(h.data().color());
        h.data().color(NodeColor.RED);
        x.data().numberOfSubtree(h.data().numberOfSubtree());
        h.data().numberOfSubtree(1 + size(h.left()) + size(h.right()));
        return x;
    }
    
    void flipColors(BitreeNode<RbTreeNode<K , V>> h) {
        h.data().color(NodeColor.RED);
        h.left().data().color(NodeColor.BLACK);
        h.right().data().color(NodeColor.BLACK);
    }

    @Override
    public void remove(K key) {
        if(!isRed(this.root().left()) && !isRed(this.root().right())) this.root().data().color(NodeColor.RED);
        this.root(delete(this.root() , key));
        if(!isEmpty()) root().data().color(NodeColor.BLACK);
    }

    BitreeNode<RbTreeNode<K , V>> delete(BitreeNode<RbTreeNode<K , V>> h, K key){
        if(key.compareTo(h.data().key()) < 0) {
            if(!isRed(h.left()) && !isRed(h.left().left())) h = moveRedLeft(h);
            h.left(delete(h.left() , key));
        } else {
            if(isRed(h.left())) h = rotateRight(h);
            if(key.compareTo(h.data().key()) == 0 && (h.right() == null)) return null;
            if(!isRed(h.right()) && !isRed(h.right().left())) h = moveRedRight(h);
            if(key.compareTo(h.data().key()) == 0) {
                BitreeNode<RbTreeNode<K , V>> rightMin = min(h.right());
                h.data().value(rightMin.data().value());
                h.data().key(rightMin.data().key());
                h.right(deleteMin(h.right()));
            } else h.right(delete(h.right() , key));
        }
        return balance(h);
    }

    BitreeNode<RbTreeNode<K , V>> min(BitreeNode<RbTreeNode<K , V>> h){
        if(h.left() == null) return h;
        return min(h.left());
    }
    
    BitreeNode<RbTreeNode<K, V>> balance(BitreeNode<RbTreeNode<K, V>> h){
        if(isRed(h.right())) h = rotateLeft(h);
        return h;
    }
    
    BitreeNode<RbTreeNode<K , V>> moveRedLeft(BitreeNode<RbTreeNode<K , V>> h){
        // Assuming that h is red and both h.left and h.left.left 
        // are black, make h.left or one of its children red.
        flipColors(h);
        if(isRed(h.right().left())) {
            h.right(rotateRight(h.right()));
            h = rotateLeft(h);
        }
        return h;
    }

    BitreeNode<RbTreeNode<K , V>> moveRedRight(BitreeNode<RbTreeNode<K , V>> h){
        // Assuming that h is red and both h.right and h.right.left 
        // are black, make h.right or one of its children red.
        flipColors(h);
        if(!isRed(h.left().left())) h = rotateRight(h);
        return h;
    }

    /**
     * delete the minimum item
     */
    public void deleteMin() {
        if(!isRed(this.root().left()) && !isRed(this.root().right())) this.root().data().color(NodeColor.RED);
        this.root(deleteMin(this.root()));
        if(!isEmpty()) this.root().data().color(NodeColor.BLACK);
    }

    BitreeNode<RbTreeNode<K , V>> deleteMin(BitreeNode<RbTreeNode<K , V>> h){
        if(h.left() == null) return null;
        if(!isRed(h.left()) && !isRed(h.left().left())) h = moveRedLeft(h);
        h.left(deleteMin(h.left()));
        return balance(h);
    }

    BitreeNode<RbTreeNode<K , V>> deleteMax(BitreeNode<RbTreeNode<K , V>> h){
        if(isRed(h.left())) h = rotateRight(h);
        if(h.right() == null) return null;
        if(!isRed(h.right()) && !isRed(h.right().left())) h = moveRedRight(h);
        h.right(deleteMax(h.right()));
        return balance(h);
    }

    /**
     * delete the maximum item
     */
    public void deleteMax() {
        if(!isRed(this.root().left()) && !isRed(this.root().right())) this.root().data().color(NodeColor.RED);
        this.root(deleteMax(this.root()));
        if(!isEmpty()) this.root().data().color(NodeColor.BLACK);
    }
}
