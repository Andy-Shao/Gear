package com.github.andyshao.lang;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.Objects;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 13, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class IntegerOperation {
    /**
     * Default int {@link Comparator}
     * @return {@link Comparator}
     */
	public static final Comparator<Integer> comparator() {
		return (o1, o2) -> {
			if(Objects.isNull(o1) && Objects.nonNull(o2)) return -1;
			else if(Objects.isNull(o1) && Objects.isNull(o2)) return 0;
			else if(Objects.nonNull(o1) && Objects.isNull(o2)) return 1;
			return Integer.compare(o1, o2);
		};
	}

    /**
     * bit get
     * @param pos position
     * @param array array
     * @return value
     */
    public static final int bitGet(BigInteger pos , final int[] array) {
        return ByteOperation.bitGet(pos , array , ByteWrapper.INT_BYTE_WRAPPER);
    }

    /**
     * Oxr operation in bit level
     * @param b1 array one
     * @param b2 array two
     * @param size size of the operation
     * @return the answer
     */
    public static final int[] bitOxr(final int[] b1 , final int[] b2 , int size) {
        return ByteOperation.bitOxr(b1 , b2 , BigInteger.valueOf(size).multiply(BigInteger.valueOf(4)) , ByteWrapper.INT_BYTE_WRAPPER);
    }

    /**
     * 带进位左移
     * @param count 左移位数
     * @param array array
     * @return the answer
     */
    public static final int[] bitRotLeft(int count , final int[] array) {
        return ByteOperation.bitRotLeft(count , array , ByteWrapper.INT_BYTE_WRAPPER);
    }

    /**
     * 带进位右移
     * @param count 右移位数
     * @param array array
     * @return the answer
     */
    public static final int[] bitRotRight(int count , final int[] array) {
        return ByteOperation.bitRotRight(count , array , ByteWrapper.INT_BYTE_WRAPPER);
    }

    /**
     * set operation in bit level
     * @param pos position
     * @param state the statue
     * @param array the array
     * @return the answer
     */
    public static final int[] bitSet(BigInteger pos , int state , final int[] array) {
        return ByteOperation.bitSet(pos , state , array , ByteWrapper.INT_BYTE_WRAPPER);
    }

    /**
     * fill data
     * @param state state
     * @param startPos start position
     * @param endPos end position
     * @param array the array
     * @return the answer
     */
    public static final int[] fill(int state , BigInteger startPos , BigInteger endPos , final int[] array) {
        return ByteOperation.fill(state , startPos , endPos , array , ByteWrapper.INT_BYTE_WRAPPER);
    }

    /**
     * get byte from i
     * @param i the origin value
     * @param position the position
     * @return the answer
     */
    public static final byte getByte(int i , int position) {
        switch (position) {
        case 0:
            return (byte) (0x000000ff & i);
        case 1:
            return (byte) ((0x0000ff00 & i) >> 8);
        case 2:
            return (byte) ((0x00ff0000 & i) >> 16);
        case 3:
            return (byte) ((0xff000000 & i) >> 24);
        default:
            throw new IllegalArgumentException(position + " less than 0 or bigger than 3");
        }
    }

    /**
     * get short from i
     * @param i the origin value
     * @param position position
     * @return the answer
     */
    public static final short getShort(int i , int position) {
        switch (position) {
        case 0:
            return (short) (0xffff & i);
        case 1:
            return (short) ((0xffff0000 & i) >> 16);
        default:
            throw new IllegalArgumentException(position + " less than 0 or bigger than 1");
        }
    }

    /**
     * set byte into i
     * @param i the origin value
     * @param position position
     * @param b {@link Byte}
     * @return the answer
     */
    public static final int setByte(int i , int position , byte b) {
        int temp = ByteOperation.toUnsignedInt(b);
        switch (position) {
        case 0:
            i &= 0xffffff00;
            break;
        case 1:
            i &= 0xffff00ff;
            temp <<= 8;
            break;
        case 2:
            i &= 0xff00ffff;
            temp <<= 16;
            break;
        case 3:
            i &= 0x00ffffff;
            temp <<= 24;
            break;
        default:
            throw new IllegalArgumentException(position + " less than 0 or bigger than 3");
        }
        return i | temp;
    }

    /**
     * set short into i
     * @param i the origin value
     * @param position position
     * @param s {@link Short}
     * @return the answer
     */
    public static final int setShort(int i , int position , short s) {
        int temp = ShortOperation.toUnsingedInt(s);
        switch (position) {
        case 0:
            i &= 0xffff0000;
            break;
        case 1:
            i &= 0x0000ffff;
            temp <<= 16;
            break;
        default:
            throw new IllegalArgumentException(position + " less than 0 or bigger than 3");
        }
        return i | temp;
    }

    /**
     * to byte from i
     * @param i the origin value
     * @return {@link ArrayType#BYTE_ARRAY}
     */
    public static final byte[] toByte(int i) {
        return new byte[] { IntegerOperation.getByte(i , 0) , IntegerOperation.getByte(i , 1) , IntegerOperation.getByte(i , 2) , IntegerOperation.getByte(i , 3) };
    }

    /**
     * to hex string
     * @param i the origin value
     * @return the hex string
     */
    public static final String toHexString(int i) {
        return Convert.BYTES_TO_HEX.convert(new byte[] { IntegerOperation.getByte(i , 0) , IntegerOperation.getByte(i , 1) , IntegerOperation.getByte(i , 2) , IntegerOperation.getByte(i , 3) });
    }

    /**
     * to hex string
     * @param is value list
     * @return the hex string
     */
    public static final String toHexString(int... is) {
        StringBuilder builder = new StringBuilder();
        for (int i : is)
            builder.append(IntegerOperation.toHexString(i));
        return builder.toString();
    }

    /**
     * to string from array
     * @param array the array
     * @return the answer
     */
    public static final String toString(int[] array) {
        return ByteOperation.toString(array , ByteWrapper.INT_BYTE_WRAPPER);
    }

    /**
     * to unsinged long
     * @param unsingedInt unsinged int
     * @return the answer
     */
    public static final long toUnsingedLong(int unsingedInt) {
        return 0x00000000ffffffffL & unsingedInt;
    }

    /**
     * get int value from byte array
     * @param bs {@link ArrayType#BYTE_ARRAY}
     * @return the answer
     */
    public static final int valueOf(byte[] bs) {
        int i = 0x00000000;
        i = (i | bs[3]) << 8;
        i = (i | bs[2]) << 8;
        i = (i | bs[1]) << 8;
        i |= bs[0];
        return i;
    }

    private IntegerOperation() {
        throw new AssertionError("No " + IntegerOperation.class + " for you!");
    }
}
