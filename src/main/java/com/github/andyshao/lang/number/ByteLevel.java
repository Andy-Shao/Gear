package com.github.andyshao.lang.number;

import java.util.Objects;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) 21 Jun 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public enum ByteLevel {
    /**byte*/
    BYTE(0, "B"),
    /**kilobyte*/
    KB(1, "KB"),
    /**megabyte*/
    MB(2, "MB"),
    /**gigabyte*/
    GB(3, "GB"),
    /**terabyte*/
    TB(4, "TB"),
    /**petabyte*/
    PB(5, "PB"),
    /**exabyte*/
    EB(6, "EB")
    ;
    private int level;
    private String tag;
    private ByteLevel(int level, String tag) {
        this.level = level;
        this.tag = tag;
    }

    /**
     * get the level
     * @return level value
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * get the tag
     * @return tag value
     */
    public String getTag() {
        return this.tag;
    }

    /**
     * get the minimum {@link ByteLevel}
     * @param one {@link ByteLevel}
     * @param two {@link ByteLevel}
     * @return the minimum {@link ByteLevel}
     */
    public static final ByteLevel min(ByteLevel one, ByteLevel two) {
        if(one == null && two != null) return two;
        if(one != null && two == null) return one;
        int oneLevel = one.getLevel();
        int twoLevel = two.getLevel();
        if(oneLevel > twoLevel) return two;
        else return one;
    }

    /**
     * get the maximum {@link ByteLevel}
     * @param one {@link ByteLevel}
     * @param two {@link ByteLevel}
     * @return the maximum {@link ByteLevel}
     */
    public static final ByteLevel max(ByteLevel one, ByteLevel two) {
        if(one == null && two != null) return two;
        if(one != null && two == null) return one;
        int oneLevel = one.getLevel();
        int twoLevel = two.getLevel();
        if(oneLevel > twoLevel) return one;
        else return two;
    }

    /**
     * find {@link ByteLevel} by tag
     * @param tag tag value
     * @return {@link ByteLevel}
     */
    public static final ByteLevel parse(String tag) {
        for(ByteLevel level : values()) {
            if(Objects.equals(level.getTag() , tag)) return level;
        }
        return null;
    }
}
