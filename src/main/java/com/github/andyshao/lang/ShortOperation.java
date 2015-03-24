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
public final class ShortOperation {

    public static final byte getByte(short s , int position) {
        switch (position) {
        case 0:
            return (byte) (0x00ff & s);
        case 1:
            return (byte) ((0xff00 & s) >> 8);
        default:
            throw new IllegalArgumentException();
        }
    }

    public static final byte[] toByte(short s) {
        return new byte[] {
            ShortOperation.getByte(s , 0) , ShortOperation.getByte(s , 1)
        };
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
