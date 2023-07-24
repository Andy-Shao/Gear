package com.github.andyshao.util;

import java.util.Spliterator;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 15, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class SpliteratorOperation {

	/**
	 * byte array spliterator
	 * @param bs {@link com.github.andyshao.lang.ArrayType#BYTE_ARRAY}
	 * @param additionalCharacteristics additional characteristics
	 * @return byte {@link Spliterator}
	 */
	public static final Spliterator<Byte> spliterator(byte[] bs, int additionalCharacteristics) {
		return spliterator(bs, 0, bs.length, additionalCharacteristics);
	}

	/**
	 * byte array spliterator
	 * @param bs {@link com.github.andyshao.lang.ArrayType#BYTE_ARRAY}
	 * @param fromIndex start position (inclusive)
	 * @param toIndex end position (exclusive)
	 * @param additionalCharacteristics additional characteristics
	 * @return byte {@link Spliterator}
	 */
	public static final Spliterator<Byte> spliterator(final byte[] bs, final int fromIndex, final int toIndex,
            final int additionalCharacteristics) {
		return new ByteArraySpliterator(bs, toIndex, fromIndex, additionalCharacteristics);
	}

	/**
	 * spliterator
	 * @param cs {@link com.github.andyshao.lang.ArrayType#CHAR_ARRAY}
	 * @param additionalCharacteristics additional characteristics
	 * @return char {@link Spliterator}
	 */
	public static final Spliterator<Character> spliterator(char[] cs, int additionalCharacteristics) {
		return spliterator(cs, 0, cs.length, additionalCharacteristics);
	}

	/**
	 * spliterator
	 * @param cs {@link com.github.andyshao.lang.ArrayType#CHAR_ARRAY}
	 * @param fromIndex start point (inclusive)
	 * @param toIndex end point (exclusive)
	 * @param additionalCharacteristics additional characteristics
	 * @return char {@link Spliterator}
	 */
	public static final Spliterator<Character> spliterator(char[] cs, int fromIndex, int toIndex,
            int additionalCharacteristics) {
		return CharArraySpliterator.builder()
				.array(cs)
				.index(fromIndex)
				.fence(toIndex)
				.characteristics(additionalCharacteristics)
				.build();
	}

	/**
	 * spliterator
	 * @param array {@link com.github.andyshao.lang.ArrayType#SHORT_ARRAY}
	 * @param additionalCharacteristics additional characteristics
	 * @return short {@link Spliterator}
	 */
	public static Spliterator<Short> spliterator(short[] array, int additionalCharacteristics) {
		return spliterator(array, additionalCharacteristics);
	}

	/**
	 * spliterator
	 * @param array {@link com.github.andyshao.lang.ArrayType#SHORT_ARRAY}
	 * @param fromIndex start point (inclusive)
	 * @param toIndex end point (exclusive)
	 * @param additionalCharacteristics additional characteristics
	 * @return short {@link Spliterator}
	 */
	public static Spliterator<Short> spliterator(short[] array, int fromIndex, int toIndex, 
			int additionalCharacteristics) {
		return ShortArraySpliterator.builder()
				.array(array)
				.index(fromIndex)
				.fence(toIndex)
				.characteristics(additionalCharacteristics)
				.build();
	}

	private SpliteratorOperation() {
		throw new AssertionError("No constructor support");
	}

	/**
	 * spliterator
	 * @param array {@link com.github.andyshao.lang.ArrayType#FLOAT_ARRAY}
	 * @param additionalCharacteristics additional characteristics
	 * @return float {@link Spliterator}
	 */
	public static Spliterator<Float> spliterator(float[] array, int additionalCharacteristics) {
		return spliterator(array, additionalCharacteristics);
	}

	/**
	 * spliterator
	 * @param array {@link com.github.andyshao.lang.ArrayType#FLOAT_ARRAY}
	 * @param startInclusive start point (inclusive)
	 * @param endExclusive end point (exclusive)
	 * @param additionalCharacteristics additional characteristics
	 * @return float {@link Spliterator}
	 */
	public static Spliterator<Float> spliterator(float[] array, int startInclusive, int endExclusive,
			int additionalCharacteristics) {
		return FloatArraySpliterator.builder()
				.array(array)
				.index(startInclusive)
				.fence(endExclusive)
				.characteristics(additionalCharacteristics)
				.build();
	}
}
