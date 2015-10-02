package com.github.andyshao.nio;

import java.nio.ByteBuffer;

/**
 * 
 * Descript:<br>
 * Copyright: Copyright(c) Aug 4, 2014<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ByteBufferOperation {
    /**
     * Get the data by position
     * 
     * @param buffer the {@link ByteBuffer} which is extract the data
     * @param start the start point of data
     * @param length the length of data
     * @return the data which you want to
     */
    public static byte[] getBytes(ByteBuffer buffer , int start , int length) {
        final ByteBuffer tempBuffer = buffer.asReadOnlyBuffer();
        tempBuffer.position(start);
        tempBuffer.limit(start + length);
        return ByteBufferOperation.usedArray(tempBuffer);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link ByteBuffer} which you want to find from
     * @param bs the data which you want to find from {@link ByteBuffer}
     * @return if it can't be found out then return -1
     */
    public static int indexOf(ByteBuffer buffer , byte... bs) {
        if (bs.length == 0) throw new IllegalArgumentException("bs is empty");
        if (bs.length > buffer.limit() - buffer.position()) return -1;
        final ByteBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = 0;
        int position = -1;
        LOOP: while (tmpByteBuffer.position() < tmpByteBuffer.limit()) {
            final byte value = tmpByteBuffer.get();
            if (Byte.compare(value , bs[index++]) == 0) {
                if (index == 1) position = tmpByteBuffer.position() - 1;
                if (index == bs.length) break LOOP;
            } else if (Byte.compare(value , bs[0]) == 0) {
                index = 1;
                position = tmpByteBuffer.position() - 1;
                if (index == bs.length) break LOOP;
            } else {
                index = 0;
                position = -1;
            }
        }
        return position;
    }

    public static int indexOf(ByteBuffer buffer , int start , int length , byte... bs) {
        final int end = start + length;
        final ByteBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(end);
        return ByteBufferOperation.indexOf(tmp , bs);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from end to head.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link ByteBuffer} which you want to find from
     * @param bs the data which you want to find from {@link ByteBuffer}
     * @return if it can't be found out then return -1
     */
    public static int lastIndexOf(ByteBuffer buffer , byte... bs) {
        if (bs.length == 0) throw new IllegalArgumentException("bs is empty");
        if (bs.length > buffer.limit() - buffer.position()) return -1;
        final ByteBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = bs.length;
        int position = -1;
        LOOP: for (int i = tmpByteBuffer.limit() - 1 ; i >= tmpByteBuffer.position() ; i--) {
            final byte value = tmpByteBuffer.get(i);
            if (Byte.compare(value , bs[--index]) == 0) {
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else if (Byte.compare(value , bs[bs.length - 1]) == 0) {
                index = bs.length - 1;
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else {
                index = bs.length;
                position = -1;
            }
        }
        return position;
    }

    public static int lastIndexOf(ByteBuffer buffer , int start , int length , byte... bs) {
        final int end = start + length;
        final ByteBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(end);
        return ByteBufferOperation.lastIndexOf(tmp , bs);
    }

    /**
     * Only return a space which great than buffer.position() and less than
     * buffer.limit()<br>
     * <b>NOTE: It is will update the buffer</b>
     * 
     * @param buffer the buffer which has the info
     * @return return the byte array and the info in it
     */
    public static byte[] usedArray(ByteBuffer buffer) {
        final byte[] bs = new byte[buffer.limit() - buffer.position()];
        buffer.get(bs);
        return bs;
    }

    private ByteBufferOperation() {
        throw new AssertionError("No support instance " + ByteBufferOperation.class.getName());
    }
}
