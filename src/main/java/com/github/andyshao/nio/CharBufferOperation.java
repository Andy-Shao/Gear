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
    /**
     * Get data by position
     * 
     * @param buffer the {@link CharBuffer} which is extracted data
     * @param start the start position of {@link CharBuffer}
     * @param length the length of {@link CharBuffer} which bellow for data
     * @return the data which you want to
     */
    public static char[] getChars(CharBuffer buffer , int start , int length) {
        final CharBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return CharBufferOperation.usedArray(tmp);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link CharBuffer} which you want to find from
     * @param cs the data which you want to find for
     * @return if it can't be found out then return -1
     * @see CharBufferOperation#indexOf(CharBuffer, int, int, char...)
     */
    public static int indexOf(CharBuffer buffer , char... cs) {
        if (cs.length == 0) throw new IllegalArgumentException("cs is empty");
        if (cs.length > buffer.limit() - buffer.position()) return -1;
        final CharBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = 0;
        int position = -1;
        LOOP: while (tmpByteBuffer.position() < tmpByteBuffer.limit()) {
            final char value = tmpByteBuffer.get();
            if (Character.compare(value , cs[index++]) == 0) {
                if (index == 1) position = tmpByteBuffer.position() - 1;
                if (index == cs.length) break LOOP;
            } else if (Character.compare(value , cs[0]) == 0) {
                index = 1;
                position = tmpByteBuffer.position() - 1;
                if (index == cs.length) break LOOP;
            } else {
                index = 0;
                position = -1;
            }
        }
        return position;
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from head to end.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link CharBuffer} which you want to find from
     * @param start the start position of {@link CharBuffer}
     * @param length the length of {@link CharBuffer} which should be
     *            searched
     * @param cs the data which you want to find for
     * @return if it can't be found out then return -1
     */
    public static int indexOf(CharBuffer buffer , int start , int length , char... cs) {
        CharBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return CharBufferOperation.indexOf(tmp , cs);
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from end to head.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link CharBuffer} which you want to find
     *            from
     * @param cs the data which you want to find for
     * @return if it can't be found out then return -1
     * @see CharBufferOperation#lastIndexOf(CharBuffer, int, int, char...)
     */
    public static int lastIndexOf(CharBuffer buffer , char... cs) {
        if (cs.length == 0) throw new IllegalArgumentException("cs is empty");
        if (cs.length > buffer.limit() - buffer.position()) return -1;
        final CharBuffer tmpByteBuffer = buffer.asReadOnlyBuffer();
        int index = cs.length;
        int position = -1;
        LOOP: for (int i = tmpByteBuffer.limit() - 1 ; i >= tmpByteBuffer.position() ; i--) {
            final char value = tmpByteBuffer.get(i);
            if (Character.compare(value , cs[--index]) == 0) {
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else if (Character.compare(value , cs[cs.length - 1]) == 0) {
                index = cs.length - 1;
                if (index == 0) {
                    position = i;
                    break LOOP;
                }
            } else {
                index = cs.length;
                position = -1;
            }
        }
        return position;
    }

    /**
     * Find the start point of data which you want to find out.
     * The searching will start from end to head.<br>
     * <b>NOTE: Only return the position of the first time find out the data</b>
     * 
     * @param buffer the {@link CharBuffer} which you want to find from
     * @param start the start position of {@link CharBuffer}
     * @param length the length of {@link CharBuffer} which should be searched
     * @param cs the data which you want to find for
     * @return if it can't be found out then return -1
     */
    public static int lastIndexOf(CharBuffer buffer , int start , int length , char... cs) {
        CharBuffer tmp = buffer.asReadOnlyBuffer();
        tmp.position(start);
        tmp.limit(start + length);
        return CharBufferOperation.lastIndexOf(tmp , cs);
    }

    /**
     * Only return a space which great than buffer.position() and less than
     * buffer.limit()<br>
     * <b>NOTE: It is will update the buffer</b>
     * 
     * @param buffer the buffer which has the info
     * @return the byte array and the info in it
     */
    public static char[] usedArray(CharBuffer buffer) {
        final char[] cs = new char[buffer.limit() - buffer.position()];
        buffer.get(cs);
        return cs;
    }

    private CharBufferOperation() {
        throw new AssertionError("No " + CharBufferOperation.class + " installment for you!");
    }
}
