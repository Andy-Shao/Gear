package com.github.andyshao.lock;

/**
 * 
 * Title: repeat checking<br>
 * Descript:<br>
 * Copyright: Copryright(c) 18 Apr 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public interface RepeatCheck {
    /**
     * is repeated
     * @param uniqueKey unique key
     * @param mode expired mode
     * @param times time value
     * @return if it can be repeated then true
     */
    boolean isRepeat(String uniqueKey, ExpireMode mode, int times);

    /**
     * is repeated
     * @param uniqueKey unique key
     * @return if it can be repeated then true
     */
    boolean isRepeat(String uniqueKey);
}
