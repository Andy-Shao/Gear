package com.github.andyshao.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.lang.StringOperation;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 26, 2018<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public final class MapOperation {
    private MapOperation() {
    }
    
    public static final <K,V> boolean isEmptyOrNull(Map<K, V> map) {
        return map == null || map.isEmpty();
    }
    
    public static final <K, V> Map<K, V> wrapMap(
    		Supplier<Map<K, V>> supplier, 
    		String valStr, 
    		Convert<String, K> keyConvert, 
    		Convert<String, V> valueConvert) {
    	final Map<K, V> result = supplier.get();
    	Arrays.stream(valStr.split(","))
    		.filter(it -> StringOperation.isEmptyOrNull(it))
    		.forEach(it -> {
    			String[] items = it.split(":");
    			switch(items.length) {
    			case 1:
    				result.put(keyConvert.convert(items[0]), null);
    				break;
    			case 2:
    				result.put(keyConvert.convert(items[0]), valueConvert.convert(items[1]));
    				break;
    		    default:
    		    	break;
    			}
    		});

		return result;
    }
    
    public static final <K, V> Map<K, V> wrapMap(
    		String valStr, 
    		Convert<String, K> keyConvert, 
    		Convert<String, V> valueConvert) {
    	return wrapMap(HashMap::new, valStr, keyConvert, valueConvert);
    }
    
    public static final Map<String, Object> wrapMap(String valStr) {
    	return wrapMap(valStr, in -> in.toString(), in -> in);
    }
    
    public static final Map<String, String> wrapStringMap(String valStr) {
    	return wrapMap(valStr, in -> in.toString(), in -> Objects.isNull(in) ? null : in.toString());
    }
}
