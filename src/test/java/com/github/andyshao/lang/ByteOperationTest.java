package com.github.andyshao.lang;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.andyshao.reflect.ArrayOperation;

public class ByteOperationTest {
    private volatile byte b;
    private volatile byte[] bs;
    private final ByteWrapper<byte[]> byteWrapper = new ByteByteWrapper();

    @BeforeEach
    public void before() {
        this.b = (byte) 0xa5;
        this.bs = ArrayOperation.pack_unpack(new int[] { 0x00 , 0x01 , 0x02 , 0x04 , 0x08 , 0x10 , 0x20 , 0x40 , 0x80 , 0xff , 0xa5 } , byte[].class , (input) -> (byte) ((int) input));
    }

    @Test
    public void testBitCopy() {
        byte[] bs = new byte[] { (byte) 0B11111111 , (byte) 0B11111111 , (byte) 0B11111111 , (byte) 0B11111111 };
        ByteOperation.bitCopy(this.bs , 0 , bs , 6 , 7);
        assertEquals(bs[0] , (byte) 0B00111111);
        assertEquals(bs[1] , (byte) 0B11100001);
        assertEquals(bs[2] , (byte) 0B11111111);
        assertEquals(bs[3] , (byte) 0B11111111);

        ByteOperation.bitCopy(this.bs , 0 , bs , 6 , 14);
        assertEquals(bs[0] , (byte) 0B00111111);
        assertEquals(bs[1] , (byte) 0B00000001);
        assertEquals(bs[2] , (byte) 0B11110010);
        assertEquals(bs[3] , (byte) 0B11111111);
    }

    @Test
    public void testBitOxr() {
        byte[] bs;
        byte[] bs1 = new byte[] { 0x00 , (byte) 0xa5 , (byte) 0xf0 };
        byte[] bs2 = new byte[] { (byte) 0xff , (byte) 0xaa , 0x00 };

        int size = 0;
        BigInteger size2 = BigInteger.ZERO;
        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertArrayEquals(bs , new byte[] {});
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertArrayEquals(bs , new byte[] {});

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B00000001);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B00000001);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B00000011);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B00000011);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B00000111);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B00000111);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B00001111);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B00001111);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B00011111);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B00011111);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B00111111);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B00111111);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B01111111);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B01111111);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B11111111);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 1);
        assertEquals(bs[0] , (byte) 0B11111111);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00000001);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00000001);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00000011);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00000011);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00000111);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00000111);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 2);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B00000000);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B00000000);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B00000000);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B00000000);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B00000000);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B00000000);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B00000000);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B00000000);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B00010000);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B00010000);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B00110000);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B00110000);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B01110000);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B01110000);

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B11110000);
        size2 = size2.add(BigInteger.ONE);
        bs = ByteOperation.bitOxr(bs1 , bs2 , size2 , this.byteWrapper);
        assertEquals(bs.length , 3);
        assertEquals(bs[0] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B11110000);
    }

    @Test
    public void testBitRotLeft() {
        byte[] bs = new byte[] { (byte) 0B11111111 , (byte) 0B11110000 , 0B00001111 , 0B00010000 };
        int count = 1;

        ByteOperation.bitRotLeft(0 , bs);
        assertEquals(bs[3] , (byte) 0B00010000);
        assertEquals(bs[2] , (byte) 0B00001111);
        assertEquals(bs[1] , (byte) 0B11110000);
        assertEquals(bs[0] , (byte) 0B11111111);

        ByteOperation.bitRotLeft(count , bs);
        assertEquals(bs[3] , (byte) 0B00100000);
        assertEquals(bs[2] , (byte) 0B00011111);
        assertEquals(bs[1] , (byte) 0B11100001);
        assertEquals(bs[0] , (byte) 0B11111110);

        ByteOperation.bitRotLeft(count , bs);
        assertEquals(bs[3] , (byte) 0B01000000);
        assertEquals(bs[2] , (byte) 0B00111111);
        assertEquals(bs[1] , (byte) 0B11000011);
        assertEquals(bs[0] , (byte) 0B11111100);

        ByteOperation.bitRotLeft(count , bs);
        assertEquals(bs[3] , (byte) 0B10000000);
        assertEquals(bs[2] , (byte) 0B01111111);
        assertEquals(bs[1] , (byte) 0B10000111);
        assertEquals(bs[0] , (byte) 0B11111000);

        ByteOperation.bitRotLeft(count , bs);
        assertEquals(bs[3] , (byte) 0B00000000);
        assertEquals(bs[2] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[0] , (byte) 0B11110001);

        ByteOperation.bitRotLeft(count , bs);
        assertEquals(bs[3] , (byte) 0B00000001);
        assertEquals(bs[2] , (byte) 0B11111110);
        assertEquals(bs[1] , (byte) 0B00011111);
        assertEquals(bs[0] , (byte) 0B11100010);

        ByteOperation.bitRotLeft(count , bs);
        assertEquals(bs[3] , (byte) 0B00000011);
        assertEquals(bs[2] , (byte) 0B11111100);
        assertEquals(bs[1] , (byte) 0B00111111);
        assertEquals(bs[0] , (byte) 0B11000100);

        ByteOperation.bitRotLeft(count , bs);
        assertEquals(bs[3] , (byte) 0B00000111);
        assertEquals(bs[2] , (byte) 0B11111000);
        assertEquals(bs[1] , (byte) 0B01111111);
        assertEquals(bs[0] , (byte) 0B10001000);

        ByteOperation.bitRotLeft(count , bs);
        assertEquals(bs[3] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B11110000);
        assertEquals(bs[1] , (byte) 0B11111111);
        assertEquals(bs[0] , (byte) 0B00010000);

        ByteOperation.bitRotLeft(0 , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B11110000);
        assertEquals(bs[1] , (byte) 0B11111111);
        assertEquals(bs[0] , (byte) 0B00010000);

        ByteOperation.bitRotLeft(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B00011111);
        assertEquals(bs[2] , (byte) 0B11100001);
        assertEquals(bs[1] , (byte) 0B11111110);
        assertEquals(bs[0] , (byte) 0B00100000);

        ByteOperation.bitRotLeft(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B00111111);
        assertEquals(bs[2] , (byte) 0B11000011);
        assertEquals(bs[1] , (byte) 0B11111100);
        assertEquals(bs[0] , (byte) 0B01000000);

        ByteOperation.bitRotLeft(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B01111111);
        assertEquals(bs[2] , (byte) 0B10000111);
        assertEquals(bs[1] , (byte) 0B11111000);
        assertEquals(bs[0] , (byte) 0B10000000);

        ByteOperation.bitRotLeft(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B11111111);
        assertEquals(bs[2] , (byte) 0B00001111);
        assertEquals(bs[1] , (byte) 0B11110001);
        assertEquals(bs[0] , (byte) 0B00000000);

        ByteOperation.bitRotLeft(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B11111110);
        assertEquals(bs[2] , (byte) 0B00011111);
        assertEquals(bs[1] , (byte) 0B11100010);
        assertEquals(bs[0] , (byte) 0B00000001);

        ByteOperation.bitRotLeft(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B11111100);
        assertEquals(bs[2] , (byte) 0B00111111);
        assertEquals(bs[1] , (byte) 0B11000100);
        assertEquals(bs[0] , (byte) 0B00000011);

        ByteOperation.bitRotLeft(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B11111000);
        assertEquals(bs[2] , (byte) 0B01111111);
        assertEquals(bs[1] , (byte) 0B10001000);
        assertEquals(bs[0] , (byte) 0B00000111);

        ByteOperation.bitRotLeft(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B11110000);
        assertEquals(bs[2] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00010000);
        assertEquals(bs[0] , (byte) 0B00001111);
    }

    @Test
    public void testBitRotRight() {
        byte[] bs = new byte[] { (byte) 0B11111111 , (byte) 0B11110000 , 0B00001111 , 0B00010000 };
        int count = 1;

        ByteOperation.bitRotRight(0 , bs);
        assertEquals(bs[3] , (byte) 0B00010000);
        assertEquals(bs[2] , (byte) 0B00001111);
        assertEquals(bs[1] , (byte) 0B11110000);
        assertEquals(bs[0] , (byte) 0B11111111);

        ByteOperation.bitRotRight(count , bs);
        assertEquals(bs[3] , (byte) 0B10001000);
        assertEquals(bs[2] , (byte) 0B00000111);
        assertEquals(bs[1] , (byte) 0B11111000);
        assertEquals(bs[0] , (byte) 0B01111111);

        ByteOperation.bitRotRight(count , bs);
        assertEquals(bs[3] , (byte) 0B11000100);
        assertEquals(bs[2] , (byte) 0B00000011);
        assertEquals(bs[1] , (byte) 0B11111100);
        assertEquals(bs[0] , (byte) 0B00111111);

        ByteOperation.bitRotRight(count , bs);
        assertEquals(bs[3] , (byte) 0B11100010);
        assertEquals(bs[2] , (byte) 0B00000001);
        assertEquals(bs[1] , (byte) 0B11111110);
        assertEquals(bs[0] , (byte) 0B00011111);

        ByteOperation.bitRotRight(count , bs);
        assertEquals(bs[3] , (byte) 0B11110001);
        assertEquals(bs[2] , (byte) 0B00000000);
        assertEquals(bs[1] , (byte) 0B11111111);
        assertEquals(bs[0] , (byte) 0B00001111);

        ByteOperation.bitRotRight(count , bs);
        assertEquals(bs[3] , (byte) 0B11111000);
        assertEquals(bs[2] , (byte) 0B10000000);
        assertEquals(bs[1] , (byte) 0B01111111);
        assertEquals(bs[0] , (byte) 0B10000111);

        ByteOperation.bitRotRight(count , bs);
        assertEquals(bs[3] , (byte) 0B11111100);
        assertEquals(bs[2] , (byte) 0B01000000);
        assertEquals(bs[1] , (byte) 0B00111111);
        assertEquals(bs[0] , (byte) 0B11000011);

        ByteOperation.bitRotRight(count , bs);
        assertEquals(bs[3] , (byte) 0B11111110);
        assertEquals(bs[2] , (byte) 0B00100000);
        assertEquals(bs[1] , (byte) 0B00011111);
        assertEquals(bs[0] , (byte) 0B11100001);

        ByteOperation.bitRotRight(count , bs);
        assertEquals(bs[3] , (byte) 0B11111111);
        assertEquals(bs[2] , (byte) 0B00010000);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[0] , (byte) 0B11110000);

        ByteOperation.bitRotRight(0 , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B11111111);
        assertEquals(bs[2] , (byte) 0B00010000);
        assertEquals(bs[1] , (byte) 0B00001111);
        assertEquals(bs[0] , (byte) 0B11110000);

        ByteOperation.bitRotRight(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B01111111);
        assertEquals(bs[2] , (byte) 0B10001000);
        assertEquals(bs[1] , (byte) 0B00000111);
        assertEquals(bs[0] , (byte) 0B11111000);

        ByteOperation.bitRotRight(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B00111111);
        assertEquals(bs[2] , (byte) 0B11000100);
        assertEquals(bs[1] , (byte) 0B00000011);
        assertEquals(bs[0] , (byte) 0B11111100);

        ByteOperation.bitRotRight(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B00011111);
        assertEquals(bs[2] , (byte) 0B11100010);
        assertEquals(bs[1] , (byte) 0B00000001);
        assertEquals(bs[0] , (byte) 0B11111110);

        ByteOperation.bitRotRight(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B00001111);
        assertEquals(bs[2] , (byte) 0B11110001);
        assertEquals(bs[1] , (byte) 0B00000000);
        assertEquals(bs[0] , (byte) 0B11111111);

        ByteOperation.bitRotRight(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B10000111);
        assertEquals(bs[2] , (byte) 0B11111000);
        assertEquals(bs[1] , (byte) 0B10000000);
        assertEquals(bs[0] , (byte) 0B01111111);

        ByteOperation.bitRotRight(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B11000011);
        assertEquals(bs[2] , (byte) 0B11111100);
        assertEquals(bs[1] , (byte) 0B01000000);
        assertEquals(bs[0] , (byte) 0B00111111);

        ByteOperation.bitRotRight(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B11100001);
        assertEquals(bs[2] , (byte) 0B11111110);
        assertEquals(bs[1] , (byte) 0B00100000);
        assertEquals(bs[0] , (byte) 0B00011111);

        ByteOperation.bitRotRight(count , bs , this.byteWrapper);
        assertEquals(bs[3] , (byte) 0B11110000);
        assertEquals(bs[2] , (byte) 0B11111111);
        assertEquals(bs[1] , (byte) 0B00010000);
        assertEquals(bs[0] , (byte) 0B00001111);
    }

    @Test
    public void testFill() {
        ByteOperation.fill(1 , BigInteger.ZERO , BigInteger.ONE , this.bs , this.byteWrapper);
        assertEquals(this.bs[0] , (byte) 0B00000001);
        ByteOperation.fill(1 , BigInteger.ZERO , BigInteger.valueOf(2) , this.bs , this.byteWrapper);
        assertEquals(this.bs[0] , (byte) 0B00000011);
        ByteOperation.fill(1 , BigInteger.ZERO , BigInteger.valueOf(3) , this.bs , this.byteWrapper);
        assertEquals(this.bs[0] , (byte) 0B00000111);
        ByteOperation.fill(1 , BigInteger.ZERO , BigInteger.valueOf(4) , this.bs , this.byteWrapper);
        assertEquals(this.bs[0] , (byte) 0B00001111);
        ByteOperation.fill(0 , BigInteger.ZERO , BigInteger.valueOf(4) , this.bs , this.byteWrapper);
        assertEquals(this.bs[0] , (byte) 0B00000000);
    }

    @Test
    public void testGet() {
        int position = 8;
        assertEquals(ByteOperation.bitGet(position++ , this.bs) , 1);
        assertEquals(ByteOperation.bitGet(position++ , this.bs) , 0);
        assertEquals(ByteOperation.bitGet(position++ , this.bs) , 0);
        assertEquals(ByteOperation.bitGet(position++ , this.bs) , 0);
        assertEquals(ByteOperation.bitGet(position++ , this.bs) , 0);
        assertEquals(ByteOperation.bitGet(position++ , this.bs) , 0);
        assertEquals(ByteOperation.bitGet(position++ , this.bs) , 0);
        assertEquals(ByteOperation.bitGet(position++ , this.bs) , 0);

        position = 0;
        assertEquals(ByteOperation.bitGet(position++ , this.b) , 1);
        assertEquals(ByteOperation.bitGet(position++ , this.b) , 0);
        assertEquals(ByteOperation.bitGet(position++ , this.b) , 1);
        assertEquals(ByteOperation.bitGet(position++ , this.b) , 0);
        assertEquals(ByteOperation.bitGet(position++ , this.b) , 0);
        assertEquals(ByteOperation.bitGet(position++ , this.b) , 1);
        assertEquals(ByteOperation.bitGet(position++ , this.b) , 0);
        assertEquals(ByteOperation.bitGet(position++ , this.b) , 1);

        BigInteger pos = BigInteger.valueOf(8);
        assertEquals(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , 1);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , 0);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , 0);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , 0);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , 0);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , 0);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , 0);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , 0);
    }

    @Test
    public void testSet() {
        int position = 0;
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.b)[0] , (byte) 0B10100101);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.b)[0] , (byte) 0B10100111);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.b)[0] , (byte) 0B10100101);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.b)[0] , (byte) 0B10101101);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.b)[0] , (byte) 0B10110101);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.b)[0] , (byte) 0B10100101);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.b)[0] , (byte) 0B11100101);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.b)[0] , (byte) 0B10100101);

        position = 0;
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , (byte) 0B00000001);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , (byte) 0B00000011);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , (byte) 0B00000111);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , (byte) 0B00001111);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , (byte) 0B00011111);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , (byte) 0B00111111);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , (byte) 0B01111111);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , (byte) 0B11111111);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , (byte) 0B00000001);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , (byte) 0B00000011);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , (byte) 0B00000111);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , (byte) 0B00001111);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , (byte) 0B00011111);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , (byte) 0B00111111);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , (byte) 0B01111111);
        assertEquals(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , (byte) 0B11111111);

        BigInteger pos = BigInteger.ZERO;
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] , (byte) 0B11111110);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] , (byte) 0B11111100);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] , (byte) 0B11111000);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] , (byte) 0B11110000);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] , (byte) 0B11100000);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] , (byte) 0B11000000);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] , (byte) 0B10000000);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] , (byte) 0B00000000);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] , (byte) 0B11111110);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] , (byte) 0B11111100);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] , (byte) 0B11111000);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] , (byte) 0B11110000);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] , (byte) 0B11100000);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] , (byte) 0B11000000);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] , (byte) 0B10000000);
        pos = pos.add(BigInteger.ONE);
        assertEquals(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] , (byte) 0B00000000);
    }

    @Test
    public void testToString() {
        assertEquals(ByteOperation.toString(this.b) , "10100101");
        assertEquals(ByteOperation.toString(this.bs) , "00000000,00000001,00000010,00000100,00001000," + "00010000,00100000,01000000,10000000,11111111,10100101");
        assertEquals(ByteOperation.toString(this.bs , this.byteWrapper) , "00000000,00000001,00000010,00000100,00001000," + "00010000,00100000,01000000,10000000,11111111,10100101");
    }
}
