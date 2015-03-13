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

    private IntegerOperation() {
        throw new AssertionError("No " + IntegerOperation.class + " for you!");
    }
}
