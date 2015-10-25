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
    /**
     * Get data by position
     * 
     * @param buffer the {@link IntBuffer} which should be extracted
     * @param start the start position of {@link IntBuffer} which should be
     *            extracted
     * @param length the length of {@link IntBuffer} which should be extracted
     * @return the data you want to
     */
    public static int[] getInts(IntBuffer buffer , int start , int length) {
        final IntBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return IntBufferOperation.usedArray(tmp);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link IntBuffer} which should be searched
     * @param start the start position of {@link IntBuffer}
     * @param length the length of {@link IntBuffer}
     * @param is the data which should be found for
     * @return if can't find out then return -1
     */
    public static int indexOf(IntBuffer buffer , int start , int length , int... is) {
        IntBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return IntBufferOperation.indexOf(tmp , is);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link IntBuffer} which should be searched
     * @param is the data which should be found out
     * @return if can't find out then return -1
     * @see IntBufferOperation#indexOf(IntBuffer, int, int, int...)
     */
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

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link IntBuffer} which should be searched
     * @param start the start position of {@link IntBuffer}
     * @param length the length of {@link IntBuffer}
     * @param is the data which should be found out
     * @return if can't find out then return -1
     */
    public static int lastIndexOf(IntBuffer buffer , int start , int length , int... is) {
        IntBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return IntBufferOperation.lastIndexOf(tmp , is);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link IntBuffer} which should be searched
     * @param is the data which should be found out
     * @return if can't find out then return -1
     * @see IntBufferOperation#lastIndexOf(IntBuffer, int, int, int...)
     */
    public static int lastIndexOf(IntBuffer buffer , int[] is) {
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

    /**
     * Only return a space which great than buffer.position() and less than
     * buffer.limit()<br>
     * <b>NOTE: It is will update the buffer</b>
     * 
     * @param buffer the {@link IntBuffer} which should be found out
     * @return the data you want to
     */
    public static int[] usedArray(IntBuffer buffer) {
        final int[] result = new int[buffer.limit() - buffer.position()];
        buffer.get(result);
        return result;
    }

    private IntBufferOperation() {
        throw new AssertionError("No " + IntBufferOperation.class + " installment for you!");
    }
}
