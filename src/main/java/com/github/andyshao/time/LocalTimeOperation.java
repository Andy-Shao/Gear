package com.github.andyshao.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class LocalTimeOperation {
    private LocalTimeOperation() {}
    
    public static final Convert<LocalTime , Date> DEFAULT_CONVERT_TO_DATE = lt -> LocalDateTimeOperation.toDate().convert(LocalDateTime.of(LocalDate.now() , lt));
    
    public static final Convert<LocalTime, Date> toDate(ZoneId zoneId){
        return lt -> LocalDateTimeOperation.toDate(zoneId).convert(LocalDateTime.of(LocalDate.now() , lt));
    }
    public static final Convert<LocalTime, Date> toDate(){
        return DEFAULT_CONVERT_TO_DATE;
    }
}
