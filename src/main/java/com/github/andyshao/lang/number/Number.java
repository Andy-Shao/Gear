package com.github.andyshao.lang.number;

import java.io.Serializable;

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
public interface Number extends Serializable {
    /**
     * get the radix
     * @return the radix number
     */
    int getRadix();

    /**
     * get the value as {@link String}
     * @return value
     */
    String getValueStr();

    /**
     * build value by {@link String}
     * @param valueStr value string
     * @return {@link Number}
     */
    Number instance(String valueStr);

    /**
     * is decimal
     * @return if it is then true
     */
    boolean isDecimal();

    /**
     * is integer
     * @return if it is then true
     */
    boolean isInteger();
}
