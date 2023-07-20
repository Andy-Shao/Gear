package com.github.andyshao.util;

import com.github.andyshao.lang.Convert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

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

    /**default convert to local date time*/
    public static final Convert<Date , LocalDateTime> DEFAULT_COVERT_TO_LOCAL_DATE_TIME = date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    /**default convert to local date*/
    public static final Convert<Date, LocalDate> DEFAULT_COVERT_TO_LOCAL_DATE = date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    /**default convert to local time*/
    public static final Convert<Date, LocalTime> DEFAULT_COVERT_TO_LOCAL_TIME = date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

    /**
     * to {@link LocalDateTime}
     * @param zoneId {@link ZoneId}
     * @return {@link Convert}
     */
    public static final Convert<Date, LocalDateTime> toLocalDateTime(ZoneId zoneId){
        return d -> d.toInstant().atZone(zoneId).toLocalDateTime();
    }

    /**
     * to {@link LocalDateTime}
     * @return {@link Convert}
     */
    public static final Convert<Date, LocalDateTime> toLocalDateTime(){
        return DEFAULT_COVERT_TO_LOCAL_DATE_TIME;
    }

    /**
     * to {@link LocalDate}
     * @param zoneId {@link ZoneId}
     * @return {@link Convert}
     */
    public static final Convert<Date, LocalDate> toLocalDate(ZoneId zoneId){
        return d -> d.toInstant().atZone(zoneId).toLocalDate();
    }

    /**
     * to {@link LocalDate}
     * @return {@link Convert}
     */
    public static final Convert<Date, LocalDate> toLocalDate(){
        return DEFAULT_COVERT_TO_LOCAL_DATE;
    }

    /**
     * to {@link LocalTime}
     * @param zoneId {@link ZoneId}
     * @return {@link Convert}
     */
    public static final Convert<Date , LocalTime> toLocalTime(ZoneId zoneId){
        return d -> d.toInstant().atZone(zoneId).toLocalTime();
    }

    /**
     * to {@link LocalTime}
     * @return {@link Convert}
     */
    public static final Convert<Date, LocalTime> toLocalTime(){
        return DEFAULT_COVERT_TO_LOCAL_TIME;
    }
}
