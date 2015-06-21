package com.github.andyshao.nio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Function;

import com.github.andyshao.lang.GeneralSystemProperty;

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

    private int mark = 0;

    public ByteBufferReader(ReadableByteChannel channel) {
        this(channel , 1024);
    }

    public ByteBufferReader(ReadableByteChannel channel , int bufferSize) {
        this.channel = channel;
        this.bufferSize = bufferSize;
        this.buffer = ByteBuffer.allocate(this.bufferSize);
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }

    public String getEncoding() {
        return this.encoding;
    }

    public Function<ByteBuffer , BufferReader.SeparatePoint> getFindSeparatePoint() {
        return this.findSeparatePoint;
    }

    @Override
    public byte[] read() throws IOException {
        READ: while (this.channel.read(this.buffer) != -1) {
            ByteBuffer temp = this.buffer.asReadOnlyBuffer();
            temp.limit(temp.position());
            temp.position(this.mark);
            BufferReader.SeparatePoint point = this.findSeparatePoint.apply(temp);
            if (point.getSeparatePoint() == -1) {
                if (this.buffer.hasRemaining()) continue READ;
                else if (this.mark == 0) {
                    if (this.bufferSize == Integer.MAX_VALUE) throw new BufferReaderException(
                        "The byteBuffer is overflow");
                    this.bufferSize = (this.bufferSize << 1) > 0 ? this.bufferSize << 1 : Integer.MAX_VALUE;
                    byte[] remaning = ByteBufferOperation.usedArray(temp);
                    this.buffer = ByteBuffer.allocate(this.bufferSize);
                    this.buffer.put(remaning);
                    continue READ;
                } else {
                    byte[] remaining = ByteBufferOperation.usedArray(temp);
                    this.buffer.clear();
                    this.buffer.put(remaining);
                    this.mark = 0;
                    continue READ;
                }
            } else {
                temp.limit(point.getSeparatePoint());
                this.mark = point.getNextStartSit();
                return ByteBufferOperation.usedArray(temp);
            }
        }
        if (this.buffer.position() == 0) return null;
        this.buffer.limit(this.buffer.position());
        this.buffer.position(this.mark);
        return ByteBufferOperation.usedArray(this.buffer);
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setFindSeparatePoint(Function<ByteBuffer , BufferReader.SeparatePoint> findSeparatePoint) {
        this.findSeparatePoint = findSeparatePoint;
    }
}
