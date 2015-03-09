package com.github.andyshao.lang;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 10, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ByteOperation {
    private ByteOperation() {
        throw new AssertionError("No " + ByteOperation.class + " for you!");
    }

    public static final int bitGet(int pos , final byte... b) {
        if (pos < 0) throw new IllegalArgumentException();
        int value = 0x01 << (pos % 8);
        return (b[pos / 8] &= value) != 0x00 ? 1 : 0;
    }

    public static final byte[] bitSet(int pos , int state , final byte... b) {
        if (state != 0 && state != 1) throw new IllegalArgumentException();
        if (pos < 0) throw new IllegalArgumentException();
        int value = 0x01 << pos % 8;
        if (state == 0) b[pos / 8] &= ~value;
        else b[pos / 8] |= value;
        return b;
    }

    public static final byte[] bitOxr(final byte[] b1 , final byte[] b2 , int size) {
        byte[] result = new byte[size % 8 == 0 ? size / 8 : size / 8 + 1];
        for (int i = 0 ; i < size ; i++) {
            if (bitGet(i , b1) != bitGet(i , b2)) bitSet(i , 1 , result);
            else bitSet(i, 0, result);
        }
        return result;
    }

    public static final String toString(byte b) {
        return Integer.toString(b , 2);
    }
}
