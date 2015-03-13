package com.github.andyshao.data.structure;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.andyshao.data.structure.Bitree.BitreeNode;

public class BitTreeTest {

    private volatile Bitree<String> bitree;

    @Before
    public void before() {
        this.bitree = Bitree.<String> defaultBitTree(() -> {
            return BitreeNode.<String> defaultBitreeNode();
        });
    }

    @Test
    public void testClean() {
        Assert.assertThat(this.bitree.size() , Matchers.is(0));

        this.bitree.bitree_ins_right(null , "root");
        this.bitree.bitree_ins_left(this.bitree.root() , "left");
        this.bitree.bitree_ins_right(this.bitree.root() , "right");

        Assert.assertThat(this.bitree.size() , Matchers.is(3));
        Assert.assertThat(this.bitree.root().data() , Matchers.is("root"));
        Assert.assertThat(this.bitree.root().left().data() , Matchers.is("left"));
        Assert.assertThat(this.bitree.root().right().data() , Matchers.is("right"));

        this.bitree.clear();

        Assert.assertThat(this.bitree.size() , Matchers.is(0));
    }

    @Test
    public void testInorder() {
        Assert.assertThat(this.bitree.size() , Matchers.is(0));

        this.bitree.bitree_ins_right(null , "root");
        this.bitree.bitree_ins_left(this.bitree.root() , "left");
        this.bitree.bitree_ins_right(this.bitree.root() , "right");

        Assert.assertThat(this.bitree.size() , Matchers.is(3));
        Assert.assertThat(this.bitree.root().data() , Matchers.is("root"));
        Assert.assertThat(this.bitree.root().left().data() , Matchers.is("left"));
        Assert.assertThat(this.bitree.root().right().data() , Matchers.is("right"));

        List<String> linked = new ArrayList<String>();
        Bitree.inorder(this.bitree.root() , linked);

        String string = "";
        for (String str : linked)
            string += str;

        Assert.assertThat(string , Matchers.is("leftrootright"));
    }

    @Test
    public void testInsertLeft() {
        Assert.assertThat(this.bitree.size() , Matchers.is(0));

        this.bitree.bitree_ins_left(null , "root");

        Assert.assertThat(this.bitree.size() , Matchers.is(1));
        Assert.assertThat(this.bitree.root().data() , Matchers.is("root"));

        this.bitree.bitree_ins_left(this.bitree.root() , "left");

        Assert.assertThat(this.bitree.size() , Matchers.is(2));
        Assert.assertThat(this.bitree.root().left().data() , Matchers.is("left"));
    }

    @Test
    public void testInsertRight() {
        Assert.assertThat(this.bitree.size() , Matchers.is(0));

        this.bitree.bitree_ins_right(null , "root");

        Assert.assertThat(this.bitree.size() , Matchers.is(1));
        Assert.assertThat(this.bitree.root().data() , Matchers.is("root"));

        this.bitree.bitree_ins_right(this.bitree.root() , "right");

        Assert.assertThat(this.bitree.size() , Matchers.is(2));
        Assert.assertThat(this.bitree.root().right().data() , Matchers.is("right"));
    }

    @Test
    public void testMeger() {
        Bitree<String> left = Bitree.<String> defaultBitTree(() -> {
            return BitreeNode.<String> defaultBitreeNode();
        });
        Bitree<String> right = Bitree.<String> defaultBitTree(() -> {
            return BitreeNode.<String> defaultBitreeNode();
        });

        left.bitree_ins_left(null , "left");
        right.bitree_ins_left(null , "right");
        Tree<String> bitree = Bitree.<String> bitreeMerge(() -> {
            return BitreeNode.<String> defaultBitreeNode();
        } , left , right , "root");

        Assert.assertThat(bitree.size() , Matchers.is(3));
        Assert.assertThat(bitree.root().data() , Matchers.is("root"));
        Assert.assertThat(bitree.root().left().data() , Matchers.is("left"));
        Assert.assertThat(bitree.root().right().data() , Matchers.is("right"));
    }

    @Test
    public void testPostorder() {
        Assert.assertThat(this.bitree.size() , Matchers.is(0));

        this.bitree.bitree_ins_right(null , "root");
        this.bitree.bitree_ins_left(this.bitree.root() , "left");
        this.bitree.bitree_ins_right(this.bitree.root() , "right");

        Assert.assertThat(this.bitree.size() , Matchers.is(3));
        Assert.assertThat(this.bitree.root().data() , Matchers.is("root"));
        Assert.assertThat(this.bitree.root().left().data() , Matchers.is("left"));
        Assert.assertThat(this.bitree.root().right().data() , Matchers.is("right"));

        List<String> linked = new ArrayList<String>();
        Bitree.postorder(this.bitree.root() , linked);

        String string = "";
        for (String str : linked)
            string += str;

        Assert.assertThat(string , Matchers.is("leftrightroot"));
    }

    @Test
    public void testPreorder() {
        Assert.assertThat(this.bitree.size() , Matchers.is(0));

        this.bitree.bitree_ins_right(null , "root");
        this.bitree.bitree_ins_left(this.bitree.root() , "left");
        this.bitree.bitree_ins_right(this.bitree.root() , "right");

        Assert.assertThat(this.bitree.size() , Matchers.is(3));
        Assert.assertThat(this.bitree.root().data() , Matchers.is("root"));
        Assert.assertThat(this.bitree.root().left().data() , Matchers.is("left"));
        Assert.assertThat(this.bitree.root().right().data() , Matchers.is("right"));

        List<String> linked = new ArrayList<String>();
        Bitree.preorder(this.bitree.root() , linked);

        String string = "";
        for (String str : linked)
            string += str;

        Assert.assertThat(string , Matchers.is("rootleftright"));
    }

    @Test
    public void testSize() {
        Assert.assertThat(this.bitree.size() , Matchers.is(0));
    }
}
