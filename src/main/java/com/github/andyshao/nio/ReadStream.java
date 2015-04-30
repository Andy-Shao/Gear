package com.github.andyshao.nio;

import java.io.IOException;
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

    default ReadStream endWith(int index) throws IOException {
        //TODO
        return null;
    }

    default ReadStream endWith(Predicate<ByteBuffer> predicate , ToIntBiFunction<Integer , ByteBuffer> function)
        throws IOException {
        return (ByteBuffer buffer) -> {
            if (!predicate.test(buffer)) return function.applyAsInt(ReadStream.this.read(buffer) , buffer);
            return -1;
        };
    }

    default ReadStream endWithNext(byte... bs) throws IOException {
        //TODO
        return null;
    }

    default ReadStream fixLength(int size) throws IOException {
        //TODO
        return null;
    }

    int read(ByteBuffer buffer) throws IOException;

    default ReadStream startWith(int index) throws IOException {
        //TODO
        return null;
    }

    default ReadStream startWith(Predicate<ByteBuffer> predicate , ToIntBiFunction<Integer , ByteBuffer> function)
        throws IOException {
        return (ByteBuffer buffer) -> {
            if (predicate.test(buffer)) return function.applyAsInt(ReadStream.this.read(buffer) , buffer);
            return 0;
        };
    }

    default ReadStream startWithNext(byte... bs) throws IOException {
        //TODO
        return null;
    }
}
