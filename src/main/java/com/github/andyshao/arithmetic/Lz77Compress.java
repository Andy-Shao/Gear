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
public class Lz77Compress implements Compress {
    public static final int LZ77_BUFFER_SIZE = 32;
    public static final int LZ77_BUFLEN_BITS = 5;
    public static final int LZ77_NEXT_BITS = 8;
    public static final int LZ77_PHRASE_BITS = Lz77Compress.LZ77_TYPE_BITS + Lz77Compress.LZ77_WINOFF_BITS
        + Lz77Compress.LZ77_NEXT_BITS + Lz77Compress.LZ77_BUFLEN_BITS;
    public static final int LZ77_SYMBOL_BITS = Lz77Compress.LZ77_TYPE_BITS + Lz77Compress.LZ77_NEXT_BITS;
    public static final int LZ77_TYPE_BITS = 1;
    public static final int LZ77_WINDOW_SIZE = 4096;
    public static final int LZ77_WINOFF_BITS = 12;

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
