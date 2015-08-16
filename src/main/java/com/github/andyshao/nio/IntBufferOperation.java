package com.github.andyshao.nio;

import java.nio.IntBuffer;

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
public final class IntBufferOperation {
    public static int[] getInts(IntBuffer buffer , int start , int length) {
        final IntBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return usedArray(tmp);
    }

    public static int indexOf(IntBuffer buffer , char... cs) {
        //TODO
        return -1;
    }

    public static int lastIndexOf(IntBuffer buffer , char... cs) {
        //TODO
        return -1;
    }

    public static int[] usedArray(IntBuffer buffer) {
        final int[] result = new int[buffer.limit() - buffer.position()];
        buffer.get(result);
        return result;
    }

    private IntBufferOperation() {
        throw new AssertionError("No " + IntBufferOperation.class + " installment for you!");
    }
}
