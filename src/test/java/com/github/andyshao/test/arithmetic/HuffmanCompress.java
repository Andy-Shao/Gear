package com.github.andyshao.test.arithmetic;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.function.Supplier;

import com.github.andyshao.data.structure.Bitree;
import com.github.andyshao.data.structure.Bitree.BitreeNode;
import com.github.andyshao.lang.ByteOperation;
import com.github.andyshao.lang.IntegerOperation;
import com.github.andyshao.lang.ShortOperation;
import com.github.andyshao.util.ArrayTools;

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
        public class MyHuffCode implements HuffCode {
            private short code;
            private int size;
            private int used;

            @Override
            public short code() {
                return this.code;
            }

            @Override
            public void code(short code) {
                this.code = code;
            }

            @Override
            public int size() {
                return this.size;
            }

            @Override
            public void size(int size) {
                this.size = size;
            }

            @Override
            public int used() {
                return this.used;
            }

            @Override
            public void used(int used) {
                this.used = used;
            }

        }

        public static HuffCode defaultHuffCode() {
            return new MyHuffCode();
        }

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
        public class MyHuffNode implements HuffNode {
            private int freq;
            private int symbol;

            @Override
            public int freq() {
                return this.freq;
            }

            @Override
            public void freq(int freq) {
                this.freq = freq;
            }

            @Override
            public int symbol() {
                return this.symbol;
            }

            @Override
            public void symbol(int symbol) {
                this.symbol = symbol;
            }
        }

        public static HuffNode defaultHuffNode() {
            return new MyHuffNode();
        }

        public int freq();

        public void freq(int freq);

        public int symbol();

        public void symbol(int symbol);
    }

    public static final int compareFreq(final Bitree<HuffNode> tree1 , final Bitree<HuffNode> tree2) {
        //Compare the frequencies stored in the root nodes of two binary trees.
        HuffNode root1 = tree1.root().data();
        HuffNode root2 = tree2.root().data();

        if (root1.freq() < root2.freq()) return 1;
        else if (root1.freq() > root2.freq()) return -1;
        else return 0;
    }

    private final Supplier<Bitree<HuffNode>> bitreeFactory;

    private final Supplier<HuffCode> huffCodeFactory;

    private final Supplier<HuffNode> huffNodeFactory;

    public HuffmanCompress() {
        this(() -> HuffCode.defaultHuffCode() , () -> HuffNode.defaultHuffNode() , () -> Bitree
            .defaultBitTree(() -> BitreeNode.defaultBitreeNode()));
    }

    public HuffmanCompress(
        Supplier<HuffCode> huffCodeFactory , Supplier<HuffNode> huffNodeFactory ,
        Supplier<Bitree<HuffNode>> bitreeFactory) {
        this.huffCodeFactory = huffCodeFactory;
        this.huffNodeFactory = huffNodeFactory;
        this.bitreeFactory = bitreeFactory;
    }

    public void buildTable(BitreeNode<HuffNode> node , final short code , final int size , HuffCode[] table) {
        if (!Bitree.bitree_is_eob(node)) {
            if (!Bitree.bitree_is_eob(node.left())) this.buildTable(node.left() , (short) (code << 1) , size + 1 ,
                table);
            if (!Bitree.bitree_is_eob(node.right())) this.buildTable(node.right() , (short) ((code << 1) | 0x01) ,
                size + 1 , table);
            if (Bitree.bitree_is_eob(node.left()) && Bitree.bitree_is_eob(node.right())) {
                int symbol = node.data().symbol();
                if (table[symbol] == null) table[symbol] = this.getHuffCodeFactory().get();
                table[symbol].used(1);
                table[symbol].code(code);
                table[symbol].size(size);
            }
        }
    }

    public final Bitree<HuffNode> buildTree(int[] freqs) {
        PriorityQueue<Bitree<HuffNode>> pqueue =
            new PriorityQueue<Bitree<HuffNode>>((tree1 , tree2) -> HuffmanCompress.compareFreq(tree1 , tree2));

        for (int c = 0 ; c <= ByteOperation.UNCHAR_MAX ; c++)
            if (freqs[c] != 0) {
                //Set up a binary tree for the current symbol and its frequency.
                Bitree<HuffNode> init = this.getBitreeFactory().get();
                HuffNode data = this.getHuffNodeFactory().get();
                data.symbol(c);
                data.freq(freqs[c]);

                init.bitree_ins_left(null , data);
                pqueue.offer(init);
            }

        int size = pqueue.size();
        for (int c = 1 ; c <= size - 1 ; c++) {
            Bitree<HuffNode> merge = null;
            Bitree<HuffNode> left = pqueue.poll();
            Bitree<HuffNode> right = pqueue.poll();
            HuffNode data = this.getHuffNodeFactory().get();
            data.freq(left.root().data().freq() + right.root().data().freq());
            merge = this.getBitreeFactory().get().bitreeMeger(left , right , data);
            pqueue.offer(merge);
        }

        return pqueue.poll();
    }

    @Override
    public byte[] compress(byte[] original) {
        //Initially, there is no buffer of compressed data.
        byte[] compressed = null;

        //Get the frequency of each symbol i the original data.
        int[] freqs = new int[ByteOperation.UNCHAR_MAX + 1];
        Arrays.fill(freqs , 0);

        if (original.length > 0) for (int ipos = 0 ; ipos < original.length ; ipos++)
            freqs[ByteOperation.toUnsignedInt(original[ipos])]++;

        //Scale the frequencies to fit into the one byte.
        int max = ByteOperation.UNCHAR_MAX;

        for (int c = 0 ; c <= ByteOperation.UNCHAR_MAX ; c++)
            if (freqs[c] > max) max = freqs[c];

        for (int c = 0 ; c <= ByteOperation.UNCHAR_MAX ; c++) {
            int scale = (int) (freqs[c] / ((double) max / (double) ByteOperation.UNCHAR_MAX));
            if (scale == 0 && freqs[c] != 0) freqs[c] = 1;
            else freqs[c] = scale;
        }

        HuffCode[] table = new HuffCode[ByteOperation.UNCHAR_MAX + 1];
        {
            Bitree<HuffNode> tree = this.buildTree(freqs);

            for (int i = 0 ; i < table.length ; i++)
                table[i] = this.getHuffCodeFactory().get();
            this.buildTable(tree.root() , (short) 0x0000 , 0 , table);
        }

        int hsize = (ByteOperation.UNCHAR_MAX + 2);
        byte[] comp =
            ArrayTools.mergeArray(byte[].class , IntegerOperation.toByte(original.length) ,
                ArrayTools.pack_unpack(freqs , byte[].class , (input) -> (byte)((int) input)));

        int opos = hsize << 3;
        for (int ipos = 0 ; ipos < original.length ; ipos++) {
            int c = original[ipos];
            for (int i = 0 ; i < table[c].size() ; i++) {
                if ((opos - (opos >> 3 << 3)) == 0) {
                    byte[] temp = null;
                    comp = temp;
                }

                int cpos = (2 << 3) - table[c].size() + i;
                ByteOperation.bitSet(opos , ByteOperation.bitGet(cpos , ShortOperation.toByte(table[c].code())) , comp);
                opos++;
            }
        }

        compressed = comp;
        return compressed;
    }

    public Supplier<Bitree<HuffNode>> getBitreeFactory() {
        return this.bitreeFactory;
    }

    public Supplier<HuffCode> getHuffCodeFactory() {
        return this.huffCodeFactory;
    }

    public Supplier<HuffNode> getHuffNodeFactory() {
        return this.huffNodeFactory;
    }

    @Override
    public byte[] uncompress(byte[] data) {
        // TODO Auto-generated method stub
        return null;
    }
}
