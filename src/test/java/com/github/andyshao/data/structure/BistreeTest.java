package com.github.andyshao.data.structure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.andyshao.data.structure.Bistree.AvlNode;

public class BistreeTest {

    private volatile Bistree<Integer> bistree;
    private final Integer[] data = new Integer[] { 4 , 5 , 9 , 0 , 1 , 6 , 7 , 8 , 2 , 3 };

    @BeforeEach
    public void before() {
        this.bistree = Bistree.<Integer> defaultBistree(Bitree.<AvlNode<Integer>> defaultBitTree(Bitree.BitreeNode::defaultBitreeNode) , Bistree.AvlNode::defaultAvlNode , Integer::compare);
    }

    @Test
    public void testInsert() {
        assertEquals(this.bistree.size() , 0);

        for (Integer i : this.data)
            this.bistree.bistree_insert(i);

        assertEquals(this.bistree.size() , this.data.length);
    }

    @Test
    public void testLookup() {
        this.testInsert();

        assertEquals(this.bistree.bistree_lookup(this.data[0]).data , this.data[0]);
    }

    @Test
    public void testRemove() {
        this.testInsert();

        this.bistree.bistree_remove(this.data[0]);

        assertEquals(this.bistree.size() , this.data.length);
        assertNull(this.bistree.bistree_lookup(this.data[0]));
    }
}
