package com.github.andyshao.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.Pair;

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
    
    public static final Convert<Pair<DateTimeFormatter, String>, LocalTime> toLocalTime() {
    	return pair -> {
    		TemporalAccessor date = pair.getFirstOrDefault(DateTimeFormatter.ofPattern("HH:mm:ss"))
    			.parse(pair.getSecond());
    		return LocalTime.from(date);
    	};
    }
}
