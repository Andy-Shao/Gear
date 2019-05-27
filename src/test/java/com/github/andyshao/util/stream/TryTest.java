package com.github.andyshao.util.stream;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;

class TryTest {

	@Test
	void funExp() {
		Arrays.asList("1", "2", "3")
			.stream()
			.map(Try.funExp(it -> doSomething(it)))
			.forEach(it -> {
				if(it.isSuccess()) {
					assertNotNull(it.getSuccess());
				}
				if(it.isFailure()) {
					assertNotNull(it.getFailure());
				}
			});
	}

	static <T> T doSomething(T t) {
		if(new Random().nextBoolean()) return t;
		throw new RuntimeException();
	}
}
