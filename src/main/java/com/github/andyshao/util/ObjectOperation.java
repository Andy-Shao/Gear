package com.github.andyshao.util;

import com.github.andyshao.util.stream.Pair;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jan 26, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ObjectOperation {
    public static final <T,R> R functionNonNullOrDefault(T t, Function<T, R> function, R def) {
        return functionNonNullOrElseGet(t, function, () -> def);
    }

    public static final <T,R> R functionNonNullOrElseGet(T t, Function<T, R> function, Supplier<R> supplier) {
        return functionAfterTestOrElseGet(t, it -> Objects.nonNull(t), function, supplier);
    }

    public static final <T,R> R functionAfterTestOrElseGet(
            T t, Predicate<T> predicate, Function<T, R> function, Supplier<R> supplier) {
        if(predicate.test(t)) return function.apply(t);
        return supplier.get();
    }

	public static final boolean isSameObj(Object thiz, Object that) {
		return thiz == that;
	}
	
	public static final boolean isPassEqualsAndHashCode(Object thiz, Object that) {
		if(thiz == null || that == null) return false;
		return thiz.equals(that) && thiz.hashCode() == that.hashCode();
	}
	
    public static final <T> T valueOrDefault(T value , T def) {
        return Objects.isNull(value) ? def : value;
    }
    
    @Deprecated
    public static final <T> T valueOrNull(T value , T nullDefault) {
        if (value == null) return nullDefault;
        else return value;
    }
    
    public static final Pair<Object, Boolean> equalsAnyOneRetItem(Object value, Object...compareList) {
    	if(value == null && compareList == null) return Pair.of(null, false);
        else if(value != null && compareList == null) return Pair.of(null, false);
    	
    	for(Object item : compareList) {
    		if(Objects.equals(value, item)) return Pair.of(item, true);
    	}
    	
    	return Pair.of(null, false);
    }
    
    public static final boolean equalsAnyOne(Object value, Object...compareList) {
        return equalsAnyOneRetItem(value, compareList).getSecond();
    }
    
    public static final Pair<Object, Boolean> equalsAnyOneRetItem(Object value, Iterator<Object> it) {
    	if(value == null && it == null) return Pair.of(null, false);
        else if(value != null && it == null) return Pair.of(null, false);
    	
    	while(it.hasNext()) {
    		Object item = it.next();
			if(Objects.equals(value, item)) return Pair.of(item, true);
    	}
    	
    	return Pair.of(null, false);
    }
    
    public static final boolean equalsAnyOne(Object value, Iterator<Object> it) {
        return equalsAnyOneRetItem(value, it).getSecond();
    }
    
    private ObjectOperation() {
        throw new AssertionError("No instance " + ObjectOperation.class + " for you");
    }
}
