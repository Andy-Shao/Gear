package com.github.andyshao.util.stream;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.andyshao.lang.StringOperation;
import com.github.andyshao.util.ExceptionableComparator;
import com.github.andyshao.util.function.ExceptionableConsumer;

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
					assertThat(it.getSuccess()).isNotNull();
				}
				if(it.isFailure()) {
					assertThat(it.getFailure()).isNotNull();
				}
			});
	}
	
	@Test
	void predExp() {
		this.list.stream()
			.filter(Try.predExp(it -> doTest(it), error -> Boolean.FALSE))
			.collect(Collectors.toList());
	}
	
	@Test
	void consumExp() {
		this.list.stream()
			.filter(StreamOperation.distinctByKey(it -> it))
			.forEach(Try.consumExp(
					it -> {
						throw new Exception();
					}, 
					error -> {
						assertThat(error.isFailure()).isTrue();
						assertThat(error.getFailure()).isNotNull();
					}));
		
		try {
			this.list.stream()
				.filter(StreamOperation.distinctByKey(it -> it))
				.forEach(ExceptionableConsumer.toConsumer().convert(it -> {
					throw new IOException();
				}));
			fail();
		} catch (RuntimeException e) {
			Throwable cause = e.getCause();
			assertThat(cause).isNotNull();
			assertThat(cause).isInstanceOf(IOException.class);
		}
		
		try {
			this.list.parallelStream()
			.filter(StreamOperation.distinctByKey(it -> it))
			.forEach(ExceptionableConsumer.toConsumer().convert(it -> {
				throw new IOException();
			}));
			fail();
		} catch (RuntimeException e) {
			Throwable cause = e.getCause();
			assertThat(cause).isNotNull();
			assertThat(cause).isInstanceOfAny(IOException.class, RuntimeException.class);
		}
	}
	
	@Test
	void compExp() {
		this.list.stream()
			.sorted(Try.compExp((o1, o2) -> StringOperation.COMPARATOR.compare(o1, o2), error -> -1));
		this.list.stream()
			.sorted(ExceptionableComparator.<String>toComparator()
					.convert((o1, o2) -> StringOperation.COMPARATOR.compare(o1, o2)));
	}
	
	@Test
	void intFunExp() {
		this.list.stream()
			.mapToInt(Try.intFunExp(Integer::valueOf, error -> -1));
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
