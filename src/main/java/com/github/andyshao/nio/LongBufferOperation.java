package com.github.andyshao.nio;

import java.nio.LongBuffer;

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
public class LongBufferOperation {

    public static long[] getLongs(LongBuffer buffer , int start , int length) {
        final LongBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return LongBufferOperation.usedArray(tmp);
    }

    public static int indexOf(LongBuffer buffer , char... cs) {
        //TODO
        return -1;
    }

    public static int lastIndexOf(LongBuffer buffer , char... cs) {
        //TODO
        return -1;
    }

    public static long[] usedArray(LongBuffer buffer) {
        final long[] result = new long[buffer.limit() - buffer.position()];
        buffer.get(result);
        return result;
    }

    private LongBufferOperation() {
        throw new AssertionError("No " + LongBufferOperation.class + " installment for you!");
    }
}
