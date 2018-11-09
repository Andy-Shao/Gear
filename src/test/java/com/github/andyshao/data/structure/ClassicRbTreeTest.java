package com.github.andyshao.data.structure;

import org.junit.jupiter.api.Test;

public class ClassicRbTreeTest {

    @Test
    public void test() {
        ClassicRbTree<Integer , Integer> rbTree = new ClassicRbTree<Integer,Integer>();
        add(rbTree, 1);
        StringBuilder sb = BitreePrinter.printForGraph(rbTree.root() , node -> node.value().toString());
        System.out.println(sb);
        System.out.println("====================");
        
        add(rbTree, 2);
        sb = BitreePrinter.printForGraph(rbTree.root() , node -> node.value().toString());
        System.out.println(sb);
        System.out.println("====================");
        
        add(rbTree, 3);
        sb = BitreePrinter.printForGraph(rbTree.root() , node -> node.value().toString());
        System.out.println(sb);
        System.out.println("====================");
        
        add(rbTree , 4);
        sb = BitreePrinter.printForGraph(rbTree.root() , node -> node.value().toString());
        System.out.println(sb);
        System.out.println("====================");
        
        add(rbTree , 5);
        sb = BitreePrinter.printForGraph(rbTree.root() , node -> node.value().toString());
        System.out.println(sb);
        System.out.println("====================");
        
        add(rbTree , 6);
        sb = BitreePrinter.printForGraph(rbTree.root() , node -> node.value().toString());
        System.out.println(sb);
        System.out.println("====================");
    }

    public void add(ClassicRbTree<Integer , Integer> rbTree, int i) {
        rbTree.add(i, i);
    }
}
