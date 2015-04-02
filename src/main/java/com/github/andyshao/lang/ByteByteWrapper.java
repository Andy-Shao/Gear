package com.github.andyshao.lang;

import java.math.BigInteger;
import java.util.Iterator;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 3, 2015<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public class ByteByteWrapper implements ByteWrapper {
    
    public ByteByteWrapper(byte[] bs, int start, int end) {
    }
    public ByteByteWrapper(byte[] bs) {
        this(bs, 0, bs.length);
    }

    @Override
    public Iterator<Byte> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public byte getByte(BigInteger index) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setByte(BigInteger index , byte b) {
        // TODO Auto-generated method stub

    }

    @Override
    public BigInteger size() {
        // TODO Auto-generated method stub
        return null;
    }

}
