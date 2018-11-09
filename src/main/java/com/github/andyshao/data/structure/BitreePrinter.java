package com.github.andyshao.data.structure;

import com.github.andyshao.data.structure.Bitree.BitreeNode;
import com.github.andyshao.lang.Convert;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 9, 2018<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public final class BitreePrinter {
    private static final String nonPad = "  ";
    
    public static final <DATA> StringBuilder printForGraph(Bitree<DATA> tree, Convert<DATA , String> convert){
        BitreeNode<DATA> root = tree.root();
        return printForGraph(root , convert);
    }
    
    public static final <DATA> StringBuilder printForGraph(BitreeNode<DATA> treeNode, Convert<DATA , String> convert) {
        StringBuilder ret = new StringBuilder();
        printForGraph(treeNode , convert , 0, ret);
        return ret;
    }
    
    public static final <DATA> void printForGraph(BitreeNode<DATA> treeNode, Convert<DATA , String> convert, int depth, 
        StringBuilder sb) {
        if(treeNode == null) return;
        String tabs = calculateTab(depth);
        
        sb.append("\n").append(tabs).append("[");
        sb.append(convert.convert(treeNode.data()));
        printForGraph(treeNode.left(), convert, depth + 1, sb);
        printForGraph(treeNode.right(), convert, depth + 1, sb);
        sb.append(tabs).append("]\n");
    }

    static final String calculateTab(int depth) {
        String tabs = null;
        StringBuilder tmp = new StringBuilder();
        for(int i=0; i<depth; i++) {
            tmp.append(nonPad);
        }
        tabs = tmp.toString();
        return tabs;
    }
}
