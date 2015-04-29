package com.github.andyshao.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Function;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 29, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ReadableByteChannelOperations {
    public static final ReadableByteChannel endWith(
        ReadableByteChannel channel , Function<ByteBuffer , Boolean> function) {
        //TODO
        return null;
    }

    public static final ReadableByteChannel endWith(ReadableByteChannel channel , int index) throws IOException {
        //TODO
        return null;
    }

    public static final ReadableByteChannel endWithNext(ReadableByteChannel channel , byte... bs) throws IOException {
        //TODO
        return null;
    }

    public static final ReadableByteChannel fixLength(ReadableByteChannel channel , int size) throws IOException {
        //TODO
        return null;
    }

    public static final ReadableByteChannel startWith(
        ReadableByteChannel channel , Function<ByteBuffer , Boolean> function) {
        //TODO
        return null;
    }

    public static final ReadableByteChannel startWith(ReadableByteChannel channel , int index) throws IOException {
        //TODO
        return null;
    }

    public static final ReadableByteChannel startWithNext(ReadableByteChannel channel , byte... bs) throws IOException {
        //TODO
        return null;
    }

    private ReadableByteChannelOperations() {
        throw new AssertionError("No " + ReadableByteChannelOperations.class + " instance for you!");
    }
}
