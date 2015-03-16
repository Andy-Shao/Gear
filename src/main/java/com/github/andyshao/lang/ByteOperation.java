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
    public static final int UNCHAR_MAX = 0xff;

    public static final void bitCopy(byte[] src , int srcPos , byte[] dest , int destPos , int length) {
        int srcStart = srcPos >> 3;
        int destStart = destPos >> 3;
        int length_ = length - (length >> 3 << 3) != 0 ? (length >> 3) + 1 : length >> 3;
        byte headTemp = dest[destStart];
        byte tailTemp = dest[destStart + length_];

        headTemp = ByteOperation.fill(0 , destPos - (destPos >> 3 << 3) , 8 , headTemp)[0];
        tailTemp = ByteOperation.fill(0 , 0 , (destPos + length) - ((destPos + length) >> 3 << 3) , tailTemp)[0];

        System.arraycopy(src , srcStart , dest , destStart , length_ + 1);

        dest[destStart] = ByteOperation.fill(0 , 0 , destPos - (destPos >> 3 << 3) , dest[destStart])[0];
        dest[destStart + length_] =
            ByteOperation.fill(0 , (destPos + length) - ((destPos + length) >> 3 << 3) , 8 , dest[destStart + length_])[0];

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
        if (pos < 0) throw new IllegalArgumentException("pos is less than 0");
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

    public static final byte[] bitRotRight(int count , final byte... bs) {
        int size = bs.length << 3;
        if (size > 0) for (int j = 0 ; j < count ; j++) {
            int fbit = 0;
            for (int i = 0 ; i < bs.length ; i++) {
                int lbit = ByteOperation.bitGet(0 , bs[i]);
                if (i == 0) fbit = lbit;
                else bs[i - 1] = ByteOperation.bitSet(7 , lbit , bs[i - 1])[0];
                bs[i] = (byte) (bs[i] << 1);
            }
            ByteOperation.bitSet(7 , fbit , bs);
        }

        return bs;
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

    public static final byte[] fill(int state , int startPos , int endPos , byte... bs) {
        if (state != 0 && state != 1) throw new IllegalArgumentException();
        if (startPos >= endPos) throw new IllegalArgumentException();

        for (int i = startPos ; i < endPos ; i++)
            ByteOperation.bitSet(i , state , bs);
        return bs;
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

    public static final int toUnsignedInt(byte unsignedByte) {
        return 0x000000ff & unsignedByte;
    }

    public static final long toUnsignedLong(byte unsignedByte) {
        return 0x00000000000000ffL & unsignedByte;
    }

    public static final short toUnsignedShort(byte unsignedByte) {
        return (short) (0x00ff & unsignedByte);
    }

    private ByteOperation() {
        throw new AssertionError("No " + ByteOperation.class + " for you!");
    }
}
