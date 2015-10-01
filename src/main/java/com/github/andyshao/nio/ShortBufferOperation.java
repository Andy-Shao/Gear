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

    public static int indexOf(ShortBuffer buffer , int start , int length , short... ss) {
        //TODO
        return -1;
    }

    public static int indexOf(ShortBuffer buffer , short... ss) {
        if (ss.length == 0) throw new IllegalArgumentException("ss is empty");
        if (ss.length > buffer.limit() - buffer.position()) return -1;
        final ShortBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = 0;
        int position = -1;
        LOOP: while (tmpByteBuffer.position() < tmpByteBuffer.limit()) {
            final short value = tmpByteBuffer.get();
            if (Short.compare(value , ss[index++]) == 0) {
                if (index == 1) position = tmpByteBuffer.position() - 1;
                if (index == ss.length) break LOOP;
            } else if (Short.compare(value , ss[0]) == 0) {
                index = 1;
                position = tmpByteBuffer.position() - 1;
                if (index == ss.length) break LOOP;
            } else {
                index = 0;
                position = -1;
            }
        }
        return position;
    }

    public static int lastIndexOf(ShortBuffer buffer , int start , int length , short... ss) {
        //TODO
        return -1;
    }

    public static int lastIndexOf(ShortBuffer buffer , short... ss) {
        if (ss.length == 0) throw new IllegalArgumentException("ss is empty");
        if (ss.length > buffer.limit() - buffer.position()) return -1;
        final ShortBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = ss.length;
        int position = -1;
        LOOP: for (int i = tmpByteBuffer.limit() - 1 ; i >= tmpByteBuffer.position() ; i--) {
            final short value = tmpByteBuffer.get(i);
            if (Short.compare(value , ss[--index]) == 0) {
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else if (Short.compare(value , ss[ss.length - 1]) == 0) {
                index = ss.length - 1;
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else {
                index = ss.length;
                position = -1;
            }
        }
        return position;
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
