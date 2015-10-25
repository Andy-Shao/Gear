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
    /**
     * Get by position
     * 
     * @param buffer the {@link LongBuffer} which should be extracted
     * @param start the start position of {@link LongBuffer}
     * @param length the length of {@link LongBuffer}
     * @return the data you want to
     */
    public static long[] getLongs(LongBuffer buffer , int start , int length) {
        final LongBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return LongBufferOperation.usedArray(tmp);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link LongBuffer} which should be searched
     * @param start the start position of {@link LongBuffer}
     * @param length the length of {@link LongBuffer}
     * @param ls the data which should be found out
     * @return if can't find out then return -1
     */
    public static int indexOf(LongBuffer buffer , int start , int length , long... ls) {
        LongBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return LongBufferOperation.indexOf(tmp , ls);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link LongBuffer} which should be searched
     * @param ls the data which should be found out
     * @return if can't find out then return -1
     * @see LongBufferOperation#indexOf(LongBuffer, int, int, long...)
     */
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

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link LongBuffer} which should be searched
     * @param start the start position of {@link LongBuffer}
     * @param length the length of {@link LongBuffer}
     * @param ls the data which should be found out
     * @return if can't find out then return -1
     */
    public static int lastIndexOf(LongBuffer buffer , int start , int length , long... ls) {
        LongBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return LongBufferOperation.lastIndexOf(tmp , ls);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link LongBuffer} which should be searched
     * @param ls the data which should be found out
     * @return if can't find out then return -1
     * @see LongBufferOperation#lastIndexOf(LongBuffer, int, int, long...)
     */
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

    /**
     * Only return a space which great than buffer.position() and less than
     * buffer.limit()<br>
     * <b>NOTE: It is will update the buffer</b>
     * 
     * @param buffer the {@link LongBuffer} which should be extrected
     * @return the data you want to
     */
    public static long[] usedArray(LongBuffer buffer) {
        final long[] result = new long[buffer.limit() - buffer.position()];
        buffer.get(result);
        return result;
    }

    private LongBufferOperation() {
        throw new AssertionError("No " + LongBufferOperation.class + " installment for you!");
    }
}
