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
public final class ShortOperation {
    /**
     * default short {@link Comparator}
     * @return {@link Comparator}
     */
	public static final Comparator<Short> comparator() {
		return (l, r) -> {
			if(Objects.isNull(l) && Objects.nonNull(r)) return -1;
			else if(Objects.isNull(l) && Objects.isNull(r)) return 0;
			else if(Objects.nonNull(l) && Objects.isNull(r)) return 1;
			return Short.compare(l, r);
		};
	}

    /**
     * get operation in bit level
     * @param pos the position
     * @param array the array
     * @return the value
     */
    public static final int bitGet(BigInteger pos , final short[] array) {
        return ByteOperation.bitGet(pos , array , ByteWrapper.SHORT_BYTE_WRAPPER);
    }

    /**
     * oxr operation in bit level
     * @param b1 {@link ArrayType#SHORT_ARRAY}
     * @param b2 {@link ArrayType#SHORT_ARRAY}
     * @param size the size of the opeartion
     * @return the answer
     */
    public static final short[] bitOxr(final short[] b1 , final short[] b2 , int size) {
        return ByteOperation.bitOxr(b1 , b2 , BigInteger.valueOf(size).multiply(BigInteger.valueOf(2)) , ByteWrapper.SHORT_BYTE_WRAPPER);
    }

    /**
     * 带进位左移
     * @param count 左移的位数
     * @param array the array
     * @return the answer
     */
    public static final short[] bitRotLeft(int count , final short[] array) {
        return ByteOperation.bitRotLeft(count , array , ByteWrapper.SHORT_BYTE_WRAPPER);
    }

    /**
     * 带进位右移
     * @param count 右移的位数
     * @param array the array
     * @return the answer
     */
    public static final short[] bitRotRight(int count , final short[] array) {
        return ByteOperation.bitRotRight(count , array , ByteWrapper.SHORT_BYTE_WRAPPER);
    }

    /**
     * set value in bit level
     * @param pos the position
     * @param state the state
     * @param array the array
     * @return the answer
     */
    public static final short[] bitSet(BigInteger pos , int state , final short[] array) {
        return ByteOperation.bitSet(pos , state , array , ByteWrapper.SHORT_BYTE_WRAPPER);
    }

    /**
     * fill the data
     * @param state the state
     * @param startPos the start position
     * @param endPos the end position
     * @param array the array
     * @return the answer
     */
    public static final short[] fill(int state , BigInteger startPos , BigInteger endPos , final short[] array) {
        return ByteOperation.fill(state , startPos , endPos , array , ByteWrapper.SHORT_BYTE_WRAPPER);
    }

    /**
     * get byte
     * @param s {@link PrimitiveType#SHORT}
     * @param position the position
     * @return {@link PrimitiveType#BYTE}
     */
    public static final byte getByte(short s , int position) {
        switch (position) {
        case 0:
            return (byte) (0x00ff & s);
        case 1:
            return (byte) ((0xff00 & s) >> 8);
        default:
            throw new IllegalArgumentException(position + " less than 0 or bigger than 1");
        }
    }

    /**
     * set byte into short
     * @param s {@link PrimitiveType#SHORT}
     * @param position the position
     * @param b {@link PrimitiveType#BYTE}
     * @return the answer
     */
    public static final short setByte(short s , int position , byte b) {
        short temp = ByteOperation.toUnsignedShort(b);
        switch (position) {
        case 0:
            s &= 0xff00;
            break;
        case 1:
            s &= 0x00ff;
            temp <<= 8;
            break;
        default:
            throw new IllegalArgumentException(position + " less than 0 or bigger than 1");
        }
        return (short) (s | temp);
    }

    /**
     * to byte
     * @param s {@link PrimitiveType#BYTE}
     * @return {@link ArrayType#BYTE_ARRAY}
     */
    public static final byte[] toByte(short s) {
        return new byte[] { ShortOperation.getByte(s , 0) , ShortOperation.getByte(s , 1) };
    }

    /**
     * hex string
     * @param s {@link PrimitiveType#SHORT}
     * @return hex string
     */
    public static final String toHexString(short s) {
        return Convert.BYTES_TO_HEX.convert(new byte[] { ShortOperation.getByte(s , 0) , ShortOperation.getByte(s , 1) });
    }

    /**
     * to hex string
     * @param s {@link ArrayType#SHORT_ARRAY}
     * @return hex string
     */
    public static final String toHexString(short... s) {
        StringBuilder builder = new StringBuilder();
        for (short sh : s)
            builder.append(ShortOperation.toHexString(sh));
        return builder.toString();
    }

    /**
     * to string from array
     * @param array the array
     * @return the answer
     */
    public static final String toString(short[] array) {
        return ByteOperation.toString(array , ByteWrapper.SHORT_BYTE_WRAPPER);
    }

    /**
     * to unsinged int
     * @param unsignedShort unsigned short
     * @return the answer
     */
    public static final int toUnsingedInt(short unsignedShort) {
        return 0x0000ffff & unsignedShort;
    }

    /**
     * to unsinged long
     * @param unsingedShort unsinged short
     * @return the answer
     */
    public static final long toUnsingedLong(short unsingedShort) {
        return 0x000000000000ffffL & unsingedShort;
    }

    /**
     * value of
     * @param bs {@link ArrayType#BYTE_ARRAY}
     * @return {@link PrimitiveType#SHORT}
     */
    public static final short valueOf(byte[] bs) {
        short s = 0x0000;
        s = (short) ((s | bs[1]) << 8);
        s |= bs[0];
        return s;
    }

    private ShortOperation() {
        throw new AssertionError("No " + ShortOperation.class + " for you!");
    }
}
