package com.github.andyshao.lang.number;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Dec 20, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public interface Number {
    int getRadix();

    String getValueStr();

    boolean isDecimal();

    boolean isInteger();
}
