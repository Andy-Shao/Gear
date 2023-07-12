package com.github.andyshao.time;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.Pair;

import java.time.LocalDateTime;
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
public final class LocalDateTimeOperation {
    private LocalDateTimeOperation() {}
    /**default date convert*/
    public static final Convert<LocalDateTime , Date> DEFAULT_CONVERT_TO_DATE = ldt -> Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());

    /**
     * to date {@link Convert}
     * @param zoneId {@link ZoneId}
     * @return to date {@link Convert}
     */
    public static final Convert<LocalDateTime, Date> toDate(ZoneId zoneId) {
        return ldt -> Date.from(ldt.atZone(zoneId).toInstant());
    }

    /**
     * to date
     * @return to date {@link Convert}
     */
    public static final Convert<LocalDateTime, Date> toDate(){
        return DEFAULT_CONVERT_TO_DATE;
    }

    /**
     * to local date time {@link Convert}
     * @return to local date time {@link Convert}
     */
    public static final Convert<Pair<DateTimeFormatter, String>, LocalDateTime> toLocalDateTime() {
    	return pair -> {
    		TemporalAccessor date = pair.getFirstOrDefault(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"))
    				.parse(pair.getSecond());
    		return LocalDateTime.from(date);
    	};
    }
}
