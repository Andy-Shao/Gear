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
    boolean isRepeat(String uniqueKey, ExpireMode mode, int times);
    boolean isRepeat(String uniqueKey);
    DistributionLock repeatCheckLock(String uniqueKey, ExpireMode mode, int times);
}
