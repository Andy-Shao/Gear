package com.github.andyshao.util;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.lang.StringOperation;
import com.github.andyshao.util.stream.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

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

	/**
	 * of operation
	 * @param map {@link Map}
	 * @param key key
	 * @param value value
	 * @return origin {@link Map}
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static Map of(Map map, Object key, Object value){
		map.put(key, value);
		return map;
	}

	/**
	 * is empty or null
	 * @param map {@link Map}
	 * @return if it is then true
	 * @param <K> key type
	 * @param <V> value type
	 */
    public static final <K,V> boolean isEmptyOrNull(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

	/**
	 * convert map
	 * @param convert key &amp; value {@link Convert}
	 * @param suplier {@link Map} factory
	 * @return the {@link Map}
	 * @param <K> origin key type
	 * @param <K2> new key type
	 * @param <V> origin value type
	 * @param <V2> new value type
	 * @param <R> new {@link Map} type
	 */
    public static final <K, K2, V, V2, R extends Map<K2, V2>> Convert<Map<K, V>, R> convertMap(
    		Convert<Pair<K, V>, Optional<Pair<K2, V2>>> convert, 
    		Supplier<R> suplier) {
    	return origin -> {
    		R ret = suplier.get();
    		origin.forEach((k, v) -> {
    			Optional<Pair<K2, V2>> converted = convert.convert(Pair.of(k, v));
    			if(converted.isPresent()) {
    				Pair<K2, V2> pair = converted.get();
    				ret.put(pair.getFirst(), pair.getSecond());
    			}
    		});
			return ret;
    	};
    }

	/**
	 * convert map
	 * @param convert key &amp; value {@link Convert}
	 * @return new {@link Map}
	 * @param <K> origin key type
	 * @param <K2> new key type
	 * @param <V> origin value type
	 * @param <V2> new value type
	 */
    public static final <K, K2, V, V2> Convert<Map<K, V>, Map<K2, V2>> convertMap(
    		Convert<Pair<K, V>, Optional<Pair<K2, V2>>> convert) {
    	return convertMap(convert, HashMap::new);
    }

	/**
	 * convert {@link ConcurrentMap}
	 * @param convert key &amp; value {@link Convert}
	 * @return new {@link Map}
	 * @param <K> origin key type
	 * @param <K2> new key type
	 * @param <V> origin value type
	 * @param <V2> new value type
	 */
    public static final <K, K2, V, V2> Convert<Map<K, V>, ConcurrentMap<K2, V2>> convertConcurrentMap(
    		Convert<Pair<K, V>, Optional<Pair<K2, V2>>> convert) {
    	return convertMap(convert, ConcurrentHashMap::new);
    }

	/**
	 * wrap {@link Map}
	 * @param supplier {@link Map} factory
	 * @param valStr value string
	 * @param convert key &amp; value {@link Convert}
	 * @return a new {@link Map}
	 * @param <K> new key type
	 * @param <V> origin value type
	 * @param <R> new {@link Map} type
	 */
    public static final <K, V, R extends Map<K, V>> R wrapMap(
    		Supplier<R> supplier, 
    		String valStr, 
    		Convert<Pair<String, String>, Optional<Pair<K, V>>> convert) {
    	final R result = supplier.get();
    	Arrays.stream(valStr.split(","))
    		.filter(it -> !StringOperation.isEmptyOrNull(it))
    		.forEach(it -> {
    			String[] items = it.split(":");
    			Optional<Pair<K, V>> op = Optional.empty();
    			switch(items.length) {
    			case 1:
    				op = convert.convert(Pair.of(items[0].trim(), null));
    				break;
    			case 2:
    				op = convert.convert(Pair.of(items[0].trim(), items[1].trim()));
    				break;
    		    default:
    		    	break;
    			}
    			if(op.isPresent()) {
    				Pair<K, V> pair = op.get();
    				result.put(pair.getFirst(), pair.getSecond());
    			}
    		});

		return result;
    }

	/**
	 * wrap {@link Map}
	 * @param valStr value string
	 * @param convert key &amp; value {@link Convert}
	 * @return new {@link Map}
	 * @param <K> key type
	 * @param <V> value type
	 */
    public static final <K, V> Map<K, V> wrapMap(
    		String valStr, 
    		Convert<Pair<String, String>, Optional<Pair<K, V>>> convert) {
    	return wrapMap(HashMap::new, valStr, convert);
    }

	/**
	 * wrap {@link ConcurrentMap}
	 * @param valStr value string
	 * @param convert key &amp; value {@link Convert}
	 * @return new {@link ConcurrentMap}
	 * @param <K> key type
	 * @param <V> value type
	 */
    public static final <K, V> ConcurrentMap<K, V> wrapConcurrentMap(
    		String valStr, 
    		Convert<Pair<String, String>, Optional<Pair<K, V>>> convert) {
    	return wrapMap(ConcurrentHashMap::new, valStr, convert);
    }

	/**
	 * wrap {@link Map}
	 * @param valStr value string
	 * @return {@link Map}
	 */
	public static final Map<String, Object> wrapMap(String valStr) {
    	return wrapMap(valStr, input -> Optional.of(Pair.of(input.getFirst(), input.getSecond())));
    }

	/**
	 * wrap string map
	 * @param valStr value string
	 * @return {@link Map}
	 */
	public static final Map<String, String> wrapStringMap(String valStr) {
    	return wrapMap(valStr, input -> Optional.of(Pair.of(input.getFirst(), input.getSecond())));
    }
}
