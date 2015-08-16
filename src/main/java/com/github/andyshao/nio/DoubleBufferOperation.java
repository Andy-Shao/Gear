package com.github.andyshao.nio;

import java.nio.DoubleBuffer;

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
public class DoubleBufferOperation {
    public static double[] getDoubles(DoubleBuffer buffer , int start , int length) {
        final DoubleBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return usedArray(tmp);
    }

    public static int indexOf(DoubleBuffer buffer , char... cs) {
        //TODO
        return -1;
    }

    public static int lastIndexOf(DoubleBuffer buffer , char... cs) {
        //TODO
        return -1;
    }

    public static double[] usedArray(DoubleBuffer buffer) {
        final double[] result = new double[buffer.limit() - buffer.position()];
        buffer.get(result);
        return result;
    }

    private DoubleBufferOperation() {
        throw new AssertionError("No " + DoubleBufferOperation.class + " installment for you!");
    }
}
