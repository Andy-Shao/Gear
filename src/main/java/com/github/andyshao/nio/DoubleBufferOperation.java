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
public final class DoubleBufferOperation {
    /**
     * Get data by position
     * 
     * @param buffer the {@link DoubleBuffer} which should be extracted
     * @param start the start position of {@link DoubleBuffer} which part should
     *            be extracted
     * @param length the length of {@link DoubleBuffer} which is data that you
     *            need it
     * @return the data which you want to
     */
    public static double[] getDoubles(DoubleBuffer buffer , int start , int length) {
        final DoubleBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return DoubleBufferOperation.usedArray(tmp);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link DoubleBuffer} which should be found out from
     * @param ds the data which should be found out for
     * @return if can't find out then return -1
     * @see DoubleBufferOperation#indexOf(DoubleBuffer, int, int, double...)
     */
    public static int indexOf(DoubleBuffer buffer , double... ds) {
        if (ds.length == 0) throw new IllegalArgumentException("ds is empty");
        if (ds.length > buffer.limit() - buffer.position()) return -1;
        final DoubleBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = 0;
        int position = -1;
        LOOP: while (tmpByteBuffer.position() < tmpByteBuffer.limit()) {
            final double value = tmpByteBuffer.get();
            if (Double.compare(value , ds[index++]) == 0) {
                if (index == 1) position = tmpByteBuffer.position() - 1;
                if (index == ds.length) break LOOP;
            } else if (Double.compare(value , ds[0]) == 0) {
                index = 1;
                position = tmpByteBuffer.position() - 1;
                if (index == ds.length) break LOOP;
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
     * @param buffer the {@link DoubleBuffer} which should be found out from
     * @param start the start position of {@link DoubleBuffer}
     * @param length the length of {@link DoubleBuffer} which part of it that
     *            should be found out
     * @param ds the data which should be found out for
     * @return if can't find out then return -1
     */
    public static int indexOf(DoubleBuffer buffer , int start , int length , double... ds) {
        DoubleBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return DoubleBufferOperation.indexOf(tmp , ds);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from end to head.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link DoubleBuffer} which should be found out from
     * @param ds the data which should be found out for
     * @return if can't find out then return -1
     * @see DoubleBufferOperation#lastIndexOf(DoubleBuffer, int, int, double...)
     */
    public static int lastIndexOf(DoubleBuffer buffer , double... ds) {
        if (ds.length == 0) throw new IllegalArgumentException("ds is empty");
        if (ds.length > buffer.limit() - buffer.position()) return -1;
        final DoubleBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = ds.length;
        int position = -1;
        LOOP: for (int i = tmpByteBuffer.limit() - 1 ; i >= tmpByteBuffer.position() ; i--) {
            final double value = tmpByteBuffer.get(i);
            if (Double.compare(value , ds[--index]) == 0) {
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else if (Double.compare(value , ds[ds.length - 1]) == 0) {
                index = ds.length - 1;
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else {
                index = ds.length;
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
     * @param buffer the {@link DoubleBuffer} which should be found out from
     * @param start the start position of {@link DoubleBuffer}
     * @param length the length of {@link DoubleBuffer} which should be searched
     * @param ds the data which should be found out for
     * @return if can't find out then return -1
     */
    public static int lastIndexOf(DoubleBuffer buffer , int start , int length , double... ds) {
        DoubleBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return DoubleBufferOperation.lastIndexOf(tmp , ds);
    }

    /**
     * Only return a space which great than buffer.position() and less than
     * buffer.limit()<br>
     * <b>NOTE: It is will update the buffer</b>
     * 
     * @param buffer the {@link DoubleBuffer} which should be extracted
     * @return the data you want to
     */
    public static double[] usedArray(DoubleBuffer buffer) {
        final double[] result = new double[buffer.limit() - buffer.position()];
        buffer.get(result);
        return result;
    }

    private DoubleBufferOperation() {
        throw new AssertionError("No " + DoubleBufferOperation.class + " installment for you!");
    }
}
