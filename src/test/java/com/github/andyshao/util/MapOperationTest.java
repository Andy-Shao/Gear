package com.github.andyshao.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.Pair;

class MapOperationTest {

	@Test
	void wrapMap() {
		Map<String, Integer> map = MapOperation.wrapMap(
				"age:29, height:168", 
				input -> {
					String key = input.getFirst();
					Integer value;
					try {
						value = Integer.valueOf(input.getSecond());
					} catch (NumberFormatException e) {
						return Optional.empty();
					}
					return Optional.of(Pair.of(key, value));
				});
		assertNotNull(map);
		assertEquals(map.size(), 2);
		assertEquals(map.get("age").intValue(), 29);
		assertEquals(map.get("height").intValue(), 168);
	}
	
	@Test
	void convertMap() {
		Map<String, Object> map = MapOperation.wrapMap("age:29, height:168");
		Convert<Map<String, Object>, Map<String, Integer>> convert = 
				MapOperation.<String, String, Object, Integer>convertMap(input -> {
			String key = input.getFirst();
			Object value = input.getSecond();
			try {
				Integer trueValue = Integer.valueOf(value.toString());
				return Optional.of(Pair.of(key, trueValue));
			} catch (NumberFormatException e) {
				return Optional.empty();
			}
		});
		Map<String, Integer> newMap = convert.convert(map);
		
		assertNotNull(newMap);
		assertEquals(newMap.size(), 2);
		assertEquals(newMap.get("age").intValue(), 29);
		assertEquals(newMap.get("height").intValue(), 168);
	}

}
