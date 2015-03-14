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

    public static final byte[] toByte(short s) {
        return new byte[] {
            (byte) (0x00ff & s) , (byte) ((0xff00 & s) >> 8)
        };
    }

    public static final short valueOf(byte[] bs) {
        short s = 0x0000;
        s |= bs[0];
        s |= bs[1] << 8;
        return s;
    }

    public static final int toUnsingedInt(short unsignedShort) {
        return 0x0000ffff & unsignedShort;
    }

    public static final long toUnsingedLong(short unsingedShort) {
        return 0x000000000000ffffL & unsingedShort;
    }

    private ShortOperation() {
        throw new AssertionError("No " + ShortOperation.class + " for you!");
    }
}
