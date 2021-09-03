package com.github.andyshao.util;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.function.FunctionOperation;
import com.github.andyshao.util.stream.Pair;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
	
	public static Stream<Byte> nextBytes(Random random, byte minimum, byte lessThan, long size) {
		return StreamSupport.stream(new RandomByteSpliterator(random, 0, size, minimum, lessThan), false);
	}
	
	public static Stream<Byte> nextBytes(Random random) {
		return nextBytes(random, Byte.MIN_VALUE, Byte.MAX_VALUE, Long.MAX_VALUE);
	}
	
	public static Stream<Byte> nextBytes() {
		return nextBytes(new Random());
	}
	
	public static byte nextByte(Random random) {
		byte[] bs = new byte[1];
		random.nextBytes(bs);
		return bs[0];
	}
	
	public static byte nextByte() {
		return nextByte(new Random());
	}
	
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
	
	public static short nextShort(Random random) {
		return nextShort(random, Short.MIN_VALUE, Short.MAX_VALUE);
	}
	
	public static short nextShort() {
		return nextShort(new Random());
	}
	
	public static Stream<Short> nextShorts(Random random, short minimum, short lessThan, long size) {
		return StreamSupport.stream(new RandomShortSpliterator(random, 0, size, minimum, lessThan), false);
	}
	
	public static Stream<Short> nextShorts(Random random) {
		return nextShorts(random, Short.MIN_VALUE, Short.MAX_VALUE, Long.MAX_VALUE);
	}
	
	public static Stream<Short> nextShorts() {
		return nextShorts(new Random());
	}
	
	public static char nextChar(Random random, char mininum, char lessThan) {
		return (char) nextInt(random, mininum, lessThan);
	}
	
	public static char nextChar(char minimum, char lessThan) {
		return nextChar(new Random(), minimum, lessThan);
	}
	
	public static char nextChar(Random random) {
		return nextChar(random, Character.MIN_VALUE, Character.MAX_VALUE);
	}
	
	public static char nextChar() {
		return nextChar(new Random());
	}
	
	public static Stream<Character> nextChars(Random random, char mininum, char lessThan, long size) {
		return StreamSupport.stream(new RandomCharSpliterator(random, 0, size, mininum, lessThan), false);
	}
	
	public static Stream<Character> nextChars(Random random) {
		return nextChars(random, Character.MIN_VALUE, Character.MAX_VALUE, Long.MAX_VALUE);
	}
	
	public static Stream<Character> nextChars() {
		return nextChars(new Random());
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
		return random.doubles(1, minimum, lessThan)
				.findFirst()
				.getAsDouble();
//		return (random.nextDouble() * lessThan) + minimum;
	}
	
	public static double nextDouble(double minimum, double lessThan) {
		return nextDouble(new Random(), minimum, lessThan);
	}
	
	public static float nextFloat(Random random, float minimum, float lessThan) {
		if(lessThan <= minimum) throw new IllegalArgumentException("lessThan must greater than minimum");
		float result = (random.nextFloat() * (lessThan - minimum)) + minimum;
		if(result >= lessThan) result = Math.nextDown(lessThan);
		return result;
//		return (random.nextFloat() * lessThan) + minimum;
	}
	
	public static float nextFloat(float minimum, float lessThan) {
		return nextFloat(new Random(), minimum, lessThan);
	}

	public static Stream<Float> nextFloats(Random random, float minimum, float maximum, long size) {
		if(maximum < minimum) throw new IllegalArgumentException("maximum less than minimum");
		return StreamSupport.stream(new RandomFloatSpliterator(random, 0, size, minimum, maximum), false);
	}

	public static Stream<Float> nextFloats(Random random) {
		return nextFloats(random, Float.MIN_VALUE, Float.MAX_VALUE, Long.MAX_VALUE);
	}
	
	public static boolean choicedByPercent(Random random, double percent) {
		if(percent < 0 || percent > 100) throw new IllegalArgumentException("percent is greater than 100 or less than 0");
		return (percent / 100.0) > random.nextDouble();
	}
	
	public static Optional<String> nextByPercent(Random random, Map<String, Double> percentages) {
		Convert<Map<String, Double>, Map<String, Function<String, Double>>> mapConvert = 
				MapOperation.<String, String, Double, Function<String, Double>>convertMap(input -> {
			return Optional.of(Pair.of(input.getFirst(), FunctionOperation.lambda(t -> input.getSecond())));
		});
		return nextByPercentFunction(random, mapConvert.convert(percentages));
	}
	
	public static Optional<String> nextByPercentFunction(
			Random random, Map<String, Function<String, Double>> percentages) {
		Double ttl = percentages.entrySet()
			.stream()
			.map(it -> it.getValue().apply(it.getKey()))
			.filter(it -> it > 0)
			.collect(Collectors.reducing((l, r) -> l + r))
			.orElse(-1.0);
		if(ttl == -1.0) throw new IllegalArgumentException("total number does not correct");
		double decision = nextDouble(0.0, ttl);
		double baseLine = 0;
		for(Map.Entry<String, Function<String, Double>> entry : percentages.entrySet()) {
			baseLine += entry.getValue().apply(entry.getKey());
			if(baseLine >= decision) return Optional.of(entry.getKey());
		}
		return Optional.empty();
	}

	static class RandomShortSpliterator extends RandomBaseSpliterator<Short> {

		protected RandomShortSpliterator(Random random, long index, long fence, Short minimum, Short maximum) {
			super(random, index, fence, minimum, maximum);
		}

		@Override
		public Spliterator<Short> trySplit() {
			long mid = mid(), ol = super.index;
			return (ol >= mid) ? null : 
				new RandomShortSpliterator(super.random, ol, super.index = mid, super.minimum, super.maximum);
		}

		@Override
		protected Short requireNext() {
			return RandomOperation.nextShort(super.random, super.minimum, super.maximum);
		}
		
	}
	
	static class RandomByteSpliterator extends RandomBaseSpliterator<Byte> {
		protected RandomByteSpliterator(Random random, long index, long fence, Byte minimum, Byte maximum) {
			super(random, index, fence, minimum, maximum);
		}

		@Override
		public Spliterator<Byte> trySplit() {
			long mid = mid(), ol = super.index;
			return (ol >= mid) ? null : 
				new RandomByteSpliterator(super.random, ol, super.index = mid, minimum, maximum);
		}

		@Override
		protected Byte requireNext() {
			return RandomOperation.nextByte(super.random, super.minimum, super.maximum);
		}
	}
	
	static class RandomCharSpliterator extends RandomBaseSpliterator<Character> {
		protected RandomCharSpliterator(Random random, long index, long fence, Character minimum, Character maximum) {
			super(random, index, fence, minimum, maximum);
		}

		@Override
		public Spliterator<Character> trySplit() {
			long mid = mid(), ol = super.index;
			return (ol >= mid) ? null : 
				new RandomCharSpliterator(super.random, ol, super.index = mid, super.minimum, super.maximum);
		}

		@Override
		protected Character requireNext() {
			return RandomOperation.nextChar(super.random, super.minimum, super.maximum);
		}
		
	}

	static class RandomFloatSpliterator extends RandomBaseSpliterator<Float> {

		protected RandomFloatSpliterator(Random random, long index, long fence, Float minimum, Float maximum) {
			super(random, index, fence, minimum, maximum);
		}

		@Override
		protected Float requireNext() {
			return RandomOperation.nextFloat(super.random, super.minimum, super.maximum);
		}

		@Override
		public Spliterator<Float> trySplit() {
			long mid = mid(), ol = super.index;
			return (ol >= mid) ? null : new RandomFloatSpliterator(super.random, ol, super.index = mid, super.minimum, super.maximum);
		}
	}

	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	static abstract class RandomBaseSpliterator<E> implements Spliterator<E> {
		protected final Random random;
		protected long index;
		protected long fence;
		protected final E minimum;
		protected final E maximum;
		
		@Override
		public boolean tryAdvance(Consumer<? super E> action) {
			Objects.requireNonNull(action);
			if(this.index++ < this.fence) {
				E item = requireNext();
				action.accept(item);
				return true;
			}
			return false;
		}
		
		protected abstract E requireNext();

		protected long mid() {
			return (this.index + this.fence) >>> 1;
		}
		
		@Override
		public long estimateSize() {
			return this.fence - this.index;
		}
		
		@Override
		public int characteristics() {
			return Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.NONNULL;
		}
	}
}
