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
public interface Injection {
    public static final String SEPARATOR = "#";

    public boolean isEntityFactory();

    /**
     * {class entity name}#{injection entity name} or
     * {injection entity name}
     * @return the injection info
     */
    public String[] name();

    public Class<?> type();
}
