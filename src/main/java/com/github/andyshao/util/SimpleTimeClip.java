package com.github.andyshao.util;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) 21 Jun 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public class SimpleTimeClip implements TimeClip{
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    private BigDecimal nu = BigDecimal.ZERO;

    @Override
    public void setClip(long l , TimeUnit timeUnit) {
        if(l < 0) throw new IllegalArgumentException("l is less than 0");
        BigDecimal nu = new BigDecimal(l);
        this.setClip(nu , timeUnit);
    }

    @Override
    public void setClip(BigDecimal nu , TimeUnit timeUnit) {
        this.nu = nu;
        this.timeUnit = timeUnit;
    }

    @Override
    public long getClip(TimeUnit timeUnit) {
        long l = this.getClipFroBigDecimal(timeUnit).longValue();
        if(l < 0) throw new TimeClipException("time clip is over long store range");
        return l;
    }

    @Override
    public BigDecimal getClipFroBigDecimal(TimeUnit timeUnit) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String description(TimeUnit timeUnit) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.nu.toString());
        boolean lessThanOne = this.nu.compareTo(BigDecimal.ONE) <= 1;
        switch (timeUnit) {
        case DAYS:
            if(lessThanOne) builder.append(" day");
            else builder.append(" days");
            break;
        case HOURS:
            if(lessThanOne) builder.append(" hour");
            else builder.append(" hours");
            break;
        case MINUTES:
            if(lessThanOne) builder.append(" minute");
            else builder.append(" minutes");
            break;
        case SECONDS:
            if(lessThanOne) builder.append(" second");
            else builder.append(" seconds");
            break;
        case MILLISECONDS:
            if(lessThanOne) builder.append(" millisecond");
            else builder.append(" milliseconds");
            break;
        case MICROSECONDS:
            if(lessThanOne) builder.append(" microsecond");
            else builder.append(" microseconds");
            break;
        case NANOSECONDS:
            if(lessThanOne) builder.append(" nanosecond");
            else builder.append(" nanoseconds");
            break;
        default:
            break;
        }
        return builder.toString();
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

    @Override
    public String description() {
        return this.description(this.timeUnit);
    }
}
