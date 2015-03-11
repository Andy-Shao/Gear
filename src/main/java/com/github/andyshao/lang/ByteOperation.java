package com.github.andyshao.lang;

import java.util.Arrays;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 10, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ByteOperation {
    public static final void bitCopy(byte[] src , int srcPos , byte[] dest , int destPos , int length) {
        int srcStart = srcPos >> 3;
        int destStart = destPos >> 3;
        int length_ = length - (length >> 3 << 3) != 0 ? (length >> 3) + 1 : length >> 3;
        byte headTemp = dest[destStart];
        byte tailTemp = dest[destStart + length_];

        for (int i = 7 ; i >= destPos - (destPos >> 3 << 3) ; i--)
            headTemp = ByteOperation.bitSet(i , 0 , headTemp)[0];
        for (int i = 0 ; i < (destPos + length) - ((destPos + length) >> 3 << 3) ; i++)
            tailTemp = ByteOperation.bitSet(i , 0 , tailTemp)[0];

        System.arraycopy(src , srcStart , dest , destStart , length_ + 1);

        for (int i = 0 ; i < destPos - (destPos >> 3 << 3) ; i++)
            dest[destStart] = ByteOperation.bitSet(i , 0 , dest[destStart])[0];
        for (int i = 7 ; i >= (destPos + length) - ((destPos + length) >> 3 << 3) ; i--)
            dest[destStart + length_] = ByteOperation.bitSet(i , 0 , dest[destStart + length_])[0];

        dest[destStart] |= headTemp;
        dest[destStart + length_] |= tailTemp;
    }

    /**
     * get the binary value.
     * 
     * @param pos position
     * @param b byte array
     * @return the value 1 or 0
     */
    public static final int bitGet(int pos , final byte... b) {
        if (pos < 0) throw new IllegalArgumentException();
        int value = 0x01 << (pos - (pos >> 3 << 3));
        return (b[pos >> 3] & value) != 0x00 ? 1 : 0;
    }

    /**
     * 
     * @param b1 byte array 1
     * @param b2 byte array 2
     * @param size the size of oxr operation bit
     * @return the byte array which has the answer.
     */
    public static final byte[] bitOxr(final byte[] b1 , final byte[] b2 , int size) {
        byte[] result = new byte[size - (size >> 3 << 3) == 0 ? size >> 3 : (size >> 3) + 1];
        Arrays.fill(result , (byte) 0x00);
        for (int i = 0 ; i < size ; i++)
            if (ByteOperation.bitGet(i , b1) != ByteOperation.bitGet(i , b2)) ByteOperation.bitSet(i , 1 , result);
            else ByteOperation.bitSet(i , 0 , result);
        return result;
    }

    /**
     * (带进位左移)
     * 
     * @param count 总移位次数
     * @param b byte array
     * @return byte array which has been operated
     */
    public static final byte[] bitRotLeft(int count , final byte... b) {
        int size = b.length << 3;
        //Rotate the buffer to the left the specified number of bist.
        if (size > 0) for (int j = 0 ; j < count ; j++) {
            int fbit = 0;
            for (int i = 0 ; i < b.length ; i++) {
                //Get the bit about to be shifted off the current byte.
                int lbit = ByteOperation.bitGet(7 , b[i]);

                //Save the bit shifted off the first byte for later.
                if (i == 0) fbit = lbit;
                /*
                 * Set the rightmost bit of the previous byte to the
                 * leftmost
                 * bit about to be shifted off the current byte.
                 */
                else b[i - 1] = ByteOperation.bitSet(0 , lbit , b[i - 1])[0];

                //Shift the current byte to the left.
                b[i] = (byte) (b[i] << 1);
            }

            //Set the rightmost bit of the buffer to the bit shifted off the first byte.
            ByteOperation.bitSet(size - 8 , fbit , b);
        }

        return b;
    }

    /**
     * set the binary value
     * 
     * @param pos position
     * @param state value
     * @param b byte array
     * @return byte array which has been operated.
     */
    public static final byte[] bitSet(int pos , int state , final byte... b) {
        if (state != 0 && state != 1) throw new IllegalArgumentException();
        if (pos < 0) throw new IllegalArgumentException();
        int value = 0x01 << (pos - (pos >> 3 << 3));
        if (state == 0) b[pos >> 3] &= ~value;
        else b[pos >> 3] |= value;
        return b;
    }

    public static final String toString(byte b) {
        String str = "";
        for (int i = 7 ; i >= 0 ; i--)
            str += ByteOperation.bitGet(i , b);
        return str;
    }

    public static final String toString(byte... bs) {
        String str = "";
        if (bs == null || bs.length == 0) return str;
        for (byte b : bs)
            str += ByteOperation.toString(b) + ",";
        return str.substring(0 , str.length() - 1);
    }

    private ByteOperation() {
        throw new AssertionError("No " + ByteOperation.class + " for you!");
    }
}
