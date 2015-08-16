package com.github.andyshao.nio;

import java.nio.ShortBuffer;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jun 15, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ShortBufferOperation {

    public static short[] getShorts(ShortBuffer buffer , int start , int length) {
        final ShortBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return ShortBufferOperation.usedArray(tmp);
    }

    public static int indexOf(ShortBuffer buffer , char... cs) {
        //TODO
        return -1;
    }

    public static int lastIndexOf(ShortBuffer buffer , char... cs) {
        //TODO
        return -1;
    }

    public static short[] usedArray(ShortBuffer buffer) {
        final short[] result = new short[buffer.limit() - buffer.position()];
        buffer.get(result);
        return result;
    }

    private ShortBufferOperation() {
        throw new AssertionError("No " + ShortBufferOperation.class + " installment for you!");
    }
}
