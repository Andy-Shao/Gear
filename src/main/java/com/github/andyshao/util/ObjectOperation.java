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
    /**
     * function not null or default
     * @param t input
     * @param function {@link Function}
     * @param def default
     * @return function result or default value
     * @param <T> input type
     * @param <R> output type
     */
    public static final <T,R> R functionNonNullOrDefault(T t, Function<T, R> function, R def) {
        return functionNonNullOrElseGet(t, function, () -> def);
    }

    /**
     * function not null or else get
     * @param t input
     * @param function {@link Function}
     * @param supplier default factory
     * @return function result or default value
     * @param <T> input type
     * @param <R> output type
     */
    public static final <T,R> R functionNonNullOrElseGet(T t, Function<T, R> function, Supplier<R> supplier) {
        return functionAfterTestOrElseGet(t, it -> Objects.nonNull(t), function, supplier);
    }

    /**
     * function after test or else get
     * @param t input
     * @param predicate {@link Predicate}
     * @param function {@link Function}
     * @param supplier default factory
     * @return function result or default value
     * @param <T> input type
     * @param <R> output type
     */
    public static final <T,R> R functionAfterTestOrElseGet(
            T t, Predicate<T> predicate, Function<T, R> function, Supplier<R> supplier) {
        if(predicate.test(t)) return function.apply(t);
        return supplier.get();
    }

    /**
     * is same object
     * @param thiz object A
     * @param that object B
     * @return if it is then true
     */
	public static final boolean isSameObj(Object thiz, Object that) {
		return thiz == that;
	}

    /**
     * is pass equals and hashCode
     * @param thiz Object A
     * @param that Object B
     * @return if it is then true
     */
	public static final boolean isPassEqualsAndHashCode(Object thiz, Object that) {
		if(thiz == null || that == null) return false;
		return thiz.equals(that) && thiz.hashCode() == that.hashCode();
	}

    /**
     * value or default
     * @param value value
     * @param def default value
     * @return value or default
     * @param <T> data type
     */
    public static final <T> T valueOrDefault(T value , T def) {
        return Objects.isNull(value) ? def : value;
    }

    /**
     * equals any one return item
     * @param value value
     * @param compareList compare list
     * @return compare result
     */
    public static final Pair<Object, Boolean> equalsAnyOneRetItem(Object value, Object...compareList) {
    	if(value == null && compareList == null) return Pair.of(null, false);
        else if(value != null && compareList == null) return Pair.of(null, false);
    	
    	for(Object item : compareList) {
    		if(Objects.equals(value, item)) return Pair.of(item, true);
    	}
    	
    	return Pair.of(null, false);
    }

    /**
     * equals and one
     * @param value value
     * @param compareList compare list
     * @return if it is then true
     */
    public static final boolean equalsAnyOne(Object value, Object...compareList) {
        return equalsAnyOneRetItem(value, compareList).getSecond();
    }

    /**
     * equals and one return item
     * @param value value
     * @param it {@link Iterator}
     * @return compare answer
     */
    public static final Pair<Object, Boolean> equalsAnyOneRetItem(Object value, Iterator<Object> it) {
    	if(value == null && it == null) return Pair.of(null, false);
        else if(value != null && it == null) return Pair.of(null, false);
    	
    	while(it.hasNext()) {
    		Object item = it.next();
			if(Objects.equals(value, item)) return Pair.of(item, true);
    	}
    	
    	return Pair.of(null, false);
    }

    /**
     * equals any one
     * @param value value
     * @param it {@link Iterator}
     * @return equal any one
     */
    public static final boolean equalsAnyOne(Object value, Iterator<Object> it) {
        return equalsAnyOneRetItem(value, it).getSecond();
    }
    
    private ObjectOperation() {
        throw new AssertionError("No instance " + ObjectOperation.class + " for you");
    }
}
