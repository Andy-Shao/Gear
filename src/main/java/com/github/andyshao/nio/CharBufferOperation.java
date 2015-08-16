package com.github.andyshao.nio;

import java.nio.CharBuffer;

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
public final class CharBufferOperation {
    public static char[] getChars(CharBuffer buffer , int start , int length) {
        final CharBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return CharBufferOperation.usedArray(tmp);
    }

    public static int indexOf(CharBuffer buffer , char... cs) {
        //TODO
        return -1;
    }

    public static int lastIndexOf(CharBuffer buffer , char... cs) {
        //TODO
        return -1;
    }

    public static char[] usedArray(CharBuffer buffer) {
        final char[] cs = new char[buffer.limit() - buffer.position()];
        buffer.get(cs);
        return cs;
    }

    private CharBufferOperation() {
        throw new AssertionError("No " + CharBufferOperation.class + " installment for you!");
    }
}
