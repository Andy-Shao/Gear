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
 * Copyright: Copryright(c) Oct 1, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class IntArrayWrapper extends ArrayWrapperModel implements ArrayWrapper {
    @Serial
    private static final long serialVersionUID = 9196038926763439775L;
    /**{@link ArrayType#INT_ARRAY}*/
    private final int[] array;

    /**
     * build {@link IntArrayWrapper}
     * @param array array
     */
    public IntArrayWrapper(int[] array) {
        this.array = array;
        this.position = 0;
        this.limit = this.array.length;
        this.mark = 0;
    }

    @Override
    public int[] array() {
        return this.array;
    }

    @Override
    public ArrayWrapper backup() {
        ArrayWrapper result = new IntArrayWrapper(ArrayOperation.backup(this.array()));
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
        ArrayWrapper result = new IntArrayWrapper(this.array());
        result.position(this.position());
        result.limit(this.limit());
        result.mark();
        return result;
    }

    @Override
    public Integer get(int index) {
		return this.array()[this.calculateRealIndex(index)];
    }

    @Override
    public Integer put(Object value , int index) {
    	int realIndex = this.calculateRealIndex(index);
		int tmp = this.array()[realIndex];
        this.array()[realIndex] = Convert.OB_2_INT.convert(value);
        return tmp;
    }

	@Override
	public Stream<Integer> stream() {
		return StreamOperation.valueOf(Arrays.stream(this.array));
	}

}
