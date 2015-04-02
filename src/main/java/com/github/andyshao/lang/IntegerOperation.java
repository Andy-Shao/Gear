package com.github.andyshao.lang;

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
    public static final int[] bitOxr(final int[] b1 , final int[] b2 , int size) {
        //TODO
        return null;
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

    public static final byte[] toByte(int i) {
        return new byte[] {
            IntegerOperation.getByte(i , 0) , IntegerOperation.getByte(i , 1) , IntegerOperation.getByte(i , 2) ,
            IntegerOperation.getByte(i , 3)
        };
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

    public static final ByteWrapper wrap(final int[] data) {
        return new IntegerByteWrapper(data);
    }

    public static final ByteWrapper wrap(final int[] data , int start , int end) {
        return new IntegerByteWrapper(data , start , end);
    }

    private IntegerOperation() {
        throw new AssertionError("No " + IntegerOperation.class + " for you!");
    }
}
