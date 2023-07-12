package com.github.andyshao.time;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.Pair;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
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
public final class LocalDateOperation {
    private LocalDateOperation() {}

    /**default convert*/
    public static final Convert<LocalDate , Date> DEFAULT_CONVERT_TO_DATE = ld -> Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

    /**
     * to date
     * @param zoneId {@link ZoneId}
     * @return to data {@link Convert}
     */
    public static final Convert<LocalDate, Date> toDate(ZoneId zoneId){
        return ld -> Date.from(ld.atStartOfDay(zoneId).toInstant());
    }

    /**
     * to data
     * @return to date {@link Convert}
     */
    public static final Convert<LocalDate, Date> toDate(){
        return DEFAULT_CONVERT_TO_DATE;
    }

    /**
     * to local date
     * @return to local date {@link Convert}
     */
    public static final Convert<Pair<DateTimeFormatter, String>, LocalDate> toLocalDate() {
    	return pair -> {
    		TemporalAccessor date = pair.getFirstOrDefault(DateTimeFormatter.ofPattern("yyyyMMdd"))
    				.parse(pair.getSecond());
    		return LocalDate.from(date);
    	};
    }
}
