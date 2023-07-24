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

	/**
	 * next bytes
	 * @param random {@link Random}
	 * @param minimum minimum byte (inclusive)
	 * @param lessThan less than byte (exclusive)
	 * @param size size
	 * @return byte stream
	 */
	public static Stream<Byte> nextBytes(Random random, byte minimum, byte lessThan, long size) {
		return StreamSupport.stream(new RandomByteSpliterator(random, 0, size, minimum, lessThan), false);
	}

	/**
	 * next bytes
	 * @param random {@link Random}
	 * @return byte {@link Stream}
	 */
	public static Stream<Byte> nextBytes(Random random) {
		return nextBytes(random, Byte.MIN_VALUE, Byte.MAX_VALUE, Long.MAX_VALUE);
	}

	/**
	 * next byte
	 * @return byte {@link Stream}
	 */
	public static Stream<Byte> nextBytes() {
		return nextBytes(new Random());
	}

	/**
	 * next byte
	 * @param random {@link Random}
	 * @return {@link com.github.andyshao.lang.PrimitiveType#BYTE}
	 */
	public static byte nextByte(Random random) {
		byte[] bs = new byte[1];
		random.nextBytes(bs);
		return bs[0];
	}

	/**
	 * next byte
	 * @return {@link com.github.andyshao.lang.PrimitiveType#BYTE}
	 */
	public static byte nextByte() {
		return nextByte(new Random());
	}

	/**
	 * next byte
	 * @param random {@link Random}
	 * @param minimum minimum byte (inclusive)
	 * @param lessThan less than byte (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#BYTE}
	 */
	public static byte nextByte(Random random, byte minimum, byte lessThan) {
		return (byte) nextInt(random, minimum, lessThan);
	}

	/**
	 * next byte
	 * @param minimum minimum byte (inclusive)
	 * @param lessThan less than byte (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#BYTE}
	 */
	public static byte nextByte(byte minimum, byte lessThan) {
		return nextByte(new Random(), minimum, lessThan);
	}

	/**
	 * next short
	 * @param random {@link Random}
	 * @param minimum minimum short (inclusive)
	 * @param lessThan less than short (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#SHORT}
	 */
	public static short nextShort(Random random, short minimum, short lessThan) {
		return (short) nextInt(random, minimum, lessThan);
	}

	/**
	 * next short
	 * @param minimum minimum short (inclusive)
	 * @param lessThan less than short (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#SHORT}
	 */
	public static short nextShort(short minimum, short lessThan) {
		return nextShort(new Random(), minimum, lessThan);
	}

	/**
	 * next short
	 * @param random #{@link Random}
	 * @return {@link com.github.andyshao.lang.PrimitiveType#SHORT}
	 */
	public static short nextShort(Random random) {
		return nextShort(random, Short.MIN_VALUE, Short.MAX_VALUE);
	}

	/**
	 * next short
	 * @return {@link com.github.andyshao.lang.PrimitiveType#SHORT}
	 */
	public static short nextShort() {
		return nextShort(new Random());
	}

	/**
	 * next short
	 * @param random {@link Random}
	 * @param minimum minimum short (inclusive)
	 * @param lessThan less than short (exclusive)
	 * @param size size number
	 * @return short {@link Stream}
	 */
	public static Stream<Short> nextShorts(Random random, short minimum, short lessThan, long size) {
		return StreamSupport.stream(new RandomShortSpliterator(random, 0, size, minimum, lessThan), false);
	}

	/**
	 * next short
	 * @param random {@link Random}
	 * @return short {@link Stream}
	 */
	public static Stream<Short> nextShorts(Random random) {
		return nextShorts(random, Short.MIN_VALUE, Short.MAX_VALUE, Long.MAX_VALUE);
	}

	/**
	 * next short
	 * @return short {@link Stream}
	 */
	public static Stream<Short> nextShorts() {
		return nextShorts(new Random());
	}

	/**
	 * next char
	 * @param random {@link Random}
	 * @param minimum minimum char (inclusive)
	 * @param lessThan less than char (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#CHAR}
	 */
	public static char nextChar(Random random, char minimum, char lessThan) {
		return (char) nextInt(random, minimum, lessThan);
	}

	/**
	 * next char
	 * @param minimum minimum char (inclusive)
	 * @param lessThan less than char (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#CHAR}
	 */
	public static char nextChar(char minimum, char lessThan) {
		return nextChar(new Random(), minimum, lessThan);
	}

	/**
	 * next char
	 * @param random {@link Random}
	 * @return {@link com.github.andyshao.lang.PrimitiveType#CHAR}
	 */
	public static char nextChar(Random random) {
		return nextChar(random, Character.MIN_VALUE, Character.MAX_VALUE);
	}

	/**
	 * next char
	 * @return {@link com.github.andyshao.lang.PrimitiveType#CHAR}
	 */
	public static char nextChar() {
		return nextChar(new Random());
	}

	/**
	 * next char
	 * @param random {@link Random}
	 * @param minimum minimum char (inclusive)
	 * @param lessThan less than char (exclusive)
	 * @param size size number
	 * @return char {@link Stream}
	 */
	public static Stream<Character> nextChars(Random random, char minimum, char lessThan, long size) {
		return StreamSupport.stream(new RandomCharSpliterator(random, 0, size, minimum, lessThan), false);
	}

	/**
	 * next char
	 * @param random {@link Random}
	 * @return char {@link Stream}
	 */
	public static Stream<Character> nextChars(Random random) {
		return nextChars(random, Character.MIN_VALUE, Character.MAX_VALUE, Long.MAX_VALUE);
	}

	/**
	 * next char
	 * @return char {@link Stream}
	 */
	public static Stream<Character> nextChars() {
		return nextChars(new Random());
	}

	/**
	 * next int
	 * @param random {@link Random}
	 * @param minimum minimum int (inclusive)
	 * @param lessThan less than int (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#INT}
	 */
	public static int nextInt(Random random, int minimum, int lessThan) {
		if(lessThan <= minimum) throw new IllegalArgumentException("lessThan must greater than minimum");
		int bound = lessThan - minimum;
		return random.nextInt(bound) + minimum;
	}

	/**
	 * next int
	 * @param minimum minimum int (inclusive)
	 * @param lessThan less than int (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#INT}
	 */
	public static int nextInt(int minimum, int lessThan) {
		return nextInt(new Random(), minimum, lessThan);
	}

	/**
	 * next long
	 * @param random {@link Random}
	 * @param minimum minimum long (inclusive)
	 * @param lessThan less than long (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#LONG}
	 */
	public static long nextLong(Random random, long minimum, long lessThan) {
		if(lessThan <= minimum) throw new IllegalArgumentException("lessThan must greater than minimum");
		if(lessThan <= (long) Integer.MAX_VALUE && lessThan >= (long) Integer.MIN_VALUE) 
			return nextInt(random, (int) minimum, (int) lessThan);
		return random.longs(1, minimum, lessThan)
				.findFirst()
				.getAsLong();
	}

	/**
	 * next long
	 * @param minimum minimum long (inclusive)
	 * @param lessThan less than long (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#LONG}
	 */
	public static long nextLong(long minimum, long lessThan) {
		return nextLong(new Random(), minimum, lessThan);
	}

	/**
	 * next double
	 * @param random {@link Random}
	 * @param minimum minimum double (inclusive)
	 * @param lessThan less than double (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#DOUBLE}
	 */
	public static double nextDouble(Random random, double minimum, double lessThan) {
		if(lessThan <= minimum) throw new IllegalArgumentException("lessThan must greater than minimum");
		return random.doubles(1, minimum, lessThan)
				.findFirst()
				.getAsDouble();
//		return (random.nextDouble() * lessThan) + minimum;
	}

	/**
	 * next double
	 * @param minimum minimum double (inclusive)
	 * @param lessThan less than double (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#DOUBLE}
	 */
	public static double nextDouble(double minimum, double lessThan) {
		return nextDouble(new Random(), minimum, lessThan);
	}

	/**
	 * next float
	 * @param random {@link Random}
	 * @param minimum minimum float (inclusive)
	 * @param lessThan less than float (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#FLOAT}
	 */
	public static float nextFloat(Random random, float minimum, float lessThan) {
		if(lessThan <= minimum) throw new IllegalArgumentException("lessThan must greater than minimum");
		float result = (random.nextFloat() * (lessThan - minimum)) + minimum;
		if(result >= lessThan) result = Math.nextDown(lessThan);
		return result;
//		return (random.nextFloat() * lessThan) + minimum;
	}

	/**
	 * next float
	 * @param minimum minimum float (inclusive)
	 * @param lessThan less than float (exclusive)
	 * @return {@link com.github.andyshao.lang.PrimitiveType#FLOAT}
	 */
	public static float nextFloat(float minimum, float lessThan) {
		return nextFloat(new Random(), minimum, lessThan);
	}

	/**
	 * next float
	 * @param random {@link Random}
	 * @param minimum minimum float (inclusive)
	 * @param maximum less than float (exclusive)
	 * @param size size number
	 * @return float {@link Stream}
	 */
	public static Stream<Float> nextFloats(Random random, float minimum, float maximum, long size) {
		if(maximum < minimum) throw new IllegalArgumentException("maximum less than minimum");
		return StreamSupport.stream(new RandomFloatSpliterator(random, 0, size, minimum, maximum), false);
	}

	/**
	 * next float
	 * @param random {@link Random}
	 * @return float {@link Stream}
	 */
	public static Stream<Float> nextFloats(Random random) {
		return nextFloats(random, Float.MIN_VALUE, Float.MAX_VALUE, Long.MAX_VALUE);
	}

	/**
	 * chose by percent
	 * @param random {@link Random}
	 * @param percent percent number
	 * @return {@link com.github.andyshao.lang.PrimitiveType#DOUBLE}
	 */
	public static boolean choseByPercent(Random random, double percent) {
		if(percent < 0 || percent > 100) throw new IllegalArgumentException("percent is greater than 100 or less than 0");
		return (percent / 100.0) > random.nextDouble();
	}

	/**
	 * next by percent
	 * @param random {@link Random}
	 * @param percentages double mapper
	 * @return percent string
	 */
	public static Optional<String> nextByPercent(Random random, Map<String, Double> percentages) {
		Convert<Map<String, Double>, Map<String, Function<String, Double>>> mapConvert = 
				MapOperation.<String, String, Double, Function<String, Double>>convertMap(input -> {
			return Optional.of(Pair.of(input.getFirst(), FunctionOperation.lambda(t -> input.getSecond())));
		});
		return nextByPercentFunction(random, mapConvert.convert(percentages));
	}

	/**
	 * next by percent function
	 * @param random {@link Random}
	 * @param percentages percentage function mapper
	 * @return percent string
	 */
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

	/**
	 * random short spliterator
	 */
	static class RandomShortSpliterator extends RandomBaseSpliterator<Short> {

		/**
		 * build {@link RandomShortSpliterator}
		 * @param random {@link Random}
		 * @param index index
		 * @param fence fence
		 * @param minimum minimum value (inclusive)
		 * @param maximum maximum value (exclusive)
		 */
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

	/**
	 * Random char spliterator
	 */
	static class RandomCharSpliterator extends RandomBaseSpliterator<Character> {
		/**
		 * build {@link RandomCharSpliterator}
		 * @param random {@link Random}
		 * @param index index
		 * @param fence fence
		 * @param minimum minimum value (inclusive)
		 * @param maximum maximum value (exclusive)
		 */
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

	/**
	 * Random float spliterator
	 */
	static class RandomFloatSpliterator extends RandomBaseSpliterator<Float> {

		/**
		 * build {@link RandomFloatSpliterator}
		 * @param random {@link Random}
		 * @param index index
		 * @param fence fence
		 * @param minimum minimum value (inclusive)
		 * @param maximum maximum value (exclusive)
		 */
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

	/**
	 * Random base spliterator
	 * @param <E> data type
	 */
	@AllArgsConstructor(access = AccessLevel.PROTECTED)
	static abstract class RandomBaseSpliterator<E> implements Spliterator<E> {
		/**{@link Random}*/
		protected final Random random;
		/**index*/
		protected long index;
		/**fence*/
		protected long fence;
		/**minimum value (inclusive)*/
		protected final E minimum;
		/**maximum value (exclusive)*/
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

		/**
		 * require next
		 * @return the next item
		 */
		protected abstract E requireNext();

		/**
		 * the middle position
		 * @return position
		 */
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
