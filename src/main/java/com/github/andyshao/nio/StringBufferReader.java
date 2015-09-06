package com.github.andyshao.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Function;

import com.github.andyshao.lang.GeneralSystemProperty;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jun 22, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class StringBufferReader implements BufferReader<String> {
    public static class SeparateByStr implements Function<String , BufferReader.SeparatePoint> {
        private final String key;

        public SeparateByStr(String key) {
            this.key = key;
        }

        @Override
        public com.github.andyshao.nio.BufferReader.SeparatePoint apply(String t) {
            int index = t.indexOf(this.key);
            return new BufferReader.SeparatePoint(index , this.key.length() + index);
        }

    }

    private final ByteBuffer byteBuffer;
    private final ReadableByteChannel channel;
    private String encoding = GeneralSystemProperty.FILE_ENCODING.value();
    private Function<String , BufferReader.SeparatePoint> findSeparatePoint = new StringBufferReader.SeparateByStr(
        GeneralSystemProperty.LINE_SEPARATOR.value());
    private volatile boolean hasNext = true;

    private String temp = "";

    public StringBufferReader(ReadableByteChannel channel) {
        this(channel , 1024);
    }

    public StringBufferReader(ReadableByteChannel channel , int bufferSize) {
        this.channel = channel;
        this.byteBuffer = ByteBuffer.allocate(bufferSize);
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }

    public String getEncoding() {
        return this.encoding;
    }

    @Override
    public boolean hasNext() {
        return this.hasNext;
    }

    @Override
    public String read() throws IOException {
        if (this.temp.length() == 0) {
            if (this.channel.read(this.byteBuffer) == -1) {
                this.hasNext = false;
                return null;
            }
            this.byteBuffer.flip();
            this.temp = new String(ByteBufferOperation.usedArray(this.byteBuffer) , this.encoding);
            this.byteBuffer.clear();
        }

        LOOP: while (true) {
            BufferReader.SeparatePoint point = this.findSeparatePoint.apply(this.temp);
            if (point.getSeparatePoint() == -1) {
                if (this.channel.read(this.byteBuffer) == -1) {
                    this.hasNext = false;
                    return this.temp;
                }
                this.byteBuffer.flip();
                this.temp = new String(ByteBufferOperation.usedArray(this.byteBuffer) , this.encoding);
                this.byteBuffer.clear();
                continue LOOP;
            } else {
                String result = this.temp.substring(0 , point.getSeparatePoint());
                this.temp = this.temp.substring(point.getNextStartSit());
                return result;
            }
        }
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setFindSeparatePoint(Function<String , BufferReader.SeparatePoint> findSeparatePoint) {
        this.findSeparatePoint = findSeparatePoint;
    }

}
