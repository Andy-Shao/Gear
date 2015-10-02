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
public final class FloatBufferOperation {
    public static float[] getFloats(FloatBuffer buffer , int start , int length) {
        final FloatBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.position(start + length);
        return FloatBufferOperation.usedArray(tmp);
    }

    public static int indexOf(FloatBuffer buffer , float... fs) {
        if (fs.length == 0) throw new IllegalArgumentException("fs is empty");
        if (fs.length > buffer.limit() - buffer.position()) return -1;
        final FloatBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = 0;
        int position = -1;
        LOOP: while (tmpByteBuffer.position() < tmpByteBuffer.limit()) {
            final float value = tmpByteBuffer.get();
            if (Float.compare(value , fs[index++]) == 0) {
                if (index == 1) position = tmpByteBuffer.position() - 1;
                if (index == fs.length) break LOOP;
            } else if (Float.compare(value , fs[0]) == 0) {
                index = 1;
                position = tmpByteBuffer.position() - 1;
                if (index == fs.length) break LOOP;
            } else {
                index = 0;
                position = -1;
            }
        }
        return position;
    }

    public static int indexOf(FloatBuffer buffer , int start , int length , float... fs) {
        FloatBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return FloatBufferOperation.indexOf(tmp , fs);
    }

    public static int lastIndexOf(FloatBuffer buffer , float... fs) {
        if (fs.length == 0) throw new IllegalArgumentException("fs is empty");
        if (fs.length > buffer.limit() - buffer.position()) return -1;
        final FloatBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = fs.length;
        int position = -1;
        LOOP: for (int i = tmpByteBuffer.limit() - 1 ; i >= tmpByteBuffer.position() ; i--) {
            final float value = tmpByteBuffer.get(i);
            if (Float.compare(value , fs[--index]) == 0) {
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else if (Float.compare(value , fs[fs.length - 1]) == 0) {
                index = fs.length - 1;
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else {
                index = fs.length;
                position = -1;
            }
        }
        return position;
    }

    public static int lastIndexOf(FloatBuffer buffer , int start , int length , float... fs) {
        FloatBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return FloatBufferOperation.lastIndexOf(tmp , fs);
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
