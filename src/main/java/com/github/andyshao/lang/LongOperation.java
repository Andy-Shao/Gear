package com.github.andyshao.lang;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.Objects;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 18, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class LongOperation {
    /**
     * Default long {@link Comparator}
     * @return {@link Comparator}
     */
	public static final Comparator<Long> comparator() {
		return (o1, o2) -> {
			if(Objects.isNull(o1) && Objects.nonNull(o2)) return -1;
			else if(Objects.isNull(o1) && Objects.isNull(o2)) return 0;
			else if(Objects.nonNull(o1) && Objects.isNull(o2)) return 1;
			return Long.compare(o1, o2);
		};
	}

    /**
     * get operation in bit level
     * @param pos the position
     * @param array the array
     * @return the answer
     */
    public static final int bitGet(BigInteger pos , final long[] array) {
        return ByteOperation.bitGet(pos , array , ByteWrapper.LONG_BYTE_WRAPPER);
    }

    /**
     * oxr operation in bit level
     * @param b1 {@link ArrayType#LONG_ARRAY}
     * @param b2 {@link ArrayType#LONG_ARRAY}
     * @param size the size of the operation
     * @return the answer
     */
    public static final long[] bitOxr(final long[] b1 , final long[] b2 , int size) {
        return ByteOperation.bitOxr(b1 , b2 , BigInteger.valueOf(size).multiply(BigInteger.valueOf(8)) , ByteWrapper.LONG_BYTE_WRAPPER);
    }

    /**
     * 带进位左移
     * @param count 左移的位数
     * @param array the array
     * @return the answer
     */
    public static final long[] bitRotLeft(int count , final long[] array) {
        return ByteOperation.bitRotLeft(count , array , ByteWrapper.LONG_BYTE_WRAPPER);
    }

    /**
     * 带进位右移
     * @param count 右移的位数
     * @param array the array
     * @return the answer
     */
    public static final long[] bitRotRight(int count , final long[] array) {
        return ByteOperation.bitRotRight(count , array , ByteWrapper.LONG_BYTE_WRAPPER);
    }

    /**
     * set operation in bit level
     * @param pos the position
     * @param state the state
     * @param array the array
     * @return the answer
     */
    public static final long[] bitSet(BigInteger pos , int state , final long[] array) {
        return ByteOperation.bitSet(pos , state , array , ByteWrapper.LONG_BYTE_WRAPPER);
    }

    /**
     * fill data
     * @param state the state
     * @param startPos start position
     * @param endPos end position
     * @param array the array
     * @return the answer
     */
    public static final long[] fill(int state , BigInteger startPos , BigInteger endPos , final long[] array) {
        return ByteOperation.fill(state , startPos , endPos , array , ByteWrapper.LONG_BYTE_WRAPPER);
    }

    /**
     * get byte from long
     * @param l {@link PrimitiveType#LONG}
     * @param pos the position
     * @return return value
     */
    public static final byte getByte(long l , int pos) {
        switch (pos) {
        case 0:
            return (byte) (0x00000000000000ffL & l);
        case 1:
            return (byte) ((0xff00L & l) >> 8);
        case 2:
            return (byte) ((0xff0000L & l) >> 16);
        case 3:
            return (byte) ((0xff000000 & l) >> 24);
        case 4:
            return (byte) ((0xff00000000L & l) >> 32);
        case 5:
            return (byte) ((0xff0000000000L & l) >> 40);
        case 6:
            return (byte) ((0xff000000000000L & l) >> 48);
        case 7:
            return (byte) ((0xff00000000000000L & l) >> 56);
        default:
            throw new IllegalArgumentException(pos + " less than 0 or bigger than 7");
        }
    }

    /**
     * get int from long
     * @param l {@link PrimitiveType#LONG}
     * @param pos the position
     * @return the answer
     */
    public static final int getInt(long l , int pos) {
        switch (pos) {
        case 0:
            return (int) (0x00000000ffffffffL & l);
        case 1:
            return (int) ((0xffffffff00000000L & l) >> 32);
        default:
            throw new IllegalArgumentException(pos + " less than 0 or bigger than 1");
        }
    }

    /**
     * get short from long
     * @param l {@link PrimitiveType#LONG}
     * @param pos the position
     * @return the answer
     */
    public static final short getShort(long l , int pos) {
        switch (pos) {
        case 0:
            return (short) (0x000000000000ffffL & l);
        case 1:
            return (short) (0x00000000ffff0000L & l);
        case 2:
            return (short) (0x0000ffff00000000L & l);
        case 3:
            return (short) (0xffff000000000000L & l);
        default:
            throw new IllegalArgumentException(pos + " less than 0 or bigger than 3");
        }
    }

    /**
     * set byte from long
     * @param l {@link PrimitiveType#LONG}
     * @param pos the position
     * @param b set value
     * @return the answer
     */
    public static final long setByte(long l , int pos , byte b) {
        long temp = ByteOperation.toUnsignedLong(b);
        switch (pos) {
        case 0:
            l &= 0xffffffffffffff00L;
            break;
        case 1:
            l &= 0xffffffffffff00ffL;
            temp <<= 8;
            break;
        case 2:
            l &= 0xffffffffff00ffffL;
            temp <<= 16;
            break;
        case 3:
            l &= 0xffffffff00ffffffL;
            temp <<= 24;
            break;
        case 4:
            l &= 0xffffff00ffffffffL;
            temp <<= 32;
            break;
        case 5:
            l &= 0xffff00ffffffffffL;
            temp <<= 40;
            break;
        case 6:
            l &= 0xff00ffffffffffffL;
            temp <<= 48;
            break;
        case 7:
            l &= 0x00ffffffffffffffL;
            temp <<= 56;
            break;
        default:
            throw new IllegalArgumentException(pos + " less than 0 or bigger than 7");
        }
        return l | temp;
    }

    /**
     * set int from long
     * @param l {@link PrimitiveType#LONG}
     * @param pos the position
     * @param i the set value
     * @return the answer
     */
    public static final long setInt(long l , int pos , int i) {
        long temp = IntegerOperation.toUnsingedLong(i);
        switch (pos) {
        case 0:
            l &= 0xffffffff00000000L;
            break;
        case 1:
            l &= 0x00000000ffffffffL;
            temp <<= 32;
            break;
        default:
            throw new IllegalArgumentException(pos + " less than 0 or bigger than 1");
        }
        return l | temp;
    }

    /**
     * set short from long
     * @param l {@link PrimitiveType#LONG}
     * @param pos the position
     * @param s set value
     * @return the answer
     */
    public static final long setShort(long l , int pos , short s) {
        long temp = ShortOperation.toUnsingedLong(s);
        switch (pos) {
        case 0:
            l &= 0xffffffffffff0000L;
            break;
        case 1:
            l &= 0xffffffff0000ffffL;
            temp <<= 16;
            break;
        case 2:
            l &= 0xffff0000ffffffffL;
            temp <<= 32;
            break;
        case 3:
            l &= 0x0000ffffffffffffL;
            temp <<= 48;
            break;
        default:
            throw new IllegalArgumentException(pos + " less than 0 or bigger than 3");
        }
        return l | temp;
    }

    /**
     * to byte
     * @param l {@link PrimitiveType#LONG}
     * @return the {@link ArrayType#BYTE_ARRAY}
     */
    public static final byte[] toByte(long l) {
        return new byte[] { LongOperation.getByte(l , 0) , LongOperation.getByte(l , 1) , LongOperation.getByte(l , 2) , LongOperation.getByte(l , 3) , LongOperation.getByte(l , 4) ,
            LongOperation.getByte(l , 5) , LongOperation.getByte(l , 6) , LongOperation.getByte(l , 7) };
    }

    /**
     * to hex string
     * @param l {@link PrimitiveType#LONG}
     * @return hex string
     */
    public static final String toHexString(long l) {
        return Convert.BYTES_TO_HEX.convert(new byte[] { LongOperation.getByte(l , 0) , LongOperation.getByte(l , 1) , LongOperation.getByte(l , 2) , LongOperation.getByte(l , 3) ,
            LongOperation.getByte(l , 4) , LongOperation.getByte(l , 5) , LongOperation.getByte(l , 6) , LongOperation.getByte(l , 7) });
    }

    /**
     * to hex string
     * @param ls {@link ArrayType#LONG_ARRAY}
     * @return a hex string
     */
    public static final String toHexString(long... ls) {
        StringBuilder builder = new StringBuilder();
        for (long l : ls)
            builder.append(LongOperation.toHexString(l));
        return builder.toString();
    }

    /**
     * to string from long array
     * @param array {@link ArrayType#LONG_ARRAY}
     * @return the answer
     */
    public static final String toString(long[] array) {
        return ByteOperation.toString(array , ByteWrapper.LONG_BYTE_WRAPPER);
    }

    /**
     * value of
     * @param bs {@link ArrayType#BYTE_ARRAY}
     * @return the result type
     */
    public static final long valueOf(byte[] bs) {
        long l = 0x00L;
        l = (l | bs[7]) << 8;
        l = (l | bs[6]) << 8;
        l = (l | bs[5]) << 8;
        l = (l | bs[4]) << 8;
        l = (l | bs[3]) << 8;
        l = (l | bs[2]) << 8;
        l = (l | bs[1]) << 8;
        l |= bs[0];
        return l;
    }

    private LongOperation() {
        throw new AssertionError("No " + LongOperation.class + " for you!");
    }
}
