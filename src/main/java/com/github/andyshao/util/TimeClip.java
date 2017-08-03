package com.github.andyshao.util;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Title:time clip<br>
 * Descript:<br>
 * Copyright: Copryright(c) 21 Jun 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public interface TimeClip {
    void setClip(long l, TimeUnit timeUnit);
    void setClip(BigDecimal nu, TimeUnit timeUnit);
    long getClip(TimeUnit timeUnit);
    BigDecimal getClipFroBigDecimal(TimeUnit timeUnit);
    String description(TimeUnit timeUnit);
    String description();
    String toString();
}
