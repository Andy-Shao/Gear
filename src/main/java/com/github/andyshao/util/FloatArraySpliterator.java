package com.github.andyshao.util;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

import lombok.Builder;

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
@Builder
public class FloatArraySpliterator implements Spliterator<Float> {
	private final float[] array;
	private int index;
	private final int fence;
	private final int characteristics;

	@Override
	public boolean tryAdvance(Consumer<? super Float> action) {
		Objects.requireNonNull(action);
		if(this.index < this.fence) {
			float item = this.array[this.index++];
			action.accept(item);
			return true;
		}
		return false;
	}

	@Override
	public Spliterator<Float> trySplit() {
		int mid = (this.index + this.fence) >>> 1, ol = this.index;
		return (ol >= mid) ? null : new FloatArraySpliterator(array, this.index = mid, ol, characteristics);
	}

	@Override
	public long estimateSize() {
		return this.fence - this.index;
	}

	@Override
	public int characteristics() {
		return this.characteristics;
	}

}
