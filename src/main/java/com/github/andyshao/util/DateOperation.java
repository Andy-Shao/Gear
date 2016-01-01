package com.github.andyshao.util;

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
public final class DateOperation {
    private DateOperation() {}

    public static final Convert<Date , LocalDateTime> DEFAULT_COVERT_TO_LOCAL_DATE_TIME = date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    public static final Convert<Date, LocalDate> DEFAULT_COVERT_TO_LOCAL_DATE = date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    public static final Convert<Date, LocalTime> DEFAULT_COVERT_TO_LOCAL_TIME = date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    
    public static final Convert<Date, LocalDateTime> toLocalDateTime(ZoneId zoneId){
        return d -> d.toInstant().atZone(zoneId).toLocalDateTime();
    }
    public static final Convert<Date, LocalDateTime> toLocalDateTime(){
        return DEFAULT_COVERT_TO_LOCAL_DATE_TIME;
    }
    
    public static final Convert<Date, LocalDate> toLocalDate(ZoneId zoneId){
        return d -> d.toInstant().atZone(zoneId).toLocalDate();
    }
    public static final Convert<Date, LocalDate> toLocalDate(){
        return DEFAULT_COVERT_TO_LOCAL_DATE;
    }
    
    public static final Convert<Date , LocalTime> toLocalTime(ZoneId zoneId){
        return d -> d.toInstant().atZone(zoneId).toLocalTime();
    }
    public static final Convert<Date, LocalTime> toLocalTime(){
        return DEFAULT_COVERT_TO_LOCAL_TIME;
    }
}
