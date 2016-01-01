package com.github.andyshao.time;

import java.time.LocalDate;
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
public final class LocalDateOperation {
    private LocalDateOperation() {}
    
    public static final Convert<LocalDate , Date> DEFAULT_CONVERT_TO_DATE = ld -> Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    
    public static final Convert<LocalDate, Date> toDate(ZoneId zoneId){
        return ld -> Date.from(ld.atStartOfDay(zoneId).toInstant());
    }
    public static final Convert<LocalDate, Date> toDate(){
        return DEFAULT_CONVERT_TO_DATE;
    }
}
