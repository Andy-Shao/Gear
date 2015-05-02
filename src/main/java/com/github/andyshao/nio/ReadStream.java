package com.github.andyshao.nio;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 30, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 */
@FunctionalInterface
public interface ReadStream {
    public static ReadStream stream(ReadableByteChannel channel) {
        return (ByteBuffer buffer) -> channel.read(buffer);
    }

    default ReadStream clip(BigInteger start , BigInteger end) throws IOException {
        //TODO
        return null;
    }

    default ReadStream clip(int start , int end) throws IOException {
        if (start > end) throw new IllegalArgumentException("start bigger than end");
        return new ReadStream() {
            private int position = 0;

            @Override
            public int read(ByteBuffer buffer) throws IOException {
                if (this.position < start) return 0;
                if (this.position > end) return -1;
                final int readedSize = ReadStream.this.read(buffer);
                if (readedSize == -1) return readedSize;
                final int pre_position = this.position;
                this.position += readedSize;
                if (this.position < end) return this.position;
                else {
                    ByteBufferOperation.reform(buffer , this.position - end);
                    return end - pre_position;
                }
            }
        };
    }

    default ReadStream endWithNext(byte... bs) throws IOException {
        return (ByteBuffer buffer) -> {
            final int readedSize = ReadStream.this.read(buffer);
            final int indexOf = ByteBufferOperation.indexOf(buffer , bs);
            if (indexOf == -1) return readedSize;
            else return -1;
        };
    }

    default ReadStream fixLength(BigInteger size) throws IOException {
        return this.clip(BigInteger.ZERO , size);
    }

    default ReadStream fixLength(int size) throws IOException {
        return this.clip(0 , size);
    }

    default ReadStream process(Predicate<ByteBuffer> predicate , ToIntBiFunction<Integer , ByteBuffer> function)
        throws IOException {
        return (ByteBuffer buffer) -> {
            if (!predicate.test(buffer)) return function.applyAsInt(ReadStream.this.read(buffer) , buffer);
            return -1;
        };
    }

    int read(ByteBuffer buffer) throws IOException;

    default ReadStream startWithNext(byte... bs) throws IOException {
        return (ByteBuffer buffer) -> {
            final int readedSize = ReadStream.this.read(buffer);
            final int indexOf = ByteBufferOperation.indexOf(buffer , bs);
            if (indexOf == -1) return 0;
            else return readedSize;
        };
    }
}
