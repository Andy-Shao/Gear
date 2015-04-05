package com.github.andyshao.lang;

import java.math.BigInteger;

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
    public static final int bitGet(BigInteger pos , final int[] array) {
        return ByteOperation.bitGet(pos , array , ByteWrapper.INT_BYTE_WRAPPER);
    }

    public static final int[] bitOxr(final int[] b1 , final int[] b2 , int size) {
        //TODO
        return null;
    }

    public static final int[] bitRotLeft(int count , final int[] array) {
        return ByteOperation.bitRotLeft(count , array , ByteWrapper.INT_BYTE_WRAPPER);
    }

    public static final int[] bitRotRight(int count , final int[] array) {
        return ByteOperation.bitRotRight(count , array , ByteWrapper.INT_BYTE_WRAPPER);
    }

    public static final int[] bitSet(BigInteger pos , int state , final int[] array) {
        return ByteOperation.bitSet(pos , state , array , ByteWrapper.INT_BYTE_WRAPPER);
    }

    public static final int[] fill(int state , int startPos , int endPos , final int[] array) {
        return ByteOperation.fill(state , startPos , endPos , array , ByteWrapper.INT_BYTE_WRAPPER);
    }

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
            throw new IllegalArgumentException();
        }
    }

    public static final short getShort(int i , int position) {
        switch (position) {
        case 0:
            return (short) (0xffff & i);
        case 1:
            return (short) ((0xffff0000 & i) >> 16);
        default:
            throw new IllegalArgumentException();
        }
    }

    public static final int setByte(int i , int position , byte b) {
        int temp = ByteOperation.toUnsignedInt(b);
        switch (position) {
        case 0:
            break;
        case 1:
            temp <<= 8;
            break;
        case 2:
            temp <<= 16;
            break;
        case 3:
            temp <<= 24;
            break;
        default:
            throw new IllegalArgumentException();
        }
        return i | temp;
    }

    public static final int setShort(int i , int position , short s) {
        //TODO
        return 0;
    }

    public static final byte[] toByte(int i) {
        return new byte[] {
            IntegerOperation.getByte(i , 0) , IntegerOperation.getByte(i , 1) , IntegerOperation.getByte(i , 2) ,
            IntegerOperation.getByte(i , 3)
        };
    }

    public static final String toString(int[] array) {
        return ByteOperation.toString(array , ByteWrapper.INT_BYTE_WRAPPER);
    }

    public static final long toUnsingedLong(int unsingedInt) {
        return 0x00000000ffffffffL & unsingedInt;
    }

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
