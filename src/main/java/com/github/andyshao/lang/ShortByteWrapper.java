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
public class ShortByteWrapper implements ByteWrapper {
    public ShortByteWrapper(short[] s, int start, int end) {
    }
    
    public ShortByteWrapper(short[] s) {
        this(s, 0, s.length);
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
