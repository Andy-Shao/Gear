package com.github.andyshao.arithmetic;

import java.util.PriorityQueue;
import java.util.function.Supplier;

import com.github.andyshao.data.structure.Bitree;
import com.github.andyshao.data.structure.Bitree.BitreeNode;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 9, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class HuffmanCompress implements Compress {
    /**
     * 
     * Title:<br>
     * Descript: Define a structure for entries in Huffman code tables.<br>
     * Copyright: Copryright(c) Mar 9, 2015<br>
     * Encoding:UNIX UTF-8
     * 
     * @author Andy.Shao
     *
     */
    public interface HuffCode {
        public short code();

        public void code(short code);

        public int size();

        public void size(int size);

        public int used();

        public void used(int used);
    }

    /**
     * 
     * Title:<br>
     * Descript: Define a structure for nodes of Huffman trees.<br>
     * Copyright: Copryright(c) Mar 9, 2015<br>
     * Encoding:UNIX UTF-8
     * 
     * @author Andy.Shao
     *
     */
    public interface HuffNode {
        public int freq();

        public void freq(int freq);

        public int symbol();

        public void symbol(int symbol);
    }

    static final int UNCHAR_MAX = 0xff;

    public static void buildTable(BitreeNode<HuffNode> node , final short code , final int size , HuffCode[] table) {
        if (!Bitree.bitree_is_eob(node)) {
            if (!Bitree.bitree_is_eob(node.left())) HuffmanCompress.buildTable(node.left() , (short) (code << 1) ,
                size + 1 , table);
            if (!Bitree.bitree_is_eob(node.right())) HuffmanCompress.buildTable(node.right() ,
                (short) ((code << 1) | 0x01) , size + 1 , table);
            if (Bitree.bitree_is_eob(node.left()) && Bitree.bitree_is_eob(node.right())) {
                table[node.data().symbol()].used(1);
                table[node.data().symbol()].code(code);
                table[node.data().symbol()].size(size);
            }
        }
    }

    public static final Bitree<HuffNode> buildTree(
        int[] freqs , Supplier<Bitree<HuffNode>> bitreeFactory , Supplier<HuffNode> huffNodeFactory) {
        PriorityQueue<Bitree<HuffNode>> pqueue = new PriorityQueue<Bitree<HuffNode>>((tree1 , tree2) -> {
            return HuffmanCompress.compareFreq(tree1 , tree2);
        });

        for (int c = 0 ; c <= HuffmanCompress.UNCHAR_MAX ; c++)
            if (freqs[c] != 0) {
                //Set up a binary tree for the current symbol and its frequency.
                Bitree<HuffNode> init = bitreeFactory.get();
                HuffNode data = huffNodeFactory.get();
                data.symbol((byte) c);
                data.freq(freqs[c]);

                init.bitree_ins_left(null , data);
                pqueue.offer(init);
            }

        int size = pqueue.size();
        for (int c = 1 ; c <= size - 1 ; c++) {
            Bitree<HuffNode> merge = bitreeFactory.get();
            Bitree<HuffNode> left = pqueue.poll();
            Bitree<HuffNode> right = pqueue.poll();
            HuffNode data = huffNodeFactory.get();
            data.freq(left.root().data().freq() + right.root().data().freq());
            Bitree.bitreeMerge(merge.getTreeNodeFactory() , left , right , data);
            pqueue.offer(merge);
        }

        return pqueue.poll();
    }

    public static final int compareFreq(final Bitree<HuffNode> tree1 , final Bitree<HuffNode> tree2) {
        //Compare the frequencies stored in the root nodes of two binary trees.
        HuffNode root1 = tree1.root().data();
        HuffNode root2 = tree2.root().data();

        if (root1.freq() < root2.freq()) return 1;
        else if (root1.freq() > root2.freq()) return -1;
        else return 0;
    }

    @Override
    public byte[] compress(byte[] data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte[] uncompress(byte[] data) {
        // TODO Auto-generated method stub
        return null;
    }
}
