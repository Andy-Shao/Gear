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
public enum ByteLevel {
    BYTE(0, "B"),KB(1, "KB"),MB(2, "MB"),GB(3, "GB"),TB(4, "TB");
    private int level;
    private String tag;
    private ByteLevel(int level, String tag) {
        this.level = level;
        this.tag = tag;
    }
    public int getLevel() {
        return this.level;
    }
    public String getTag() {
        return this.tag;
    }
    public static final ByteLevel min(ByteLevel one, ByteLevel two) {
        if(one == null && two != null) return two;
        if(one != null && two == null) return one;
        int oneLevel = one.getLevel();
        int twoLevel = two.getLevel();
        if(oneLevel > twoLevel) return two;
        else return one;
    }
    public static final ByteLevel max(ByteLevel one, ByteLevel two) {
        if(one == null && two != null) return two;
        if(one != null && two == null) return one;
        int oneLevel = one.getLevel();
        int twoLevel = two.getLevel();
        if(oneLevel > twoLevel) return one;
        else return two;
    }
}
