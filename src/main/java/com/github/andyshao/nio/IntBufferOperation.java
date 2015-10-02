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
        return IntBufferOperation.usedArray(tmp);
    }

    public static int indexOf(IntBuffer buffer , int[] is) {
        if (is.length == 0) throw new IllegalArgumentException("is is empty");
        if (is.length > buffer.limit() - buffer.position()) return -1;
        final IntBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = 0;
        int position = -1;
        LOOP: while (tmpByteBuffer.position() < tmpByteBuffer.limit()) {
            final int value = tmpByteBuffer.get();
            if (Integer.compare(value , is[index++]) == 0) {
                if (index == 1) position = tmpByteBuffer.position() - 1;
                if (index == is.length) break LOOP;
            } else if (Integer.compare(value , is[0]) == 0) {
                index = 1;
                position = tmpByteBuffer.position() - 1;
                if (index == is.length) break LOOP;
            } else {
                index = 0;
                position = -1;
            }
        }
        return position;
    }

    public static int indexOf(IntBuffer buffer , int start , int length , int... is) {
        //TODO
        return -1;
    }

    public static int lastIndexOf(IntBuffer buffer , int... is) {
        if (is.length == 0) throw new IllegalArgumentException("is is empty");
        if (is.length > buffer.limit() - buffer.position()) return -1;
        final IntBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = is.length;
        int position = -1;
        LOOP: for (int i = tmpByteBuffer.limit() - 1 ; i >= tmpByteBuffer.position() ; i--) {
            final int value = tmpByteBuffer.get(i);
            if (Integer.compare(value , is[--index]) == 0) {
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else if (Integer.compare(value , is[is.length - 1]) == 0) {
                index = is.length - 1;
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else {
                index = is.length;
                position = -1;
            }
        }
        return position;
    }

    public static int lastIndexOf(IntBuffer buffer , int start , int length , int... is) {
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
