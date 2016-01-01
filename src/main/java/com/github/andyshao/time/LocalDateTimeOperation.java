package com.github.andyshao.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.github.andyshao.lang.Convert;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jan 1, 2016<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public final class LocalDateTimeOperation {
    private LocalDateTimeOperation() {}
    
    public static final Convert<LocalDateTime , Date> DEFAULT_CONVERT_TO_DATE = ldt -> Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    
    public static final Convert<LocalDateTime, Date> toDate(ZoneId zoneId) {
        return ldt -> Date.from(ldt.atZone(zoneId).toInstant());
    }
    public static final Convert<LocalDateTime, Date> toDate(){
        return DEFAULT_CONVERT_TO_DATE;
    }
}
