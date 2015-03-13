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

    private ShortOperation() {
        throw new AssertionError("No " + ShortOperation.class + " for you!");
    }
}
