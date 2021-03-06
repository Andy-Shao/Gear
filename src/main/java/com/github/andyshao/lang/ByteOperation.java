package com.github.andyshao.lang;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

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
    private static final BigInteger EIGHT = BigInteger.valueOf(8);
    public static final int UNCHAR_MAX = 0xff;
    
    public static final Comparator<Byte> comparator() {
    	return (l, r) -> {
    		if(Objects.isNull(l) && Objects.nonNull(r)) return -1;
    		else if(Objects.isNull(l) && Objects.isNull(r)) return 0;
    		else if(Objects.nonNull(l) && Objects.isNull(r)) return 1;
    		return Byte.compare(r, l);
    	};
    }

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
        dest[destStart + length_] = ByteOperation.fill(0 , (destPos + length) - ((destPos + length) >> 3 << 3) , 8 , dest[destStart + length_])[0];

        dest[destStart] |= headTemp;
        dest[destStart + length_] |= tailTemp;
    }

    public static final <ARRAY> int bitGet(BigInteger pos , final ARRAY array , ByteWrapper<ARRAY> byteWrapper) {
        if (pos.compareTo(BigInteger.ZERO) == -1) throw new IllegalArgumentException("pos less than 0");
        int value = 0x01 << pos.remainder(ByteOperation.EIGHT).intValue();
        return (byteWrapper.getByte(array , pos.divide(ByteOperation.EIGHT)) & value) != 0x00 ? 1 : 0;
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

    @SuppressWarnings("unchecked")
    public static final <ARRAY> ARRAY bitOxr(final ARRAY b1 , final ARRAY b2 , BigInteger size , ByteWrapper<ARRAY> byteWrapper) {
        final BigInteger answers[] = size.divideAndRemainder(ByteOperation.EIGHT);
        ARRAY result = (ARRAY) Array.newInstance(b1.getClass().getComponentType() , answers[1].intValue() == 0 ? answers[0].intValue() : answers[0].intValue() + 1);
        ByteOperation.fill(0 , BigInteger.ZERO , size , result , byteWrapper);
        for (BigInteger i = BigInteger.ZERO ; i.compareTo(size) == -1 ; i = i.add(BigInteger.ONE))
            if (ByteOperation.bitGet(i , b1 , byteWrapper) != ByteOperation.bitGet(i , b2 , byteWrapper)) ByteOperation.bitSet(i , 1 , result , byteWrapper);
            else ByteOperation.bitSet(i , 0 , result , byteWrapper);
        return result;
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

    public static final <ARRAY> ARRAY bitRotLeft(int count , final ARRAY array , ByteWrapper<ARRAY> byteWrapper) {
        final BigInteger length = byteWrapper.size(array);
        final BigInteger size = length.multiply(ByteOperation.EIGHT);
        if (size.compareTo(BigInteger.ZERO) == 1) for (int j = 0 ; j < count ; j++) {
            int fbit = 0;
            final BigInteger temp = length.subtract(BigInteger.ONE);
            for (BigInteger i = length.subtract(BigInteger.ONE) ; i.compareTo(BigInteger.ZERO) != -1 ; i = i.subtract(BigInteger.ONE)) {
                int lbit = ByteOperation.bitGet(7 , byteWrapper.getByte(array , i));
                if (i.compareTo(temp) == 0) fbit = lbit;
                else {
                    BigInteger temp2 = i.add(BigInteger.ONE);
                    byteWrapper.setByte(array , temp2 , ByteOperation.bitSet(0 , lbit , byteWrapper.getByte(array , temp2))[0]);
                }
                byteWrapper.setByte(array , i , (byte) (byteWrapper.getByte(array , i) << 1));
            }
            ByteOperation.bitSet(BigInteger.ZERO , fbit , array , byteWrapper);
        }
        return array;
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
            for (int i = b.length - 1 ; i >= 0 ; i--) {
                //Get the bit about to be shifted off the current byte.
                int lbit = ByteOperation.bitGet(7 , b[i]);
                //Save the bit shifted off the first byte for later.
                if (i == b.length - 1) fbit = lbit;
                /*
                 * Set the rightmost bit of the previous byte to the
                 * leftmost
                 * bit about to be shifted off the current byte.
                 */
                else b[i + 1] = ByteOperation.bitSet(0 , lbit , b[i + 1])[0];
                //Shift the current byte to the left.
                b[i] = (byte) (b[i] << 1);
            }
            //Set the rightmost bit of the buffer to the bit shifted off the first byte.
            ByteOperation.bitSet(0 , fbit , b);
        }
        return b;
    }

    public static final <ARRAY> ARRAY bitRotRight(int count , final ARRAY array , ByteWrapper<ARRAY> byteWrapper) {
        final BigInteger length = byteWrapper.size(array);
        final BigInteger size = length.multiply(ByteOperation.EIGHT);
        if (size.compareTo(BigInteger.ZERO) == 1) for (int j = 0 ; j < count ; j++) {
            int fbit = 0;
            for (BigInteger i = BigInteger.ZERO ; i.compareTo(length) == -1 ; i = i.add(BigInteger.ONE)) {
                int lbit = ByteOperation.bitGet(0 , byteWrapper.getByte(array , i));
                if (i.compareTo(BigInteger.ZERO) == 0) fbit = lbit;
                else {
                    BigInteger temp = i.subtract(BigInteger.ONE);
                    byteWrapper.setByte(array , temp , ByteOperation.bitSet(7 , lbit , byteWrapper.getByte(array , temp))[0]);
                }
                byteWrapper.setByte(array , i , (byte) (byteWrapper.getByte(array , i) >> 1));
            }
            ByteOperation.bitSet(size.subtract(BigInteger.ONE) , fbit , array , byteWrapper);
        }
        return array;
    }

    public static final byte[] bitRotRight(int count , final byte... bs) {
        int size = bs.length << 3;
        if (size > 0) for (int j = 0 ; j < count ; j++) {
            int fbit = 0;
            for (int i = 0 ; i < bs.length ; i++) {
                int lbit = ByteOperation.bitGet(0 , bs[i]);
                if (i == 0) fbit = lbit;
                else bs[i - 1] = ByteOperation.bitSet(7 , lbit , bs[i - 1])[0];
                bs[i] = (byte) (bs[i] >> 1);
            }
            ByteOperation.bitSet(size - 1 , fbit , bs);
        }
        return bs;
    }

    public static final <ARRAY> ARRAY bitSet(BigInteger pos , int state , final ARRAY array , ByteWrapper<ARRAY> byteWrapper) {
        if (state != 0 && state != 1) throw new IllegalArgumentException("state neighter 0 nor 1");
        if (pos.compareTo(BigInteger.ZERO) == -1) throw new IllegalArgumentException("pos less than 0");
        int value = 0x01 << pos.remainder(ByteOperation.EIGHT).intValue();
        BigInteger index = pos.divide(ByteOperation.EIGHT);
        if (state == 0) byteWrapper.setByte(array , index , (byte) (byteWrapper.getByte(array , index) & ~value));
        else byteWrapper.setByte(array , index , (byte) (byteWrapper.getByte(array , index) | value));
        return array;
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
        if (state != 0 && state != 1) throw new IllegalArgumentException("state neither 0 nor 1");
        if (pos < 0) throw new IllegalArgumentException("pos less than 0");
        int value = 0x01 << (pos - (pos >> 3 << 3));
        if (state == 0) b[pos >> 3] &= ~value;
        else b[pos >> 3] |= value;
        return b;
    }

    public static final <ARRAY> ARRAY fill(int state , BigInteger startPos , BigInteger endPos , final ARRAY array , ByteWrapper<ARRAY> byteWrapper) {
        if (state != 0 && state != 1) throw new IllegalArgumentException("state neither 0 or 1");
        if (startPos.compareTo(endPos) == 1) throw new IllegalArgumentException("startPos bigger than endPos");
        for (BigInteger i = startPos ; i.compareTo(endPos) == -1 ; i = i.add(BigInteger.ONE))
            ByteOperation.bitSet(i , state , array , byteWrapper);
        return array;
    }

    public static final byte[] fill(int state , int startPos , int endPos , final byte... bs) {
        if (state != 0 && state != 1) throw new IllegalArgumentException("state neither 0 or 1");
        if (startPos > endPos) throw new IllegalArgumentException("startPos bigger than endPos");

        for (int i = startPos ; i < endPos ; i++)
            ByteOperation.bitSet(i , state , bs);
        return bs;
    }

    public static final String toHexString(byte b) {
        return Convert.BYTE_2_STR.convert(b);
    }

    public static final String toHexString(byte... bs) {
        return Convert.BYTES_TO_HEX.convert(bs);
    }

    static final <ARRAY> String toString(ARRAY array , ByteWrapper<ARRAY> byteWrapper) {
        StringBuilder result = new StringBuilder();
        BigInteger length = byteWrapper.size(array);
        if (array == null || length.compareTo(BigInteger.ZERO) == 0) return "";
        for (BigInteger i = BigInteger.ZERO ; i.compareTo(length) == -1 ; i = i.add(BigInteger.ONE))
            result.append(ByteOperation.toString(byteWrapper.getByte(array , i))).append(",");
        return result.substring(0 , result.length() - 1);
    }

    public static final String toString(byte b) {
        StringBuilder result = new StringBuilder();
        for (int i = 7 ; i >= 0 ; i--)
            result.append(ByteOperation.bitGet(i , b));
        return result.toString();
    }

    public static final String toString(byte... bs) {
        StringBuilder result = new StringBuilder();
        if (bs == null || bs.length == 0) return "";
        for (byte b : bs)
            result.append(ByteOperation.toString(b)).append(",");
        return result.substring(0 , result.length() - 1);
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

    public static final byte[] unsigedRightShif(int times , byte... bs) {
        ByteOperation.bitRotRight(times , bs);
        for (int i = 0 ; i < times ; i++)
            ByteOperation.bitSet(i , 0 , bs);
        return bs;
    }

    public static final <ARRAY> ARRAY unsignedRigthShif(int times , ARRAY array , ByteWrapper<ARRAY> byteWrapper) {
        ByteOperation.bitRotRight(times , array , byteWrapper);
        for (int i = 0 ; i < times ; i++)
            ByteOperation.bitSet(BigInteger.valueOf(i) , 0 , array , byteWrapper);
        return array;
    }

    public static final byte unsingedRightShift(int times , byte b) {
        for (int i = 0 ; i < times ; i++) {
            b >>= b;
            b |= 0x7F;
        }
        return b;
    }

    private ByteOperation() {
        throw new AssertionError("No " + ByteOperation.class + " for you!");
    }
}
