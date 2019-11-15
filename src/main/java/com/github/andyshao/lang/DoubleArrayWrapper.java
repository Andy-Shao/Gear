package com.github.andyshao.lang;

import java.util.Arrays;
import java.util.stream.Stream;

import com.github.andyshao.reflect.ArrayOperation;
import com.github.andyshao.util.stream.StreamOperation;

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
public class DoubleArrayWrapper extends ArrayWrapperModel implements ArrayWrapper {
    private static final long serialVersionUID = 7582951027306249651L;
    private final double[] array;

    public DoubleArrayWrapper(double[] array) {
        this.array = array;
        this.position = 0;
        this.mark = 0;
        this.limit = this.array.length;
    }

    @Override
    public double[] array() {
        return this.array;
    }

    @Override
    public ArrayWrapper backup() {
        ArrayWrapper result = new DoubleArrayWrapper(ArrayOperation.backup(this.array()));
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
        ArrayWrapper result = new DoubleArrayWrapper(this.array());
        result.position(this.position());
        result.limit(this.limit());
        result.mark();
        return result;
    }

    @Override
    public Double get(int index) {
        return this.array()[this.calculateRealIndex(index)];
    }

    @Override
    public Double put(Object value , int index) {
    	int realIndex = this.calculateRealIndex(index);
        Double result = this.get(realIndex);
        this.array()[realIndex] = Convert.OB_2_DOUBLE.convert(value);
        return result;
    }

	@Override
	public Stream<Double> stream() {
		return StreamOperation.valueOf(Arrays.stream(this.array));
	}

}
