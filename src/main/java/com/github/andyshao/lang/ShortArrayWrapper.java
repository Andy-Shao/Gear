package com.github.andyshao.lang;

import com.github.andyshao.reflect.ArrayOperation;

import java.io.Serial;
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
public class ShortArrayWrapper extends ArrayWrapperModel implements ArrayWrapper {
    @Serial
    private static final long serialVersionUID = 8712917356401328024L;
    /**{@link ArrayType#SHORT_ARRAY}*/
    private final short[] array;

    /**
     * build {@link ShortArrayWrapper}
     * @param array the array
     */
    public ShortArrayWrapper(short[] array) {
        this.array = array;
        this.position = 0;
        this.limit = this.array.length;
        this.mark = 0;
    }

    @Override
    public short[] array() {
        return this.array;
    }

    @Override
    public ArrayWrapper backup() {
        ArrayWrapper result = new ShortArrayWrapper(ArrayOperation.backup(this.array()));
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
        ArrayWrapper result = new ShortArrayWrapper(this.array());
        result.position(this.position());
        result.limit(this.limit());
        result.mark();
        return result;
    }

    @Override
    public Short get(int index) {
        return this.array()[this.calculateRealIndex(index)];
    }

    @Override
    public Short put(Object value , int index) {
    	int realIndex = this.calculateRealIndex(index);
        Short result = this.get(realIndex);
        this.array()[realIndex] = Convert.OB_2_SHORT.convert(value);
        return result;
    }

	@Override
	public Stream<Short> stream() {
		return ArrayOperation.stream(this.array);
	}
}
