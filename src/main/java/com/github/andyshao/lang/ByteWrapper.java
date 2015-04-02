package com.github.andyshao.lang;

import java.math.BigInteger;

/**
 * Wrap any kinds of object or array. Operate them as a byte array.
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 2, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public interface ByteWrapper extends Iterable<Byte> {

    public byte getByte(BigInteger index);

    public void setByte(BigInteger index , byte b);

    public BigInteger size();
}
