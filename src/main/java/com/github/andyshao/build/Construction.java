package com.github.andyshao.build;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 11, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public interface Construction {
    public static final String DEFALUT_WORDS = "#";

    /**
     * {class entity name}#{injection entity name}#{parameter number} or
     * {injection entity name}#{parameter number}
     * 
     * @return the construction info
     */
    public String[] name();
}