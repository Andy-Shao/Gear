package com.github.andyshao.util.stream;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class StreamOperationTest {
	
	@Test
	void distinctByKey() {
		long distinctCount = Arrays.asList(1, 1, 3, 4, 5, 6, 5, 2)
			.stream()
			.filter(StreamOperation.distinctByKey(it -> it))
			.count();
		assertEquals(distinctCount, 6);
	}
}
