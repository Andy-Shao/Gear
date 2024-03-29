package com.github.andyshao.nio;

import com.github.andyshao.lang.GeneralSystemProperty;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Function;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jun 21, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class ByteBufferReader implements BufferReader<byte[]> {
    /**
     * 
     * Title:<br>
     * Descript:<br>
     * Copyright: Copryright(c) Oct 23, 2015<br>
     * Encoding:UNIX UTF-8
     * 
     * @author Andy.Shao
     *
     */
    public static class SeparateByBytes implements Function<ByteBuffer , BufferReader.SeparatePoint> {
        private final byte[] key;

        /**
         * build {@link SeparateByBytes}
         * @param key the key
         */
        public SeparateByBytes(byte[] key) {
            this.key = key;
        }

        @Override
        public com.github.andyshao.nio.BufferReader.SeparatePoint apply(ByteBuffer t) {
            int index = ByteBufferOperation.indexOf(t , this.key);
            return new BufferReader.SeparatePoint(index , this.key.length + index);
        }
    }

    private ByteBuffer buffer;
    private int bufferSize;
    private final ReadableByteChannel channel;
    private String encoding = GeneralSystemProperty.FILE_ENCODING.value();
    private Function<ByteBuffer , BufferReader.SeparatePoint> findSeparatePoint = (buffer) -> {
        try {
            byte[] key = GeneralSystemProperty.LINE_SEPARATOR.value().getBytes(ByteBufferReader.this.getEncoding());
            int index = ByteBufferOperation.indexOf(buffer , key);
            return new BufferReader.SeparatePoint(index , key.length + index);
        } catch (UnsupportedEncodingException exception) {
            throw new RuntimeException(exception);
        }
    };
    private volatile boolean hasNext = true;

    /**
     * Create the {@link ByteBufferReader}<br>
     * set the capacity of {@link ByteBuffer} is 1024
     * 
     * @param channel the channel which is {@link ReadableByteChannel}
     * @see ByteBufferReader#ByteBufferReader(ReadableByteChannel, int)
     */
    public ByteBufferReader(ReadableByteChannel channel) {
        this(channel , 1024);
    }

    /**
     * Create the {@link ByteBufferReader}
     * 
     * @param channel the channel which is {@link ReadableByteChannel}
     * @param bufferSize the capacity of {@link ByteBuffer}
     */
    public ByteBufferReader(ReadableByteChannel channel , int bufferSize) {
        this.channel = channel;
        this.bufferSize = bufferSize;
        this.buffer = ByteBuffer.allocate(this.bufferSize);
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }

    /**
     * Get the encoding of data
     * 
     * @return encoding
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * Get the {@link BufferReader.SeparatePoint} converter
     * 
     * @return {@link Function}
     */
    public Function<ByteBuffer , BufferReader.SeparatePoint> getFindSeparatePoint() {
        return this.findSeparatePoint;
    }

    @Override
    public boolean hasNext() {
        return this.hasNext;
    }

    @Override
    public byte[] read() throws IOException {
        READ: while (this.channel.read(this.buffer) != -1) {
            ByteBuffer temp = this.buffer.asReadOnlyBuffer();
            temp.limit(temp.position());
            temp.position(0);
            BufferReader.SeparatePoint point = this.findSeparatePoint.apply(temp);
            if (point.getSeparatePoint() == -1) {
                if (this.buffer.hasRemaining()) continue READ;
                if (this.bufferSize == Integer.MAX_VALUE) throw new BufferReaderException("the byteBuffer is overflow");
                this.bufferSize = (this.bufferSize << 1) > 0 ? this.bufferSize << 1 : Integer.MAX_VALUE;
                byte[] remaining = ByteBufferOperation.usedArray(temp);
                this.buffer = ByteBuffer.allocate(this.bufferSize);
                this.buffer.put(remaining);
                continue READ;
            } else {
                final int limit = temp.limit();
                temp.limit(point.getSeparatePoint());
                byte[] result = ByteBufferOperation.usedArray(temp);
                temp.limit(limit);
                temp.position(point.getNextStartSit() > limit ? limit : point.getNextStartSit());
                byte[] remaining = ByteBufferOperation.usedArray(temp);
                this.buffer.clear();
                this.buffer.put(remaining);
                return result;
            }
        }
        if (this.buffer.position() != 0) {
            ByteBuffer temp = this.buffer.asReadOnlyBuffer();
            temp.limit(temp.position());
            temp.position(0);
            BufferReader.SeparatePoint point = this.findSeparatePoint.apply(temp);
            if (point.getSeparatePoint() == -1) {
                this.hasNext = false;
                return ByteBufferOperation.usedArray(temp);
            } else {
                final int limit = temp.limit();
                temp.limit(point.getSeparatePoint());
                byte[] result = ByteBufferOperation.usedArray(temp);
                temp.limit(limit);
                temp.position(point.getNextStartSit() > limit ? limit : point.getNextStartSit());
                byte[] remaining = ByteBufferOperation.usedArray(temp);
                this.buffer.clear();
                this.buffer.put(remaining);
                return result;
            }
        } else {
            this.hasNext = false;
            return null;
        }

    }

    /**
     * Set the encoding of data's
     * 
     * @param encoding the encoding of data's
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Set the {@link BufferReader.SeparatePoint} converter
     * 
     * @param findSeparatePoint the {@link BufferReader.SeparatePoint} converter
     */
    public void setFindSeparatePoint(Function<ByteBuffer , BufferReader.SeparatePoint> findSeparatePoint) {
        this.findSeparatePoint = findSeparatePoint;
    }
}
