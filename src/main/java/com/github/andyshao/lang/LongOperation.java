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
    public static final long[] bitOxr(final long[] b1 , final long[] b2 , int size) {
        //TODO
        return null;
    }
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
            throw new IllegalArgumentException();
        }
    }

    public static final long setByte(long l , int pos , byte b) {
        long temp = ByteOperation.toUnsignedLong(b);
        switch (pos) {
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
        case 4:
            temp <<= 32;
            break;
        case 5:
            temp <<= 40;
            break;
        case 6:
            temp <<= 48;
            break;
        case 7:
            temp <<= 56;
            break;
        default:
            throw new IllegalArgumentException();
        }
        return l | temp;
    }

    public static final byte[] toByte(long l) {
        return new byte[] {
            LongOperation.getByte(l , 0) , LongOperation.getByte(l , 1) , LongOperation.getByte(l , 2) ,
            LongOperation.getByte(l , 3) , LongOperation.getByte(l , 4) , LongOperation.getByte(l , 5) ,
            LongOperation.getByte(l , 6) , LongOperation.getByte(l , 7)
        };
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

    public static final ByteWrapper wrap(long[] ls) {
        return new LongByteWrapper(ls);
    }

    public static final ByteWrapper wrap(long[] ls , int start , int end) {
        return new LongByteWrapper(ls , start , end);
    }

    public LongOperation() {
        throw new AssertionError("No " + LongOperation.class + " for you!");
    }
}
