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
	
	public static byte nextByte(Random random, byte minimum, byte lessThan) {
		return (byte) nextInt(random, minimum, lessThan);
	}
	
	public static byte nextByte(byte minimum, byte lessThan) {
		return nextByte(new Random(), minimum, lessThan);
	}
	
	public static short nextShort(Random random, short minimum, short lessThan) {
		return (short) nextInt(random, minimum, lessThan);
	}
	
	public static short nextShort(short minimum, short lessThan) {
		return nextShort(new Random(), minimum, lessThan);
	}
	
	public static char nextChar(Random random, char mininum, char lessThan) {
		return (char) nextInt(random, mininum, lessThan);
	}
	
	public static char nextChar(char minimum, char lessThan) {
		return nextChar(new Random(), minimum, lessThan);
	}
	
	public static int nextInt(Random random, int minimum, int lessThan) {
		if(lessThan <= minimum) throw new IllegalArgumentException("lessThan must greater than minimum");
		int bound = lessThan - minimum;
		return random.nextInt(bound) + minimum;
	}
	
	public static int nextInt(int minimum, int lessThan) {
		return nextInt(new Random(), minimum, lessThan);
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
		return (random.nextDouble() * lessThan) + minimum;
	}
	
	public static double nextDouble(double minimum, double lessThan) {
		return nextDouble(new Random(), minimum, lessThan);
	}
	
	public static float nextFloat(Random random, float minimum, float lessThan) {
		if(lessThan <= minimum) throw new IllegalArgumentException("lessThan must greater than minimum");
		return (random.nextFloat() * lessThan) + minimum;
	}
	
	public static float nextFloat(float minimum, float lessThan) {
		return nextFloat(new Random(), minimum, lessThan);
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
