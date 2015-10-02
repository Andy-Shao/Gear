package com.github.andyshao.lang;

import java.math.BigInteger;
import java.util.Comparator;

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
    private static final Comparator<Short> COMPARATOR = (x , y) -> Short.compare(x , y);

    public static final int bitGet(BigInteger pos , final short[] array) {
        return ByteOperation.bitGet(pos , array , ByteWrapper.SHORT_BYTE_WRAPPER);
    }

    public static final short[] bitOxr(final short[] b1 , final short[] b2 , int size) {
        return ByteOperation.bitOxr(b1 , b2 , BigInteger.valueOf(size).multiply(BigInteger.valueOf(2)) ,
            ByteWrapper.SHORT_BYTE_WRAPPER);
    }

    public static final short[] bitRotLeft(int count , final short[] array) {
        return ByteOperation.bitRotLeft(count , array , ByteWrapper.SHORT_BYTE_WRAPPER);
    }

    public static final short[] bitRotRight(int count , final short[] array) {
        return ByteOperation.bitRotRight(count , array , ByteWrapper.SHORT_BYTE_WRAPPER);
    }

    public static final short[] bitSet(BigInteger pos , int state , final short[] array) {
        return ByteOperation.bitSet(pos , state , array , ByteWrapper.SHORT_BYTE_WRAPPER);
    }

    public static final Comparator<Short> comparator() {
        return ShortOperation.COMPARATOR;
    }

    public static final short[] fill(int state , BigInteger startPos , BigInteger endPos , final short[] array) {
        return ByteOperation.fill(state , startPos , endPos , array , ByteWrapper.SHORT_BYTE_WRAPPER);
    }

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

    public static final byte[] toByte(short s) {
        return new byte[] { ShortOperation.getByte(s , 0) , ShortOperation.getByte(s , 1) };
    }

    public static final String toHexString(short s) {
        return Convert.BYTES_TO_HEX
            .convert(new byte[] { ShortOperation.getByte(s , 0) , ShortOperation.getByte(s , 1) });
    }

    public static final String toHexString(short... s) {
        StringBuilder builder = new StringBuilder();
        for (short sh : s)
            builder.append(ShortOperation.toHexString(sh));
        return builder.toString();
    }

    public static final String toString(short[] array) {
        return ByteOperation.toString(array , ByteWrapper.SHORT_BYTE_WRAPPER);
    }

    public static final int toUnsingedInt(short unsignedShort) {
        return 0x0000ffff & unsignedShort;
    }

    public static final long toUnsingedLong(short unsingedShort) {
        return 0x000000000000ffffL & unsingedShort;
    }

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
