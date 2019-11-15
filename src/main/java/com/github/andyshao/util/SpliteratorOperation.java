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
	
	public static final Spliterator<Byte> spliterator(byte[] bs, int additionalCharacteristics) {
		return spliterator(bs, 0, bs.length, additionalCharacteristics);
	}
	
	public static final Spliterator<Byte> spliterator(final byte[] bs, final int fromIndex, final int toIndex,
            final int additionalCharacteristics) {
		return new ByteArraySpliterator(bs, toIndex, fromIndex, additionalCharacteristics);
	}
	
	public static final Spliterator<Character> spliterator(char[] cs, int additionalCharacteristics) {
		return spliterator(cs, 0, cs.length, additionalCharacteristics);
	}
	
	public static final Spliterator<Character> spliterator(char[] cs, int fromIndex, int toIndex,
            int additionalCharacteristics) {
		return CharArraySpliterator.builder()
				.array(cs)
				.index(fromIndex)
				.fence(toIndex)
				.characteristics(additionalCharacteristics)
				.build();
	}
	
	public static Spliterator<Short> spliterator(short[] array, int additionalCharacteristics) {
		return spliterator(array, additionalCharacteristics);
	}
	
	public static Spliterator<Short> spliterator(short[] array, int fromIndex, int toIndex, 
			int additionalCharacteristics) {
		return ShortArraySpliterator.builder()
				.array(array)
				.index(fromIndex)
				.fence(toIndex)
				.characteristics(additionalCharacteristics)
				.build();
	}

	private SpliteratorOperation() {}

	public static Spliterator<Float> spliterator(float[] array, int additionalCharacteristics) {
		return spliterator(array, additionalCharacteristics);
	}

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
