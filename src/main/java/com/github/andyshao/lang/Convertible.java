package com.github.andyshao.lang;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Dec 20, 2016<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 * @param <OUT> output
 */
public interface Convertible<OUT> {
    /**
     * convert action
     * @return output
     */
    OUT convert();
}
