package com.github.andyshao.lang;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.Objects;

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
    /**
     * char {@link Comparator}
     * @return {@link Comparator}
     */
	public static final Comparator<Character> comparator() {
		return (l, r) -> {
			if(Objects.isNull(l) && Objects.nonNull(r)) return -1;
			else if(Objects.isNull(l) && Objects.isNull(r)) return 0;
			else if(Objects.nonNull(l) && Objects.isNull(r)) return 1;
			return Character.compare(l, r);
		};
	}

    /**
     * get value at bit level
     * @param pos the position
     * @param array {@link ArrayType#CHAR_ARRAY}
     * @return the value
     */
    public static final int bitGet(BigInteger pos , final char[] array) {
        return ByteOperation.bitGet(pos , array , ByteWrapper.CHAR_BYTE_WRAPPER);
    }

    /**
     * oxr operation in bit level
     * @param b1 one
     * @param b2 two
     * @param size the operation size
     * @return the answer
     */
    public static final char[] bitOxr(final char[] b1 , final char[] b2 , int size) {
        return ByteOperation.bitOxr(b1 , b2 , BigInteger.valueOf(size).multiply(BigInteger.valueOf(2)) , ByteWrapper.CHAR_BYTE_WRAPPER);
    }

    /**
     * 带进位左移动
     * @param count 移动位数
     * @param array the array
     * @return the answer
     */
    public static final char[] bitRotLeft(int count , final char[] array) {
        return ByteOperation.bitRotLeft(count , array , ByteWrapper.CHAR_BYTE_WRAPPER);
    }

    /**
     * 带进位右移动
     * @param count 移动位数
     * @param array array
     * @return the answer
     */
    public static final char[] bitRotRight(int count , final char[] array) {
        return ByteOperation.bitRotRight(count , array , ByteWrapper.CHAR_BYTE_WRAPPER);
    }

    /**
     * set value at bit level
     * @param pos position
     * @param state state
     * @param array the array
     * @return the answer
     */
    public static final char[] bitSet(BigInteger pos , int state , final char[] array) {
        return ByteOperation.bitSet(pos , state , array , ByteWrapper.CHAR_BYTE_WRAPPER);
    }

    /**
     * fill value
     * @param state state
     * @param startPos start position
     * @param endPos end position
     * @param array array
     * @return the answer
     */
    public static final char[] fill(int state , BigInteger startPos , BigInteger endPos , final char[] array) {
        return ByteOperation.fill(state , startPos , endPos , array , ByteWrapper.CHAR_BYTE_WRAPPER);
    }

    /**
     * get byte
     * @param c {@link Character}
     * @param pos position
     * @return the answer
     */
    public static final byte getByte(char c , int pos) {
        return ShortOperation.getByte((short) c , pos);
    }

    /**
     * set byte
     * @param c {@link Character}
     * @param pos position
     * @param b {@link Byte}
     * @return the origin value
     */
    public static final char setByte(char c , int pos , byte b) {
        return (char) ShortOperation.setByte((short) c , pos , b);
    }

    /**
     * to byte
     * @param c {@link Character}
     * @return the answer
     */
    public static final byte[] toByte(char c) {
        return ShortOperation.toByte((short) c);
    }

    /**
     * to hex string
     * @param c {@link Character}
     * @return the answer
     */
    public static final String toHexString(char c) {
        return ShortOperation.toHexString((short) c);
    }

    /**
     * to hex string
     * @param cs {@link ArrayType#CHAR_ARRAY}
     * @return hex string
     */
    public static final String toHexString(char... cs) {
        StringBuilder builder = new StringBuilder();
        for (char c : cs)
            builder.append(CharOperation.toHexString(c));
        return builder.toString();
    }

    /**
     * to string
     * @param array {@link ArrayType#CHAR_ARRAY}
     * @return the answer
     */
    public static final String toString(char[] array) {
        return ByteOperation.toString(array , ByteWrapper.CHAR_BYTE_WRAPPER);
    }

    /**
     * value of operation
     * @param bs {@link ArrayType#BYTE_ARRAY}
     * @return {@link Character}
     */
    public static final char valueOf(byte[] bs) {
        return (char) ShortOperation.valueOf(bs);
    }

    private CharOperation() {
        throw new AssertionError("No " + CharOperation.class + " for you!");
    }
}
