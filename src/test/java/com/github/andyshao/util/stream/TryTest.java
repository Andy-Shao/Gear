package com.github.andyshao.util.stream;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TryTest {
	private List<String> list;
	
	@BeforeEach
	public void before() {
		this.list = Arrays.asList("1", "2", "3");
	}

	@Test
	void funExp() {
		this.list.stream()
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
	
	@Test
	void predExp() {
		this.list.stream()
			.filter(Try.predExp(it -> doTest(it), error -> Boolean.FALSE))
			.collect(Collectors.toList());
	}

	static <T> T doSomething(T t) {
		if(new Random().nextBoolean()) return t;
		throw new RuntimeException();
	}
	
	static <T> boolean doTest(T t) throws IOException {
		if(new Random().nextBoolean()) return true;
		throw new IOException();
	}
}
