package com.github.andyshao.util.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EitherTest {

	@Test
	void lift() {
		Arrays.asList("1", "2", "3")
			.stream()
			.map(Either.funExp(item -> dosomething(item)))
			.forEach(either -> {
				if(either.isLeft()) {
					Optional<Throwable> left = either.getLeft();
					assertTrue(left.isPresent());
					assertTrue(left.get() instanceof RuntimeException);
				} else {
					Optional<String> right = either.getRight();
					assertTrue(right.isPresent());
				}
			});
	}

	static <T> T dosomething(T t) {
		Random random = new Random();
		if(random.nextBoolean()) return t;
		else throw new RuntimeException();
	}
}
