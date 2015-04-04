package com.github.andyshao.lang;

import java.math.BigInteger;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 5, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class CharOperation {
    public static final int bitGet(BigInteger pos , final char[] array) {
        return ByteOperation.bitGet(pos , array , ByteWrapper.CHAR_BYTE_WRAPPER);
    }

    public static final char[] bitOxr(final char[] b1 , final char[] b2 , int size) {
        //TODO
        return null;
    }

    public static final char[] bitRotLeft(int count , final char[] array) {
        return ByteOperation.bitRotLeft(count , array , ByteWrapper.CHAR_BYTE_WRAPPER);
    }

    public static final char[] bitRotRight(int count , final char[] array) {
        return ByteOperation.bitRotRight(count , array , ByteWrapper.CHAR_BYTE_WRAPPER);
    }

    public static final char[] bitSet(BigInteger pos , int state , final char[] array) {
        return ByteOperation.bitSet(pos , state , array , ByteWrapper.CHAR_BYTE_WRAPPER);
    }

    public static final char[] fill(int state , int startPos , int endPos , final char[] array) {
        return ByteOperation.fill(state , startPos , endPos , array , ByteWrapper.CHAR_BYTE_WRAPPER);
    }

    public static final byte getByte(char c , int pos) {
        //TODO
        return 0;
    }

    public static final long setByte(char c , int pos , byte b) {
        //TODO
        return 0;
    }

    public static final byte[] toByte(char c) {
        //TODO
        return null;
    }

    public static final String toString(char[] array) {
        return ByteOperation.toString(array , ByteWrapper.CHAR_BYTE_WRAPPER);
    }

    public static final char valueOf(byte[] bs) {
        //TODO
        return 0;
    }

    private CharOperation() {
        throw new AssertionError("No " + CharOperation.class + " for you!");
    }
}
