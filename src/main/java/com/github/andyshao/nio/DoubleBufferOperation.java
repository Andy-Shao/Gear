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
public class DoubleBufferOperation {
    public static double[] getDoubles(DoubleBuffer buffer , int start , int length) {
        final DoubleBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return DoubleBufferOperation.usedArray(tmp);
    }

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

    public static double[] usedArray(DoubleBuffer buffer) {
        final double[] result = new double[buffer.limit() - buffer.position()];
        buffer.get(result);
        return result;
    }

    private DoubleBufferOperation() {
        throw new AssertionError("No " + DoubleBufferOperation.class + " installment for you!");
    }
}
