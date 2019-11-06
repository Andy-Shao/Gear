package com.github.andyshao.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.andyshao.util.stream.Pair;

class RandomOperationTest {
	private int times = 99999;

	@Test
	void nextInt() {
		Random random = new Random();
		long timeStamps = System.currentTimeMillis();
		for(int i=0; i<times; i++) {
			RandomOperation.nextInt(random, 15, 5000);
		}
		timeStamps = System.currentTimeMillis() - timeStamps;
		System.out.println("nextInt(int, int) spending " + calculateTime(timeStamps, times).toString());
	}
	
	@Test
	void nextLong() {
		Random random = new Random();
		long timeStamps = System.currentTimeMillis();
		for(int i=0; i<times; i++) {
			RandomOperation.nextLong(random, Long.MAX_VALUE - 500, Long.MAX_VALUE);
		}
		timeStamps = System.currentTimeMillis() - timeStamps;
		System.out.println("nextLong(long, long) spending " + calculateTime(timeStamps, times).toString());
	}
	
	@Test
	void nextDouble() {
		Random random = new Random();
		long timeStamps = System.currentTimeMillis();
		for(int i=0; i<times; i++) {
			RandomOperation.nextDouble(random, 0.0, 100.0);
		}
		timeStamps = System.currentTimeMillis() - timeStamps;
		System.out.println("nextDouble(double, double) spending " + calculateTime(timeStamps, times).toString());
	}

	BigDecimal calculateTime(long timeStamps, int times) {
		return new BigDecimal(timeStamps).divide(new BigDecimal(times), 4, RoundingMode.HALF_UP);
	}

	@Test
	void nextByPercent() {
		Random random = new Random();
		Map<String, Double> map = MapOperation.wrapMap("a:0, b:100", input -> {
			return Optional.of(Pair.of(input.getFirst(), Double.valueOf(input.getSecond())));
		});
		Optional<String> next = RandomOperation.nextByPercent(random, map);
		Assertions.assertThat(next.isPresent()).isTrue();
		Assertions.assertThat(next.get()).isEqualTo("b");
	}
	
	@Test
	void nextChars() {
		Optional<Character> c = RandomOperation.nextChars(new Random(47))
			.filter(it -> it > 'c')
			.findFirst();
		c.ifPresent(it -> {
			Assertions.assertThat(it).isEqualTo('繜');
		});
	}
}
