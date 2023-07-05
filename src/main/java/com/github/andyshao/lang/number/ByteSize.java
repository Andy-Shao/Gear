package com.github.andyshao.lang.number;

import com.github.andyshao.lang.Convert;

import java.io.Serializable;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) 21 Jun 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public interface ByteSize extends Convert<String , ByteSize>, Serializable{
    /**base size range*/
    int RANGE_SIZE = 1024;

    /**
     * set size
     * @param l size number
     * @param level {@link ByteLevel}
     */
    void setSize(long l, ByteLevel level);

    /**
     * get size
     * @param level {@link ByteLevel}
     * @return size nubmer
     */
    long getSize(ByteLevel level);

    /**
     * get size
     * @return size number
     */
    long getSize();

    /**
     * get {@link ByteLevel}
     * @return {@link ByteLevel}
     */
    ByteLevel getLevel();
}
