package com.github.andyshao.util;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Oct 28, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class RandomOperation {
	private RandomOperation() {}
	
	public static int nextInt(Random random, int mininum, int lessThan) {
		if(lessThan <= mininum) throw new IllegalArgumentException("lessThan must greater than minimum");
		int bound = lessThan - mininum;
		return random.nextInt(bound) + mininum;
	}
	
	public static int nextInt(int mininum, int lessThan) {
		return nextInt(new Random(), mininum, lessThan);
	}
	
	public static long nextLong(Random random, long minimum, long lessThan) {
		if(lessThan <= minimum) throw new IllegalArgumentException("lessThan must greater than minimum");
		if(lessThan <= (long) Integer.MAX_VALUE && lessThan >= (long) Integer.MIN_VALUE) 
			return nextInt(random, (int) minimum, (int) lessThan);
		return random.longs(1, minimum, lessThan)
				.findFirst()
				.getAsLong();
	}
	
	public static long nextLong(long minimum, long lessThan) {
		return nextLong(new Random(), minimum, lessThan);
	}
	
	public static double nextDouble(Random random, double minimum, double lessThan) {
		if(lessThan <= minimum) throw new IllegalArgumentException("lessThan must greater than minimum");
		return random.nextDouble() * (lessThan - minimum);
	}
	
	public static double nextDouble(double minimum, double lessThan) {
		return nextDouble(new Random(), minimum, lessThan);
	}
	
	public static boolean choicedByPercent(Random random, double percent) {
		if(percent < 0 || percent > 100) throw new IllegalArgumentException("percent is greater than 100 or less than 0");
		return (percent / 100.0) > random.nextDouble();
	}
	
	public static Optional<String> nextByPercent(Random random, Map<String, Double> percentages) {
		Double ttl = percentages.entrySet()
			.stream()
			.map(it -> it.getValue())
			.filter(it -> it > 0)
			.collect(Collectors.reducing((l, r) -> l + r))
			.orElse(-1.0);
		if(ttl == -1.0) throw new IllegalArgumentException("total number does not correct");
		double decision = nextDouble(0.0, ttl);
		double baseLine = 0;
		for(Map.Entry<String, Double> entry : percentages.entrySet()) {
			baseLine += entry.getValue();
			if(baseLine >= decision) return Optional.of(entry.getKey());
		}
		return Optional.empty();
	}
}
