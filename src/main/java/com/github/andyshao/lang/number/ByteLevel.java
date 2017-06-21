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
    BYTE(0),KB(1),MB(2),GB(3),TB(4);
    private int level;
    private ByteLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return this.level;
    }
}
