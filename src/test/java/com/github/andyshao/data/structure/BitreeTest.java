package com.github.andyshao.data.structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.andyshao.data.structure.Bitree.BitreeNode;

public class BitreeTest {

    private volatile Bitree<String> bitree;

    @BeforeEach
    public void before() {
        this.bitree = Bitree.<String> defaultBitTree(BitreeNode::defaultBitreeNode);
    }

    @Test
    public void testClean() {
        assertEquals(this.bitree.size() , 0);

        this.bitree.insRight(null , "root");
        this.bitree.insLeft(this.bitree.root() , "left");
        this.bitree.insRight(this.bitree.root() , "right");

        assertEquals(this.bitree.size() , 3);
        assertEquals(this.bitree.root().data() , "root");
        assertEquals(this.bitree.root().left().data() , "left");
        assertEquals(this.bitree.root().right().data() , "right");

        this.bitree.clear();

        assertEquals(this.bitree.size() , 0);
    }

    @Test
    public void testInorder() {
        assertEquals(this.bitree.size() , 0);

        this.bitree.insRight(null , "root");
        this.bitree.insLeft(this.bitree.root() , "left");
        this.bitree.insRight(this.bitree.root() , "right");

        assertEquals(this.bitree.size() , 3);
        assertEquals(this.bitree.root().data() , "root");
        assertEquals(this.bitree.root().left().data() , "left");
        assertEquals(this.bitree.root().right().data() , "right");

        List<String> linked = new ArrayList<String>();
        Bitree.inorder(this.bitree.root() , linked);

        String string = "";
        for (String str : linked)
            string += str;

        assertEquals(string , "leftrootright");
    }

    @Test
    public void testInsertLeft() {
        assertEquals(this.bitree.size(), 0);

        this.bitree.insLeft(null , "root");

        assertEquals(this.bitree.size() , 1);
        assertEquals(this.bitree.root().data() , "root");

        this.bitree.insLeft(this.bitree.root() , "left");

        assertEquals(this.bitree.size() , 2);
        assertEquals(this.bitree.root().left().data() , "left");
    }

    @Test
    public void testInsertRight() {
        assertEquals(this.bitree.size() , 0);

        this.bitree.insRight(null , "root");

        assertEquals(this.bitree.size() , 1);
        assertEquals(this.bitree.root().data() , "root");

        this.bitree.insRight(this.bitree.root() , "right");

        assertEquals(this.bitree.size() , 2);
        assertEquals(this.bitree.root().right().data() , "right");
    }

    @Test
    public void testMeger() {
        Bitree<String> left = Bitree.<String> defaultBitTree(BitreeNode::defaultBitreeNode);
        Bitree<String> right = Bitree.<String> defaultBitTree(BitreeNode::defaultBitreeNode);

        left.insLeft(null , "left");
        right.insLeft(null , "right");
        Tree<String , BitreeNode<String>> bitree = this.bitree.bitreeMeger(left , right , "root");

        assertEquals(bitree.size() , 3);
        assertEquals(bitree.root().data() , "root");
        assertEquals(bitree.root().left().data() , "left");
        assertEquals(bitree.root().right().data() , "right");
    }

    @Test
    public void testPostorder() {
        assertEquals(this.bitree.size() , 0);

        this.bitree.insRight(null , "root");
        this.bitree.insLeft(this.bitree.root() , "left");
        this.bitree.insRight(this.bitree.root() , "right");

        assertEquals(this.bitree.size() , 3);
        assertEquals(this.bitree.root().data() , "root");
        assertEquals(this.bitree.root().left().data() , "left");
        assertEquals(this.bitree.root().right().data() , "right");

        List<String> linked = new ArrayList<String>();
        Bitree.postorder(this.bitree.root() , linked);

        String string = "";
        for (String str : linked)
            string += str;

        assertEquals(string , "leftrightroot");
    }

    @Test
    public void testPreorder() {
        assertEquals(this.bitree.size() , 0);

        this.bitree.insRight(null , "root");
        this.bitree.insLeft(this.bitree.root() , "left");
        this.bitree.insRight(this.bitree.root() , "right");

        assertEquals(this.bitree.size() , 3);
        assertEquals(this.bitree.root().data() , "root");
        assertEquals(this.bitree.root().left().data() , "left");
        assertEquals(this.bitree.root().right().data() , "right");

        List<String> linked = new ArrayList<String>();
        Bitree.preorder(this.bitree.root() , linked);

        String string = "";
        for (String str : linked)
            string += str;

        assertEquals(string , "rootleftright");
    }

    @Test
    public void testSize() {
        assertEquals(this.bitree.size() , 0);
    }
}
