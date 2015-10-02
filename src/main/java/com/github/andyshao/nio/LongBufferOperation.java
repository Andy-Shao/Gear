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
public final class LongBufferOperation {

    public static long[] getLongs(LongBuffer buffer , int start , int length) {
        final LongBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return LongBufferOperation.usedArray(tmp);
    }

    public static int indexOf(LongBuffer buffer , int start , int length , long... ls) {
        LongBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return indexOf(tmp , ls);
    }

    public static int indexOf(LongBuffer buffer , long... ls) {
        if (ls.length == 0) throw new IllegalArgumentException("ls is empty");
        if (ls.length > buffer.limit() - buffer.position()) return -1;
        final LongBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = 0;
        int position = -1;
        LOOP: while (tmpByteBuffer.position() < tmpByteBuffer.limit()) {
            final long value = tmpByteBuffer.get();
            if (Long.compare(value , ls[index++]) == 0) {
                if (index == 1) position = tmpByteBuffer.position() - 1;
                if (index == ls.length) break LOOP;
            } else if (Long.compare(value , ls[0]) == 0) {
                index = 1;
                position = tmpByteBuffer.position() - 1;
                if (index == ls.length) break LOOP;
            } else {
                index = 0;
                position = -1;
            }
        }
        return position;
    }

    public static int lastIndexOf(LongBuffer buffer , int start , int length , long... ls) {
        LongBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return lastIndexOf(tmp , ls);
    }

    public static int lastIndexOf(LongBuffer buffer , long... ls) {
        if (ls.length == 0) throw new IllegalArgumentException("ls is empty");
        if (ls.length > buffer.limit() - buffer.position()) return -1;
        final LongBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = ls.length;
        int position = -1;
        LOOP: for (int i = tmpByteBuffer.limit() - 1 ; i >= tmpByteBuffer.position() ; i--) {
            final long value = tmpByteBuffer.get(i);
            if (Long.compare(value , ls[--index]) == 0) {
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else if (Long.compare(value , ls[ls.length - 1]) == 0) {
                index = ls.length - 1;
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else {
                index = ls.length;
                position = -1;
            }
        }
        return position;
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
