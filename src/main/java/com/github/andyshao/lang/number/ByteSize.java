package com.github.andyshao.lang.number;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) 21 Jun 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public interface ByteSize {
    int RANGE_SIZE = 1024;
    void setSize(long l, ByteLevel level);
    long getSize(ByteLevel level);
    long getSize();
}
