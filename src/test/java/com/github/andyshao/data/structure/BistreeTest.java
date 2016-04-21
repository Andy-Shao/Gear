package com.github.andyshao.data.structure;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.andyshao.data.structure.Bistree.AvlNode;

public class BistreeTest {

    private volatile Bistree<Integer> bistree;
    private final Integer[] data = new Integer[] { 4 , 5 , 9 , 0 , 1 , 6 , 7 , 8 , 2 , 3 };

    @Before
    public void before() {
        this.bistree = Bistree.<Integer> defaultBistree(Bitree.<AvlNode<Integer>> defaultBitTree(Bitree.BitreeNode::defaultBitreeNode) , Bistree.AvlNode::defaultAvlNode , Integer::compare);
    }

    @Test
    public void testInsert() {
        Assert.assertThat(this.bistree.size() , Matchers.is(0));

        for (Integer i : this.data)
            this.bistree.bistree_insert(i);

        Assert.assertThat(this.bistree.size() , Matchers.is(this.data.length));
    }

    @Test
    public void testLookup() {
        this.testInsert();

        Assert.assertThat(this.bistree.bistree_lookup(this.data[0]).data , Matchers.is(this.data[0]));
    }

    @Test
    public void testRemove() {
        this.testInsert();

        this.bistree.bistree_remove(this.data[0]);

        Assert.assertThat(this.bistree.size() , Matchers.is(this.data.length));
        Assert.assertThat(this.bistree.bistree_lookup(this.data[0]) == null , Matchers.is(true));
    }
}
