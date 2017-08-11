package com.github.andyshao.data.structure;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.andyshao.data.structure.Bitree.BitreeNode;

public class BitreeTest {

    private volatile Bitree<String> bitree;

    @Before
    public void before() {
        this.bitree = Bitree.<String> defaultBitTree(BitreeNode::defaultBitreeNode);
    }

    @Test
    public void testClean() {
        Assert.assertThat(this.bitree.size() , Matchers.is(0));

        this.bitree.insRight(null , "root");
        this.bitree.insLeft(this.bitree.root() , "left");
        this.bitree.insRight(this.bitree.root() , "right");

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

        this.bitree.insRight(null , "root");
        this.bitree.insLeft(this.bitree.root() , "left");
        this.bitree.insRight(this.bitree.root() , "right");

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

        this.bitree.insLeft(null , "root");

        Assert.assertThat(this.bitree.size() , Matchers.is(1));
        Assert.assertThat(this.bitree.root().data() , Matchers.is("root"));

        this.bitree.insLeft(this.bitree.root() , "left");

        Assert.assertThat(this.bitree.size() , Matchers.is(2));
        Assert.assertThat(this.bitree.root().left().data() , Matchers.is("left"));
    }

    @Test
    public void testInsertRight() {
        Assert.assertThat(this.bitree.size() , Matchers.is(0));

        this.bitree.insRight(null , "root");

        Assert.assertThat(this.bitree.size() , Matchers.is(1));
        Assert.assertThat(this.bitree.root().data() , Matchers.is("root"));

        this.bitree.insRight(this.bitree.root() , "right");

        Assert.assertThat(this.bitree.size() , Matchers.is(2));
        Assert.assertThat(this.bitree.root().right().data() , Matchers.is("right"));
    }

    @Test
    public void testMeger() {
        Bitree<String> left = Bitree.<String> defaultBitTree(BitreeNode::defaultBitreeNode);
        Bitree<String> right = Bitree.<String> defaultBitTree(BitreeNode::defaultBitreeNode);

        left.insLeft(null , "left");
        right.insLeft(null , "right");
        Tree<String , BitreeNode<String>> bitree = this.bitree.bitreeMeger(left , right , "root");

        Assert.assertThat(bitree.size() , Matchers.is(3));
        Assert.assertThat(bitree.root().data() , Matchers.is("root"));
        Assert.assertThat(bitree.root().left().data() , Matchers.is("left"));
        Assert.assertThat(bitree.root().right().data() , Matchers.is("right"));
    }

    @Test
    public void testPostorder() {
        Assert.assertThat(this.bitree.size() , Matchers.is(0));

        this.bitree.insRight(null , "root");
        this.bitree.insLeft(this.bitree.root() , "left");
        this.bitree.insRight(this.bitree.root() , "right");

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

        this.bitree.insRight(null , "root");
        this.bitree.insLeft(this.bitree.root() , "left");
        this.bitree.insRight(this.bitree.root() , "right");

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
