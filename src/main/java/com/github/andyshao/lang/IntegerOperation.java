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

    private IntegerOperation() {
        throw new AssertionError("No " + IntegerOperation.class + " for you!");
    }
}
