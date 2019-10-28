package com.github.andyshao.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import org.junit.jupiter.api.Test;

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

}
