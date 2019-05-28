package com.github.andyshao.util.stream;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class StreamOperationTest {

	@Test
	void wrap() {
		List<String> list = Arrays.<String>asList("1", "2", "3")
			.stream()
			.map(StreamOperation.wrap(it -> doSomething(it)))
			.collect(Collectors.toList());
		assertNotNull(list);
		assertFalse(list.isEmpty());
		assertEquals(list.get(0), "1");
	}

	static <T> T doSomething(T t) throws IOException {
		return t;
	}
	
	@Test
	void distinctByKey() {
		long distinctCount = Arrays.asList(1, 1, 3, 4, 5, 6, 5, 2)
			.stream()
			.filter(StreamOperation.distinctByKey(it -> it))
			.count();
		assertEquals(distinctCount, 6);
	}
}
