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
    public static int indexOf(ByteBuffer buffer , byte... bs) {
        //TODO
        if (bs.length == 0) throw new IllegalArgumentException("bs is null");
        if (bs.length > buffer.limit() - buffer.position()) return -1;
        final ByteBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = 0;
        int position = -1;
        while (tmpByteBuffer.position() < tmpByteBuffer.limit())
            if (Byte.compare(tmpByteBuffer.get() , bs[index++]) == 0) {
                if (index == 1) position = tmpByteBuffer.position() - 1;
                else if (index == bs.length) break;
            } else {
                index = 0;
                position = -1;
            }
        return position;
    }

    public static int lastIndexOf(ByteBuffer buffer , byte... bs) {
        //TODO
        return -1;
    }

    /**
     * Only return a space which great than buffer.position() and less than
     * buffer.limit()
     * 
     * @param buffer the buffer which has the info
     * @return return the byte array and the info in it
     */
    public static byte[] usedArray(ByteBuffer buffer) {
        byte[] bs = new byte[buffer.limit() - buffer.position()];
        buffer.get(bs);
        return bs;
    }

    private ByteBufferOperation() {
        throw new AssertionError("No support instance " + ByteBufferOperation.class.getName());
    }
}
