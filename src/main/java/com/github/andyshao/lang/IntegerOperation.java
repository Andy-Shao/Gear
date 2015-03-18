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
    public static final byte[] toByte(int i) {
        return new byte[] {
            (byte) (0x000000ff & i) , (byte) ((0x0000ff00 & i) >> 8) , (byte) ((0x00ff0000 & i) >> 16) ,
            (byte) ((0xff000000 & i) >> 24)
        };
    }

    public static final short[] toShort(int i) {
        return new short[] {
            (short) (0x0000ffff & i) , (short) ((0xffff0000 & i) >> 16)
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

    public static final int valueOf(short[] s) {
        int i = 0x00000000;
        i = (i |s[1]) << 16;
        i |= s[0];
        return i;
    }

    private IntegerOperation() {
        throw new AssertionError("No " + IntegerOperation.class + " for you!");
    }
}
