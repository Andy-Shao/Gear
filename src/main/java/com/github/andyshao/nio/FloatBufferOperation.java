package com.github.andyshao.nio;

import java.nio.FloatBuffer;

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
public class FloatBufferOperation {
    public static float[] getFloats(FloatBuffer buffer , int start , int length) {
        final FloatBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.position(start + length);
        return FloatBufferOperation.usedArray(tmp);
    }

    public static int indexOf(FloatBuffer buffer , char... cs) {
        //TODO
        return -1;
    }

    public static int lastIndexOf(FloatBuffer buffer , char... cs) {
        //TODO
        return -1;
    }

    public static float[] usedArray(FloatBuffer buffer) {
        final float[] result = new float[buffer.limit() - buffer.position()];
        buffer.get(result);
        return result;
    }

    private FloatBufferOperation() {
        throw new AssertionError("No " + FloatBufferOperation.class + " installment for you!");
    }
}
