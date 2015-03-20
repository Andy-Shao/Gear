package com.github.andyshao.lang;

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
    public static final int bitGet(int pos , final long... ls) {
        if (pos < 0) throw new IllegalArgumentException("pos is less than 0");
        long value = 0x01L << (pos - (pos >> 6 << 6));
        return (ls[pos >> 6] & value) != 0x00 ? 1 : 0;
    }

    public static final byte getByte(long l , int pos) {
        byte result = 0x00;
        switch (pos) {
        case 0:
            result = (byte) (0x00000000000000ffL & l);
            break;
        case 1:
            result = (byte) ((0xff00L & l) >> 8);
            break;
        case 2:
            result = (byte) ((0xff0000L & l) >> 16);
            break;
        case 3:
            result = (byte) ((0xff000000 & l) >> 24);
            break;
        case 4:
            result = (byte) ((0xff00000000L & l) >> 32);
            break;
        case 5:
            result = (byte) ((0xff0000000000L & l) >> 40);
            break;
        case 6:
            result = (byte) ((0xff000000000000L & l) >> 48);
            break;
        case 7:
            result = (byte) ((0xff00000000000000L & l) >> 56);
            break;
        default:
            throw new IllegalArgumentException();
        }
        return result;
    }

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

    public static final long valueOf(int[] is) {
        //TODO
        return 0L;
    }

    public static final long valueOf(short[] s) {
        //TODO
        return 0L;
    }

    public LongOperation() {
        throw new AssertionError("No " + LongOperation.class + " for you!");
    }
}
