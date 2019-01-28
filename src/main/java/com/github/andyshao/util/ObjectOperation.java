package com.github.andyshao.util;

import java.util.Iterator;
import java.util.Objects;

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
	public static final boolean isSameObj(Object thiz, Object that) {
		return thiz == that;
	}
	
	public static final boolean isBothPassEqualsAndHashCode(Object thiz, Object that) {
		if(thiz == null || that == null) return false;
		return thiz.equals(that) && thiz.hashCode() == that.hashCode();
	}
	
    public static final <T> T valueOrNull(T value , T nullDefault) {
        if (value == null) return nullDefault;
        else return value;
    }
    
    public static final boolean equalsAnyOne(Object value, Object...compareList) {
        if(value == null && compareList == null) return true;
        else if(value != null && compareList == null) return false;
        
        for(Object item : compareList) {
            if(Objects.equals(value , item)) return true;
        }
        
        return false;
    }
    
    public static final boolean equalsAnyOne(Object value, Iterator<Object> it) {
        if(value == null && it == null) return true;
        else if(value != null && it == null) return false;
        
        while(it.hasNext()) {
            if(Objects.equals(value , it.next())) return true;
        }
        
        return false;
    }
    
    private ObjectOperation() {
        throw new AssertionError("No instance " + ObjectOperation.class + " for you");
    }
}
