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
    /**
     * Get data by position
     * 
     * @param buffer the {@link FloatBuffer} which should be extracted
     * @param start the start position of data of {@link FloatBuffer}'s
     * @param length the length of data of {@link FloatBuffer}
     * @return the data you want to
     */
    public static float[] getFloats(FloatBuffer buffer , int start , int length) {
        final FloatBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.position(start + length);
        return FloatBufferOperation.usedArray(tmp);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link FloatBuffer} which should be searched
     * @param fs the data which should be found for
     * @return if can't find out then return -1
     * @see FloatBufferOperation#indexOf(FloatBuffer, int, int, float...)
     */
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

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link FloatBuffer} which should be searched
     * @param start the start position of {@link FloatBuffer} which should be
     *            searched
     * @param length the length of {@link FloatBuffer} which should be searched
     * @param fs the data which should be found out
     * @return if can't find out then return -1
     */
    public static int indexOf(FloatBuffer buffer , int start , int length , float... fs) {
        FloatBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return FloatBufferOperation.indexOf(tmp , fs);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from end to head.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link FloatBuffer} which should be searched
     * @param fs the data which should be found out
     * @return if can't find out then return -1
     * @see FloatBufferOperation#lastIndexOf(FloatBuffer, int, int, float...)
     */
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

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from end to head.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link FloatBuffer} which should be searched
     * @param start the start position of {@link FloatBuffer} which should be
     *            searched
     * @param length the length of {@link FloatBuffer} which should be searched
     * @param fs the data which should be found out
     * @return if can't find out then return -1
     */
    public static int lastIndexOf(FloatBuffer buffer , int start , int length , float... fs) {
        FloatBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return FloatBufferOperation.lastIndexOf(tmp , fs);
    }

    /**
     * Only return a space which great than buffer.position() and less than
     * buffer.limit()<br>
     * <b>NOTE: It is will update the buffer</b>
     * 
     * @param buffer the {@link FloatBuffer} which should be extracted
     * @return the data you want to
     */
    public static float[] usedArray(FloatBuffer buffer) {
        final float[] result = new float[buffer.limit() - buffer.position()];
        buffer.get(result);
        return result;
    }

    private FloatBufferOperation() {
        throw new AssertionError("No " + FloatBufferOperation.class + " installment for you!");
    }
}
