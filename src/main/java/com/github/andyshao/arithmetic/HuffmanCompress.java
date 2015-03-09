package com.github.andyshao.arithmetic;

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
    public class HuffCode {
        public short code;
        public int size;
        public byte used;
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
    public class HuffNode {
        public int freq;
        public byte symbol;
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
