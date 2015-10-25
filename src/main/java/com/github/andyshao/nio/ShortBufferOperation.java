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
    /**
     * Get the data by position
     * 
     * @param buffer the {@link ShortBuffer} which should be extracted
     * @param start the start position of {@link ShortBuffer}
     * @param length the length of {@link ShortBuffer}
     * @return the data you want to
     */
    public static short[] getShorts(ShortBuffer buffer , int start , int length) {
        final ShortBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return ShortBufferOperation.usedArray(tmp);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link ShortBuffer} which should be searched
     * @param start the start position of {@link ShortBuffer}
     * @param length the length of {@link ShortBuffer}
     * @param ss the data which should be found out
     * @return if can't find out then return -1
     */
    public static int indexOf(ShortBuffer buffer , int start , int length , short... ss) {
        ShortBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return ShortBufferOperation.indexOf(tmp , ss);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link ShortBuffer} which should be searched
     * @param ss the data which should be found out
     * @return if can't find out then return -1
     * @see ShortBufferOperation#indexOf(ShortBuffer, int, int, short...)
     */
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

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link ShortBuffer} which should be searched
     * @param start the start position of {@link ShortBuffer}
     * @param length the length of {@link ShortBuffer}
     * @param ss the data which should be found out
     * @return if can't find out then return -1
     */
    public static int lastIndexOf(ShortBuffer buffer , int start , int length , short... ss) {
        ShortBuffer tmp = buffer.asReadOnlyBuffer();
        buffer.position(start);
        buffer.limit(start + length);
        return ShortBufferOperation.lastIndexOf(tmp , ss);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link ShortBuffer} which should be searched
     * @param ss the data which should be found out
     * @return if can't find out then return -1
     * @see ShortBufferOperation#lastIndexOf(ShortBuffer, int, int, short...)
     */
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

    /**
     * Only return a space which great than buffer.position() and less than
     * buffer.limit()<br>
     * <b>NOTE: It is will update the buffer</b>
     * 
     * @param buffer the {@link ShortBuffer} which should be extracted
     * @return the data which you want to
     */
    public static short[] usedArray(ShortBuffer buffer) {
        final short[] result = new short[buffer.limit() - buffer.position()];
        buffer.get(result);
        return result;
    }

    private ShortBufferOperation() {
        throw new AssertionError("No " + ShortBufferOperation.class + " installment for you!");
    }
}
