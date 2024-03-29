package com.github.andyshao.lang;

import com.github.andyshao.reflect.ArrayOperation;
import com.github.andyshao.util.stream.StreamOperation;

import java.io.Serial;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Oct 2, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class LongArrayWrapper extends ArrayWrapperModel implements ArrayWrapper {
    @Serial
    private static final long serialVersionUID = 3577544893965403772L;
    /**{@link ArrayType#LONG_ARRAY}*/
    private final long[] array;

    /**
     * build {@link LongArrayWrapper}
     * @param array {@link ArrayType#LONG_ARRAY}
     */
    public LongArrayWrapper(long[] array) {
        this.array = array;
        this.mark = 0;
        this.position = 0;
        this.limit = this.array.length;
    }

    @Override
    public long[] array() {
        return this.array;
    }

    @Override
    public ArrayWrapper backup() {
        ArrayWrapper result = new LongArrayWrapper(ArrayOperation.backup(this.array()));
        result.position(this.position());
        result.limit(this.limit());
        result.mark();
        return result;
    }

    @Override
    public int capacity() {
        return this.array().length;
    }

    @Override
    public ArrayWrapper duplicate() {
        ArrayWrapper result = new LongArrayWrapper(this.array());
        result.position(this.position());
        result.limit(this.limit());
        result.mark();
        return result;
    }

    @Override
    public Long get(int index) {
        return this.array()[this.calculateRealIndex(index)];
    }

    @Override
    public Long put(Object value , int index) {
    	int realIndex = this.calculateRealIndex(index);
        Long result = this.get(realIndex);
        this.array()[realIndex] = Convert.OB_2_LONG.convert(value);
        return result;
    }

	@Override
	public Stream<Long> stream() {
		return StreamOperation.valueOf(Arrays.stream(this.array));
	}

}
