package com.github.andyshao.lang;

import java.math.BigInteger;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.andyshao.reflect.ArrayOperation;

public class ByteOperationTest {
    private volatile byte b;
    private volatile byte[] bs;
    private final ByteWrapper<byte[]> byteWrapper = new ByteByteWrapper();

    @Before
    public void before() {
        this.b = (byte) 0xa5;
        this.bs = ArrayOperation.pack_unpack(new int[] {
            0x00 , 0x01 , 0x02 , 0x04 , 0x08 , 0x10 , 0x20 , 0x40 , 0x80 , 0xff , 0xa5
        } , byte[].class , (input) -> (byte) ((int) input));
    }

    @Test
    public void testBitCopy() {
        byte[] bs = new byte[] {
            (byte) 0B11111111 , (byte) 0B11111111 , (byte) 0B11111111 , (byte) 0B11111111
        };
        ByteOperation.bitCopy(this.bs , 0 , bs , 6 , 7);
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B11100001));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B11111111));

        ByteOperation.bitCopy(this.bs , 0 , bs , 6 , 14);
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00000001));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11110010));
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B11111111));
    }

    @Test
    public void testBitOxr() {
        byte[] bs;
        byte[] bs1 = new byte[] {
            0x00 , (byte) 0xa5 , (byte) 0xf0
        };
        byte[] bs2 = new byte[] {
            (byte) 0xff , (byte) 0xaa , 0x00
        };

        int size = 0;
        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs , Matchers.is(new byte[] {}));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(1));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00000001));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(1));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00000011));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(1));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00000111));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(1));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00001111));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(1));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00011111));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(1));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00111111));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(1));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B01111111));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(1));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(2));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00000001));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(2));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00000011));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(2));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00000111));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(2));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(2));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(2));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(2));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(2));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(3));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00000000));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(3));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00000000));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(3));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00000000));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(3));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00000000));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(3));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00010000));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(3));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00110000));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(3));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B01110000));

        bs = ByteOperation.bitOxr(bs1 , bs2 , size++);
        Assert.assertThat(bs.length , Matchers.is(3));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11110000));
    }

    @Test
    public void testBitRotLeft() {
        byte[] bs = new byte[] {
            (byte) 0B11111111 , (byte) 0B11110000 , 0B00001111 , 0B00010000
        };
        int count = 1;

        ByteOperation.bitRotLeft(0 , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B00010000));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B11110000));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));

        ByteOperation.bitRotLeft(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B00100000));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00011111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B11100001));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111110));

        ByteOperation.bitRotLeft(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B01000000));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B11000011));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111100));

        ByteOperation.bitRotLeft(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B10000000));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B01111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B10000111));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111000));

        ByteOperation.bitRotLeft(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B00000000));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11110001));

        ByteOperation.bitRotLeft(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B00000001));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11111110));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00011111));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11100010));

        ByteOperation.bitRotLeft(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B00000011));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11111100));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00111111));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11000100));

        ByteOperation.bitRotLeft(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B00000111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11111000));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B01111111));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B10001000));

        ByteOperation.bitRotLeft(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11110000));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00010000));
    }

    @Test
    public void testBitRotRight() {
        byte[] bs = new byte[] {
            (byte) 0B11111111 , (byte) 0B11110000 , 0B00001111 , 0B00010000
        };
        int count = 1;

        ByteOperation.bitRotRight(0 , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B00010000));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B11110000));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));

        ByteOperation.bitRotRight(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B10001000));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00000111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B11111000));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B01111111));

        ByteOperation.bitRotRight(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B11000100));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00000011));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B11111100));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00111111));

        ByteOperation.bitRotRight(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B11100010));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00000001));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B11111110));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00011111));

        ByteOperation.bitRotRight(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B11110001));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00000000));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00001111));

        ByteOperation.bitRotRight(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B11111000));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B10000000));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B01111111));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B10000111));

        ByteOperation.bitRotRight(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B11111100));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B01000000));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00111111));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11000011));

        ByteOperation.bitRotRight(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B11111110));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00100000));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00011111));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11100001));

        ByteOperation.bitRotRight(count , bs);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00010000));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11110000));
        
        ByteOperation.bitRotRight(0 , bs , byteWrapper);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B00010000));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11110000));
        
        ByteOperation.bitRotRight(count , bs , byteWrapper);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B01111111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B10001000));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00000111));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111000));
        
        ByteOperation.bitRotRight(count , bs , byteWrapper);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B00111111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11000100));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00000011));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111100));
        
        ByteOperation.bitRotRight(count , bs , byteWrapper);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B00011111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11100010));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00000001));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111110));
        
        ByteOperation.bitRotRight(count , bs , byteWrapper);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11110001));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00000000));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B11111111));
        
        ByteOperation.bitRotRight(count , bs , byteWrapper);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B10000111));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11111000));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B10000000));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B01111111));
        
        ByteOperation.bitRotRight(count , bs , byteWrapper);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B11000011));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11111100));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B01000000));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00111111));
        
        ByteOperation.bitRotRight(count , bs , byteWrapper);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B11100001));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11111110));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00100000));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00011111));
        
        ByteOperation.bitRotRight(count , bs , byteWrapper);
        Assert.assertThat(bs[3] , Matchers.is((byte) 0B11110000));
        Assert.assertThat(bs[2] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(bs[1] , Matchers.is((byte) 0B00010000));
        Assert.assertThat(bs[0] , Matchers.is((byte) 0B00001111));
    }

    @Test
    public void testFill() {
        ByteOperation.fill(1 , BigInteger.ZERO , BigInteger.ONE , this.bs , this.byteWrapper);
        Assert.assertThat(this.bs[0] , Matchers.is((byte) 0B00000001));
        ByteOperation.fill(1 , BigInteger.ZERO , BigInteger.valueOf(2) , this.bs , this.byteWrapper);
        Assert.assertThat(this.bs[0] , Matchers.is((byte) 0B00000011));
        ByteOperation.fill(1 , BigInteger.ZERO , BigInteger.valueOf(3) , this.bs , this.byteWrapper);
        Assert.assertThat(this.bs[0] , Matchers.is((byte) 0B00000111));
        ByteOperation.fill(1 , BigInteger.ZERO , BigInteger.valueOf(4) , this.bs , this.byteWrapper);
        Assert.assertThat(this.bs[0] , Matchers.is((byte) 0B00001111));
        ByteOperation.fill(0 , BigInteger.ZERO , BigInteger.valueOf(4) , this.bs , this.byteWrapper);
        Assert.assertThat(this.bs[0] , Matchers.is((byte) 0B00000000));
    }

    @Test
    public void testGet() {
        int position = 8;
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(1));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));

        position = 0;
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(1));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(1));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(1));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(1));

        BigInteger pos = BigInteger.valueOf(8);
        Assert.assertThat(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , Matchers.is(1));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , Matchers.is(0));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , Matchers.is(0));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , Matchers.is(0));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , Matchers.is(0));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , Matchers.is(0));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , Matchers.is(0));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitGet(pos , this.bs , this.byteWrapper) , Matchers.is(0));
    }

    @Test
    public void testSet() {
        int position = 0;
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10100101));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10100111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10100101));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10101101));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10110101));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10100101));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B11100101));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10100101));

        position = 0;
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B00000001));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B00000011));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B00000111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B00011111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B00111111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B01111111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B11111111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , Matchers.is((byte) 0B00000001));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , Matchers.is((byte) 0B00000011));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , Matchers.is((byte) 0B00000111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , Matchers.is((byte) 0B00011111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , Matchers.is((byte) 0B00111111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , Matchers.is((byte) 0B01111111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[1] , Matchers.is((byte) 0B11111111));

        BigInteger pos = BigInteger.ZERO;
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] ,
            Matchers.is((byte) 0B11111110));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] ,
            Matchers.is((byte) 0B11111100));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] ,
            Matchers.is((byte) 0B11111000));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] ,
            Matchers.is((byte) 0B11110000));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] ,
            Matchers.is((byte) 0B11100000));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] ,
            Matchers.is((byte) 0B11000000));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] ,
            Matchers.is((byte) 0B10000000));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[0] ,
            Matchers.is((byte) 0B00000000));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] ,
            Matchers.is((byte) 0B11111110));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] ,
            Matchers.is((byte) 0B11111100));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] ,
            Matchers.is((byte) 0B11111000));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] ,
            Matchers.is((byte) 0B11110000));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] ,
            Matchers.is((byte) 0B11100000));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] ,
            Matchers.is((byte) 0B11000000));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] ,
            Matchers.is((byte) 0B10000000));
        pos = pos.add(BigInteger.ONE);
        Assert.assertThat(ByteOperation.bitSet(pos , 0 , this.bs , this.byteWrapper)[1] ,
            Matchers.is((byte) 0B00000000));
    }

    @Test
    public void testToString() {
        Assert.assertThat(ByteOperation.toString(this.b) , Matchers.is("10100101"));
        Assert.assertThat(ByteOperation.toString(this.bs) , Matchers.is("00000000,00000001,00000010,00000100,00001000,"
            + "00010000,00100000,01000000,10000000,11111111,10100101"));
        Assert.assertThat(ByteOperation.toString(this.bs , this.byteWrapper) , Matchers
            .is("00000000,00000001,00000010,00000100,00001000,"
                + "00010000,00100000,01000000,10000000,11111111,10100101"));
    }
}
