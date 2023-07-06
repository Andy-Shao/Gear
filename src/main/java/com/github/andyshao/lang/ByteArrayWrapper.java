package com.github.andyshao.lang;

import com.github.andyshao.reflect.ArrayOperation;

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
public class ByteArrayWrapper extends ArrayWrapperModel implements ArrayWrapper {
    private static final long serialVersionUID = 1819607580863945576L;
    private final byte[] array;

    /**
     * Build {@link ByteArrayWrapper}
     * @param array {@link ArrayType#BYTE_ARRAY}
     */
    public ByteArrayWrapper(byte[] array) {
        this.array = array;
        this.limit = this.array.length;
        this.position = 0;
        this.mark = 0;
    }

    @Override
    public byte[] array() {
        return this.array;
    }

    @Override
    public ArrayWrapper backup() {
        ArrayWrapper result = new ByteArrayWrapper(ArrayOperation.backup(this.array()));
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
        ArrayWrapper result = new ByteArrayWrapper(this.array());
        result.position(this.position());
        result.limit(this.limit());
        result.mark();
        return result;
    }

    @Override
    public Byte get(int index) {
        return this.array()[this.calculateRealIndex(index)];
    }

    @Override
    public Byte put(Object value , int index) {
        int realIndex = this.calculateRealIndex(index);
        Byte result = this.get(realIndex);
        this.array()[realIndex] = (Byte) value;
        return result;
    }

	@Override
	public Stream<Byte> stream() {
		return ArrayOperation.stream(this.array);
	}

}
