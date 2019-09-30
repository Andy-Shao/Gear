package com.github.andyshao.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class MapOperationTest {

	@Test
	void wrapMap() {
		Map<String, Integer> map = MapOperation.wrapMap(
				HashMap::new, "age:29, height:168", in -> in.toString(), in -> Integer.valueOf(in));
		assertNotNull(map);
		assertEquals(map.size(), 2);
		assertEquals(map.get("age").intValue(), 29);
		assertEquals(map.get("height").intValue(), 168);
	}

}
