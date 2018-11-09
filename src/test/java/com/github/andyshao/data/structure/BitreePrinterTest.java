package com.github.andyshao.data.structure;

import org.junit.jupiter.api.Test;

import com.github.andyshao.data.structure.Bitree.BitreeNode;

public class BitreePrinterTest {

    @Test
    public void testPrintTree() {
        Bitree<Object> tree = Bitree.defaultBitTree();
        BitreeNode<Object> root = tree.insLeft(tree.root() , "1");
        BitreeNode<Object> left = tree.insLeft(root , "-1");
        BitreeNode<Object> right = tree.insRight(root , "2");
        tree.insLeft(left , "-2");
        tree.insRight(left , "-0.5");
        tree.insRight(right , "3");
        tree.insLeft(right , "1.5");
        
        StringBuilder sb = BitreePrinter.printForGraph(tree , Object::toString);
        System.out.println(sb);
    }
}
