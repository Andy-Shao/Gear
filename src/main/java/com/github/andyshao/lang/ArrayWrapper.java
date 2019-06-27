package com.github.andyshao.lang;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;

import com.github.andyshao.reflect.ArrayOperation;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Oct 1, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 * @see ByteArrayWrapper
 * @see IntArrayWrapper
 * @see CharArrayWrapper
 * @see LongArrayWrapper
 * @see ShortArrayWrapper
 * @see FloatArrayWrapper
 * @see DoubleArrayWrapper
 * @see ObjectArrayWrapper
 */
public interface ArrayWrapper extends Iterable<Object> , Serializable {
    public static <ARRAY> ArrayWrapper newInstance(Class<ARRAY> arrayType , int length) {
        if (!arrayType.isArray()) throw new IllegalArgumentException();
        @SuppressWarnings("unchecked")
        ARRAY array = (ARRAY) Array.newInstance(arrayType.getComponentType() , length);
        return ArrayWrapper.wrap(array);
    }

    @SuppressWarnings("unchecked")
    public static <ARRAY , E> ArrayWrapper wrap(ARRAY array) {
        if (!array.getClass().isArray()) throw new IllegalArgumentException();
        if (int[].class.isInstance(array)) return new IntArrayWrapper((int[]) array);
        else if (byte[].class.isInstance(array)) return new ByteArrayWrapper((byte[]) array);
        else if (char[].class.isInstance(array)) return new CharArrayWrapper((char[]) array);
        else if (short[].class.isInstance(array)) return new ShortArrayWrapper((short[]) array);
        else if (float[].class.isInstance(array)) return new FloatArrayWrapper((float[]) array);
        else if (double[].class.isInstance(array)) return new DoubleArrayWrapper((double[]) array);
        else if (long[].class.isInstance(array)) return new LongArrayWrapper((long[]) array);
        else return new ObjectArrayWrapper<E>((E[]) array);
    }
    
    public static boolean isSameType(ArrayWrapper left, ArrayWrapper right) {
    	Class<?> leftType = left.array().getClass().getComponentType();
    	Class<?> rightType = right.array().getClass().getComponentType();
    	return leftType == rightType;
    }
    
    public default ArrayType type() {
    	Class<?> componentType = this.array().getClass().getComponentType();
    	if(int.class.isInstance(componentType)) return ArrayType.INT_ARRAY;
    	else if(byte.class.isInstance(componentType)) return ArrayType.BYTE_ARRAY;
    	else if(char.class.isInstance(componentType)) return ArrayType.CHAR_ARRAY;
    	else if(short.class.isInstance(componentType)) return ArrayType.SHORT_ARRAY;
    	else if(float.class.isInstance(componentType)) return ArrayType.SHORT_ARRAY;
    	else if(double.class.isInstance(componentType)) return ArrayType.DOUBLE_ARRAY;
    	else if(long.class.isInstance(componentType)) return ArrayType.LONG_ARRAY;
    	else return ArrayType.OBJ_ARRAY;
    }

    /**
     * get the array which store in this class
     * 
     * @return return array
     */
    public Object array();

    /**
     * backup this {@link ArrayWrapper}<br>
     * mark = 0; position, limit and capacity as same as old class.
     * the array values is same but rebuild the new array
     * 
     * @return return the backup objection
     */
    public ArrayWrapper backup();

    /**
     * This value is unchangeable
     * 
     * @return The capacity of {@link ArrayWrapper}
     */
    public int capacity();

    /**
     * clip and create the new array
     * 
     * @param start the start position
     * @param end the end position(exclude)
     * @param arrayType the tye of array
     * @param <ARRAY> the type of array
     * @return return the new array
     */
    public default <ARRAY> ARRAY clip(int start , int end , Class<ARRAY> arrayType) {
        ArrayWrapper tmp = this.duplicate();
        tmp.position(start);
        tmp.limit(end);
        return tmp.usedArray(arrayType);
    }

    /**
     * duplicate this {@link ArrayWrapper}<br>
     * mark = 0; position, limit, capacity and array as same as old class.
     * the array still the old one and doesn't change.
     * 
     * @return return the duplicate objection
     */
    public ArrayWrapper duplicate();

    /**
     * get the value which position of array's is index
     * 
     * @param index the position of value
     * @return the value
     * @throws ArrayIndexOutOfBoundsException if index out of array length
     */
    public Object get(int index) throws ArrayIndexOutOfBoundsException;

    @Override
    public default Iterator<Object> iterator() {
        return new Iterator<Object>() {
            private int index = ArrayWrapper.this.position();

            @Override
            public boolean hasNext() {
                return this.index < ArrayWrapper.this.limit();
            }

            @Override
            public Object next() {
                Object result = ArrayWrapper.this.get(this.index);
                this.index++;
                return result;
            }
        };
    }

    /**
     * get the length of used array
     * 
     * @return length of used array
     */
    public default int length() {
        return this.limit() - this.position();
    }

    /**
     * limit of array
     * 
     * @return the limit of this array
     */
    public int limit();

    /**
     * set the limit of array
     * 
     * @param limit the limit of array
     * @throws ArrayIndexOutOfBoundsException if limit out of array length
     */
    public void limit(int limit) throws ArrayIndexOutOfBoundsException;

    /**
     * mark = position
     */
    public void mark();

    /**
     * mark = limit
     */
    public void markLimit();

    /**
     * get the position of array
     * 
     * @return the position of array
     */
    public int position();

    /**
     * set the position of array
     * 
     * @param position the position of array
     * @throws ArrayIndexOutOfBoundsException if limit out of array length
     */
    public void position(int position) throws ArrayIndexOutOfBoundsException;

    /**
     * set value of array's which position is index
     * 
     * @param value the value which should be processed
     * @param index the index which position should be processed
     * @return the last value of index position
     * @throws ArrayIndexOutOfBoundsException if index out of array length
     */
    public Object put(Object value , int index) throws ArrayIndexOutOfBoundsException;

    /**
     * position = mark
     */
    public void reset();

    /**
     * limit = mark
     */
    public void resetLimit();

    /**
     * the used array which between position and end(exclude) in array
     * 
     * @param arrayType the type of array which should be return to
     * @param <ARRAY> the type of array
     * @return the used array
     */
    @SuppressWarnings("unchecked")
    public default <ARRAY> ARRAY usedArray(Class<ARRAY> arrayType) {
        Object usedArray = ArrayOperation.splitArray(this.array() , this.position() , this.limit());
        if (arrayType.isInstance(usedArray)) return (ARRAY) usedArray;
        else return ArrayOperation.pack_unpack(usedArray , arrayType);
    }
}
