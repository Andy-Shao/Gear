package com.github.andyshao.reflect;

import com.github.andyshao.lang.*;
import com.github.andyshao.util.CollectionOperation;
import com.github.andyshao.util.SpliteratorOperation;
import com.github.andyshao.util.stream.StreamOperation;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 
 * Title: Some tools of {@link Array}<br>
 * Descript:<br>
 * Copyright: Copryright(c) Oct 5, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ArrayOperation {
    /**
     * Set value into the array at zero position
     * @param array a int array
     * @param value a int value
     * @return the origin array
     */
    public static int[] of(int[] array, int value){
        array[0] = value;
        return array;
    }

    /**
     * Set value into the array at zero position
     * @param array a object array
     * @param value a object value
     * @return the origin array
     * @param <T> the type of the array member's
     */
    public static <T> T[] of(T[] array, T value){
        array[0] = value;
        return array;
    }

    /**
     * Set value into the array at zero position
     * @param array a short array
     * @param value a short value
     * @return the origin array
     */
    public static short[] of(short[] array, short value){
        array[0] = value;
        return array;
    }

    /**
     * Set value into the array at zero position
     * @param array a byte array
     * @param value a byte value
     * @return the origin array
     */
    public static byte[] of(byte[] array, byte value){
        array[0] = value;
        return array;
    }

    /**
     * Set value into the array at zero position
     * @param array a char array
     * @param value a char value
     * @return the origin array
     */
    public static char[] of(char[] array, char value){
        array[0] = value;
        return array;
    }

    /**
     * Set value into the array at zero position
     * @param array a long array
     * @param value a long value
     * @return the origin array
     */
    public static long[] of(long[] array, long value){
        array[0] = value;
        return array;
    }

    /**
     * Set value into the array at zero position
     * @param array a float array
     * @param value a float value
     * @return the origin array
     */
    public static float[] of(float[] array, float value){
        array[0] = value;
        return array;
    }

    /**
     * Set value into the array at zero position
     * @param array a double array
     * @param value a double value
     * @return the origin array
     */
    public static double[] of(double[] array, double value){
        array[0] = value;
        return array;
    }

    /**
     * check the array type
     * @param array the {@link ArrayWrapper}
     * @return the it is empty or null, then return true
     */
    public static boolean isEmptyOrNull(ArrayWrapper array) {
        if(array instanceof NullArrayWrapper) return true;
        return array.length() == 0;
    }

    /**
     * Generate a collection
     * @param col the collection object
     * @param ts the members of the collection
     * @return a collection
     * @param <T> the type of the collection
     * @param <E> the type of the member
     */
	@SafeVarargs
	public static <T, E extends Collection<T>> E asCollection(E col, T...ts) {
    	CollectionOperation.addAll(col, ts);
    	return col;
    }

    /**
     * Generate a list
     * @param ts the members of the list
     * @return a list
     * @param <T> the type of the data
     */
	@SafeVarargs
	public static <T> Collection<T> asCollection(T...ts) {
    	return Arrays.asList(ts);
    }

    /**
     * Add items into the list
     * @param list the list
     * @param ts the new members of the list
     * @return the origin list
     * @param <T> the type of the data
     */
	@SafeVarargs
	public static <T> List<T> asList(List<T> list, T...ts){
    	return asCollection(list, ts);
    }

    /**
     * Add items into the queue
     * @param queue the queue which is added the list
     * @param ts the new members of the queue
     * @return the origin queue
     * @param <T> the type of the data
     */
	@SafeVarargs
	public static <T> Queue<T> asQueue(Queue<T> queue, T...ts) {
    	for(T t : ts) queue.offer(t);
    	return queue;
    }

    /**
     * Get a {@link ArrayBlockingQueue} which includes the members
     * @param ts the members of the queue
     * @return the {@link ArrayBlockingQueue}
     * @param <T> the type of the data
     */
	@SafeVarargs
	public static <T> Queue<T> asQueue(T...ts) {
    	return asQueue(new ArrayBlockingQueue<T>(ts.length), ts);
    }

    /**
     * add items into a set
     * @param set the set which should add members
     * @param ts the new members of the set
     * @return the origin set
     * @param <T> the type of data
     */
	@SafeVarargs
	public static <T> Set<T> asSet(Set<T> set, T...ts){
    	return asCollection(set, ts);
    }

    /**
     * get a {@link HashSet} which includes the ts members
     * @param ts the new members of the set
     * @return a {@link HashSet}
     * @param <T> the type of data
     */
	@SafeVarargs
	public static <T> Set<T> asSet(T...ts) {
    	return asSet(new HashSet<>(ts.length), ts);
    }

    /**
     * Copy all members of the array
     * @param array the array which should be copied
     * @return the new array which has same members of the array
     * @param <T> the type of the array
     */
	public static <T> T backup(T array) {
        return ArrayOperation.splitArray(array , 0 , Array.getLength(array));
    }

    /**
     * Get a byte array comparator
     * @return a byte array comparator
     */
	public static Comparator<byte[]> byteArrayComparator() {
		return byteArrayComparator(ByteOperation.comparator());
	}

    /**
     * Use byte comparator in order to generate the byte array comparator
     * @param byteComparator a byte comparator
     * @return a byte array comparator
     */
	public static Comparator<byte[]> byteArrayComparator(Comparator<Byte> byteComparator) {
		return (l, r) -> comparator(byteComparator).compare(ArrayWrapper.wrap(l), ArrayWrapper.wrap(r));
	}

    /**
     * Get a char array comparator
     * @return a char array comparator
     */
	public static Comparator<char[]> charArrayComparator() {
		return charArrayComparator(CharOperation.comparator());
	}

    /**
     * Use a char comparator in order to generate a char array comparator
     * @param charComparator a char comparator
     * @return a char array comparator
     */
	public static Comparator<char[]> charArrayComparator(Comparator<Character> charComparator) {
		return (l, r) -> comparator(charComparator).compare(ArrayWrapper.wrap(l), ArrayWrapper.wrap(r));
	}

    /**
     * Get a general array comparator by the array member's comparator
     * @param componentComparator the array member's comparator
     * @return the array comparator
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Comparator<ArrayWrapper> comparator(Comparator componentComparator) {
		return (left, right) -> {
			if(!ArrayWrapper.isSameType(left, right)) throw new IllegalArgumentException("Array type should be same");
			if(Objects.isNull(left) && Objects.nonNull(right)) return -1;
			else if(Objects.nonNull(left) && Objects.isNull(right)) return 1;
			int leftLength = left.length();
			int rightLength = right.length();
			if(leftLength > rightLength) return 1;
			else if(leftLength < rightLength) return -1;
			int compare = 0;
			for(int i=0; i<leftLength; i++) {
				compare = componentComparator.compare(left.get(i), right.get(i));
				if(compare == 0) continue;
				else return compare;
			}
			return compare;
		};
	}
	
	/**
     * Let a Object[][] convert to a Map.
     * Let Object[i][0] is key and Object[i][1] is value.
     * 
     * @param convertK the convert of key's
     * @param convertV the convert of value's
     * @param map the input map which is result
     * @param arrays the information
     * @param <K> the type of key
     * @param <V> the type of value
     * @return the Map what you want to get
     */
    public static <K , V> Map<K , V> convertToMap(Convert<Object , K> convertK , Convert<Object , V> convertV , Map<K , V> map , Object[]... arrays) {
        for (Object[] array : arrays)
            map.put(convertK.convert(array[0]) , convertV.convert(array[1]));
        return map;
    }

    /**
     * Get a double array comparator
     * @return a double array comparator
     */
	public static Comparator<double[]> doubleArrayComparator() {
		return doubleArrayComparator(DoubleOperation.comparator());
	}

    /**
     * Use double comparator in order to generate a double array comparator
     * @param doubleComparator a double comparator
     * @return a double array comparator
     */
	public static Comparator<double[]> doubleArrayComparator(Comparator<Double> doubleComparator) {
		return (l, r) -> comparator(doubleComparator).compare(ArrayWrapper.wrap(l), ArrayWrapper.wrap(r));
	}
	
	/**
     * flip array
     * 
     * @param array the array which is processed.
     * @param <T> the type of array
     * @return A flip array.
     */
    @SuppressWarnings("unchecked")
    public static <T> T flipArray(T array) {
        ArrayWrapper arrayWrapper = ArrayWrapper.wrap(array);
        int length = arrayWrapper.length();
        if (length == 0) return array;
        ArrayWrapper temp = ArrayWrapper.newInstance(array.getClass() , length);
        for (int i = length - 1 , b = temp.position() ; i >= arrayWrapper.position() ; i-- , b++)
            temp.put(arrayWrapper.get(i) , b);
        return (T) temp.array();
    }

    /**
     * Get a float array comparator
     * @return a float array comparator
     */
	public static Comparator<float[]> floatArrayComparator() {
		return floatArrayComparator(FloatOperation.comparator());
	}

    /**
     * Use a float comparator in order to generate a float array comparator
     * @param floatComparator a float comparator
     * @return a float array comparator
     */
	public static Comparator<float[]> floatArrayComparator(Comparator<Float> floatComparator) {
		return (l, r) -> comparator(floatComparator).compare(ArrayWrapper.wrap(l), ArrayWrapper.wrap(r));
	}
	
	/**
     * get the element of array's
     * 
     * @param array the array which is processed.
     * @param index the address of value
     * @param nullDefault the default value
     * @param <T> the type of array
     * @return the value which index is index.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValue(T array , int index , Object nullDefault) {
        if (ArrayOperation.isAbove(array , index)) return (T) nullDefault;
        return (T) Array.get(array , index);
    }
	
    /**
     * find out the location of first item.
     * 
     * @param array the array which is processed
     * @param item the item which should be found
     * @return if can't find out anything then return -1
     */
    public static int indexOf(ArrayWrapper array , Object item) {
        for (int i = array.position() ; i < array.length() ; i++)
            if (Objects.equals(array.get(i) , item)) return i;
        return -1;
    }
    
    /**
     * find out the location of first item.
     * 
     * @param array the array which is processed
     * @param start the start position of array
     * @param end then end position of array(exclude)
     * @param item the item which should be found
     * @param <T> the type of array
     * @return if can't find out anything then return -1
     * @see ArrayOperation#indexOf(ArrayWrapper, Object)
     */
    public static <T> int indexOf(T array , int start , int end , Objects item) {
        ArrayWrapper arrayWrapper = ArrayWrapper.wrap(array);
        arrayWrapper.position(start);
        arrayWrapper.limit(end);
        return ArrayOperation.indexOf(array , item);
    }
    
    /**
     * find out the location of first item.
     * 
     * @param array
     *            the array which is processed
     * @param item The item which should be found
     * @param <T> the type of array
     * @return if can't find out anything then return -1
     * @see ArrayOperation#indexOf(Object, int, int, Objects)
     */
    public static <T> int indexOf(T array , Object item) {
        return ArrayOperation.indexOf(ArrayWrapper.wrap(array) , item);
    }
    
    /**
     * find out the location of first target
     * 
     * @param array the array which is processed
     * @param start the start position of array
     * @param end the end position of end(exclude)
     * @param target the target which should be found
     * @param <T> the type of the array
     * @return if it can't find out then return -1
     * @see ArrayOperation#indexOfAllWrapper(ArrayWrapper, ArrayWrapper)
     */
    public static <T> int indexOfAll(T array , int start , int end , T target) {
        ArrayWrapper arrayWrapper = ArrayWrapper.wrap(array);
        arrayWrapper.position(start);
        arrayWrapper.limit(end);
        return ArrayOperation.indexOfAllWrapper(arrayWrapper , ArrayWrapper.wrap(target));
    }
    
    /**
     * find out the first time of the target.
     * 
     * @param array the array which should be searched.
     * @param target the target which should be found out from array
     * @param <T> the type of the array
     * @return if can't find out anything from array , return -1
     * @see ArrayOperation#indexOfAllWrapper(ArrayWrapper, ArrayWrapper)
     */
    public static <T> int indexOfAll(T array , T target) {
        return ArrayOperation.indexOfAllWrapper(ArrayWrapper.wrap(array) , ArrayWrapper.wrap(target));
    }
    
    /**
     * find out the first time of the target
     * 
     * @param array the array which should be searched
     * @param target the target which should be found out from array
     * @return if can't find out anything from array then return -1
     */
    public static int indexOfAllWrapper(ArrayWrapper array , ArrayWrapper target) {
        if (array.length() == 0 || target.length() == 0) throw new IllegalArgumentException("array or target can't empty!");
        if (target.length() > array.length()) return -1;
        int index = target.position();
        int position = -1;
        LOOP: for (int i = array.position() ; i < array.length() ; i++)
            if (Objects.equals(array.get(i) , target.get(index++))) {
                if (index == 1) position = i;
                if (index == target.length()) break LOOP;
            } else if (Objects.equals(array.get(i) , target.get(target.position()))) {
                index = target.position() + 1;
                position = i;
                if (index == target.length()) break LOOP;
            } else {
                index = target.position();
                position = -1;
            }
        return position;
    }

    /**
     * Get a int array comparator
     * @return a array comparator
     */
    public static Comparator<int[]> intArrayComparator() {
		return intArrayComparator(IntegerOperation.comparator());
	}

    /**
     * Use a int comparator in order to generate int array comparator
     * @param intComparator a int comparator
     * @return a int array comparator
     */
    public static Comparator<int[]> intArrayComparator(Comparator<Integer> intComparator) {
		return (o1, o2) -> {
			return comparator(intComparator).compare(ArrayWrapper.wrap(o1), ArrayWrapper.wrap(o2));
		};
	}

    /**
     * check the index, is it out of the array length.
     * 
     * @param array the array which is processed.
     * @param index the address of value
     * @param <T> the type of array
     * @return if the index above array then return true
     */
    private static <T> boolean isAbove(T array , int index) {
        return Array.getLength(array) <= index;
    }

    /**
     * check the index site of array
     * 
     * @param array the array which is processed.
     * @param index the address of value
     * @param <T> the type of array
     * @return if the index doesn't above array and the value is null , return
     *         true
     */
    public static <T> boolean isEmpty(T array , int index) {
        return ArrayOperation.isAbove(array , index) ? false : Array.get(array , index) == null;
    }

    /**
     * create a {@link Iterable} object for {@link ArrayWrapper}
     * 
     * @param array the type of array
     * @param <T> type of array item
     * @return a {@link Iterable}
     */
    public static <T> Iterable<T> iterable(ArrayWrapper array) {
        return () -> new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return array.capacity() > this.index;
            }

            @SuppressWarnings("unchecked")
            @Override
            public T next() {
                return (T) array.get(this.index++);
            }
        };
    }

    /**
     * create a {@link Iterable} object for T[]
     * 
     * @param array input array
     * @param <T> array type
     * @return a {@link Iterable}
     */
    public static <T> Iterable<T> iterable(final T[] array) {
        return () -> new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return array.length > this.index;
            }

            @Override
            public T next() {
                return array[this.index++];
            }
        };
    }

    /**
     * find out the last time location of item's
     * 
     * @param array the array which is processed
     * @param item the item which should be found
     * @return if can't find out anything then return -1
     */
    public static int lastIndexOf(ArrayWrapper array , Object item) {
        for (int i = array.length() - 1 ; i >= array.position() ; i--)
            if (Objects.equals(array.get(i) , item)) return i;
        return -1;
    }

    /**
     * find out the last time location of item's
     * 
     * @param array the array which is processed
     * @param start the start position of array
     * @param end the end position of array(exclude)
     * @param item the item which should be found
     * @param <T> the type of array
     * @return if can't find out anything then return -1
     */
    public static <T> int lastIndexOf(T array , int start , int end , Object item) {
        ArrayWrapper arrayWrapper = ArrayWrapper.wrap(array);
        arrayWrapper.position(start);
        arrayWrapper.limit(end);
        return ArrayOperation.lastIndexOf(arrayWrapper , item);
    }

    /**
     * find out the last time location of item's
     * 
     * @param array
     *            the array which is processed
     * @param item
     *            The item which should be found
     * @param <T> the type of array
     * @return if can't find out anything then return -1
     */
    public static <T> int lastIndexOf(T array , Object item) {
        ArrayWrapper arrayWrapper = ArrayWrapper.wrap(array);
        return ArrayOperation.lastIndexOf(arrayWrapper , item);
    }

    /**
     * find out the last time location of target
     * 
     * @param array the array which is searched
     * @param start the start position of array
     * @param end the end position of array (exclude)
     * @param target the target which should be found from array
     * @param <T> the type of array
     * @return if can't find out anything then return -1
     */
    public static <T> int lastIndexOfAll(T array , int start , int end , T target) {
        ArrayWrapper arrayWrapper = ArrayWrapper.wrap(array);
        arrayWrapper.position(start);
        arrayWrapper.limit(end);
        return ArrayOperation.lastIndexOfAllWrapper(arrayWrapper , ArrayWrapper.wrap(target));
    }

    /**
     * find out the last time location of target
     * 
     * @param array the array which should be searched.
     * @param target the target which should be found out from array
     * @param <T> the type of the class
     * @return if can't find out anything from array, return -1
     */
    public static <T> int lastIndexOfAll(T array , T target) {
        return ArrayOperation.lastIndexOfAllWrapper(ArrayWrapper.wrap(array) , ArrayWrapper.wrap(target));
    }

    /**
     * find out the last time location of target
     * 
     * @param array the array which is searched
     * @param target the target which should be found from array
     * @param <T> the type of array
     * @return if can't find out anything then return -1
     */
    public static <T> int lastIndexOfAllWrapper(ArrayWrapper array , ArrayWrapper target) {
        if (array.length() == 0 || target.length() == 0) throw new IllegalArgumentException("array or target can't empty!");
        if (target.length() > array.length()) return -1;
        int index = target.limit();
        int position = -1;
        LOOP: for (int i = array.limit() - 1 ; i >= array.position() ; i--) {
            final Object value = array.get(i);
            if (Objects.equals(value , target.get(--index))) {
                if (index == array.position()) {
                    position = i;
                    break LOOP;
                }
            } else if (Objects.equals(value , target.get(target.limit() - 1))) {
                index = target.limit() - 1;
                if (index == array.position()) {
                    position = i;
                    break LOOP;
                }
            } else {
                index = array.limit();
                position = -1;
            }
        }
        return position;
    }

    /**
     * Get a long array comparator
     * @return a long array comparator
     */
    public static Comparator<long[]> longArrayComparator() {
		return longArrayComparator(LongOperation.comparator());
	}

    /**
     * Use a long comparator in order to generate a long array comparator
     * @param longComparator a long array comparator
     * @return a long array comparator
     */
    public static Comparator<long[]> longArrayComparator(Comparator<Long> longComparator) {
		return (l, r) -> {
			return comparator(longComparator).compare(ArrayWrapper.wrap(l), ArrayWrapper.wrap(r));
		};
	}

    /**
     * Merge the Arrays.<br>
     * 
     * @param array_type
     *            the class type of the finally array.
     * @param arrays which should be merged
     * @param <T> the type of array
     * @return the finally array which contain all of information of arrays.
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> T mergeArray(Class<T> array_type , T... arrays) {
        int length = 0;
        for (T array : arrays)
            length += Array.getLength(array);
        T result = (T) Array.newInstance(array_type.getComponentType() , length);
        for (int i = 0 , point = 0 ; i < arrays.length ; point += Array.getLength(arrays[i]) , i++)
            System.arraycopy(arrays[i] , 0 , result , point , Array.getLength(arrays[i]));
        return result;
    }

    /**
     * Use a object comparator in order to generate a object array comparator
     * @param objComparator a object comparator
     * @return a object array comparator
     * @param <T> the type of the data
     */
    public static <T> Comparator<T[]> objArrayComparator(Comparator<T> objComparator) {
		return (l, r) -> comparator(objComparator).compare(ArrayWrapper.wrap(l), ArrayWrapper.wrap(r));
	}

    /**
     * Try to convert the array<br>
     * 
     * @param in input array
     * @param out output array
     * @param function do some operation
     * @see ArrayOperation#pack_unpack(Object, Class, Function)
     */
    public static final void pack_unpack(ArrayWrapper in , ArrayWrapper out , Function<Object , Object> function) {
        if (out.length() < in.length()) throw new IllegalArgumentException("out is too small");
        for (int i = in.position() ; i < in.length() ; i++)
            out.put(function.apply(in.get(i)) , i);
    }

    /**
     * Try to convert the array.<br>
     * 
     * @param in input array
     * @param outClazz output array type
     * @param <IN> Input array type
     * @param <OUT> Ouput array type
     * @return output array
     * @see ArrayOperation#pack_unpack(Object, Class, Function)
     */
    public static <IN , OUT> OUT pack_unpack(IN in , Class<OUT> outClazz) {
        return ArrayOperation.pack_unpack(in , outClazz , (Object input) -> input);
    }

    /**
     * Try to convert the array.<br>
     * eg.<br>
     * int[] -- Integer[]<br>
     * int[] -- long[]<br>
     * Take Note: long[] can't convert to int[].
     * It is low efficient. It need use a for loop copy array.
     * 
     * @param in input array
     * @param outClazz output array type
     * @param function do some operations
     * @param <IN> Input array type
     * @param <OUT> Output array type
     * @return output array
     */
    @SuppressWarnings("unchecked")
    public static final <IN , OUT> OUT pack_unpack(IN in , Class<OUT> outClazz , Function<Object , Object> function) {
        ArrayWrapper inWrapper = ArrayWrapper.wrap(in);
        ArrayWrapper outWrapper = ArrayWrapper.newInstance(outClazz , inWrapper.length());
        ArrayOperation.pack_unpack(inWrapper , outWrapper , function);
        return (OUT) outWrapper.array();
    }

    /**
     * Remove all of item which be included in array.
     * 
     * @param array
     *            The array is processed.
     * @param item The item which should be remove
     * @param <T> the type of array
     * @param <I> the type of member of array
     * @return a array which has been processed.
     */
    public static <T , I> T removeAllItem(T array , I item) {
        int index = -1;
        while ((index = ArrayOperation.indexOf(array , item)) != -1)
            array = ArrayOperation.removeItem(array , index);
        return array;
    }

    /**
     * Remove the first location which included the item in array.
     * 
     * @param array
     *            the array which is processed.
     * @param item the item which should be remove.
     * @param <T> the type of array
     * @param <I> the type of member of array
     * @return a array which has been processed.
     */
    public static <T , I> T removeFirstItem(T array , I item) {
        int index = ArrayOperation.indexOf(array , item);
        if (index >= 0) array = ArrayOperation.removeItem(array , index);
        return array;
    }

    /**
     * Remove the array[i] from the array.
     * 
     * @param array
     *            the array is processed
     * @param i the location which should be removed.
     * @param <T> the type of array
     * @return a array which has been processed.
     */
    public static <T> T removeItem(T array , int i) {
        return ArrayOperation.removeItem(array , i , i + 1);
    }

    /**
     * Remove the array from start to end. the end is not include.
     * 
     * @param array the array is processed
     * @param start start index
     * @param end end index
     * @param <T> the type of array
     * @return a array which has been processed.
     */
    @SuppressWarnings("unchecked")
    public static <T> T removeItem(T array , int start , int end) {
        if (start < 0 || end < 0 || end <= start) throw new IllegalArgumentException("The argument that start or end is not right.");
        if (start >= Array.getLength(array)) return array;

        T head = start == 0 ? ArrayOperation.splitArray(array , end , Array.getLength(array)) : ArrayOperation.splitArray(array , 0 , start);
        T tail = start == 0 ? (T) Array.newInstance(array.getClass().getComponentType() , 0) : ArrayOperation.splitArray(array , end , Array.getLength(array));
        array = ArrayOperation.mergeArray((Class<T>) array.getClass() , head , tail);

        return array;
    }

    /**
     * inject a new value in {@link ArrayWrapper}
     * <pre> {@code
     * ArrayWrapper intArray = ArrayWrapper.wrap(new int[]{1, 2, 3});
     * int[] array = (int[]) injectItem(intArray, 4, 1);
     * }</pre>
     * As show in the example, the array will be {1, 4, 2, 3}.
     * @param arrayWrapper a {@link ArrayWrapper}
     * @param value the new value
     * @param index the position of the {@link ArrayWrapper}
     * @return the new array
     */
    public static Object injectItem(ArrayWrapper arrayWrapper, Object value, int index) {
        if(index < 0 || index >= arrayWrapper.length()) throw new ArrayIndexOutOfBoundsException();
        final Object array = arrayWrapper.array();
        final Class<?> componentType = array.getClass().getComponentType();
        Object head = Array.newInstance(componentType, index);
        final int tailLength = arrayWrapper.length() - index;
        Object tail = Array.newInstance(componentType, tailLength);
        Object rest = Array.newInstance(componentType, arrayWrapper.length() + 1);

        System.arraycopy(array, 0, head, 0, index);
        System.arraycopy(array, index, tail, 0, tailLength);

        System.arraycopy(head, 0, rest, 0, index);
        ArrayWrapper.wrap(rest).put(value, index);
        System.arraycopy(tail, 0, rest, index + 1, tailLength);

        return rest;
    }

    /**
     * Remove the last location of item from array.
     * 
     * @param array
     *            the array which is processed.
     * @param item the item which should be removed.
     * @param <T> the type of array
     * @param <I> the type of member of array
     * @return a array which has been processed.
     */
    public static <T , I> T removeLastItem(T array , I item) {
        int index = ArrayOperation.lastIndexOf(array , item);
        if (index >= 0) array = ArrayOperation.removeItem(array , index);
        return array;
    }

    /**
     * Get a short array comparator
     * @return a short array comparator
     */
    public static Comparator<short[]> shortArrayComparator() {
		return shortArrayComparator(ShortOperation.comparator());
	}

    /**
     * Use a short comparator in order to generate a short array comparator
     * @param shortComparator a short comparator
     * @return a short array comparator
     */
    public static Comparator<short[]> shortArrayComparator(Comparator<Short> shortComparator) {
		return (l, r) -> comparator(shortComparator).compare(ArrayWrapper.wrap(l), ArrayWrapper.wrap(r));
	}

    /**
     * Get a small array from the array.
     * 
     * @param array
     *            the array is processed
     * @param from
     *            the start of location of array
     * @param end the end of location of array. NOTE: never include itself.
     * @param <T> the type of array
     * @return a array which has been processed.
     */
    @SuppressWarnings("unchecked")
    public static <T> T splitArray(T array , int from , int end) {
        if (!array.getClass().isArray()) throw new IllegalArgumentException("The array must be a array");
        T result = (T) Array.newInstance(array.getClass().getComponentType() , end - from);
        System.arraycopy(array , from , result , 0 , end - from);
        return result;
    }

    /**
     * Get a byte stream
     * @param bs a byte array
     * @return a byte stream
     */
    public static Stream<Byte> stream(byte[] bs) {
		return StreamSupport.stream(SpliteratorOperation.spliterator(
				bs, Spliterator.ORDERED | Spliterator.IMMUTABLE), false);
	}

    /**
     * Get a byte stream
     * @param bs a byte array
     * @param startInclusive the start point of byte array (include)
     * @param endExclusive the end point of byte array (exclude)
     * @return a byte stream
     */
    public static Stream<Byte> stream(byte[] bs, int startInclusive, int endExclusive) {
		return StreamSupport.stream(SpliteratorOperation.spliterator(
				bs, startInclusive, endExclusive, Spliterator.ORDERED | Spliterator.IMMUTABLE), false);
	}

    /**
     * Get a char stream
     * @param cs a char array
     * @return a byte stream
     */
    public static Stream<Character> stream(char[] cs) {
		return StreamSupport.stream(SpliteratorOperation.spliterator(
				cs, Spliterator.ORDERED | Spliterator.IMMUTABLE), false);
	}

    /**
     * Get a char stream
     * @param cs the char array
     * @param startInclusive the start position of the char array (include)
     * @param endExclusive the end position of the char array (exlude)
     * @return a char stream
     */
    public static Stream<Character> stream(char[] cs, int startInclusive, int endExclusive) {
		return StreamSupport.stream(SpliteratorOperation.spliterator(
				cs, startInclusive, endExclusive, Spliterator.ORDERED | Spliterator.IMMUTABLE), false);
	}

    /**
     * Get a double stream
     * @param array a double array
     * @return a double stream
     */
    public static Stream<Double> stream(double[] array) {
    	return StreamOperation.valueOf(Arrays.stream(array));
    }

    /**
     * Get a double stream
     * @param array a double array
     * @param startInclusive the start position of the array (include)
     * @param endExclusive the end position of the array (exclude)
     * @return a double stream
     */
    public static Stream<Double> stream(double[] array, int startInclusive, int endExclusive) {
    	return StreamOperation.valueOf(Arrays.stream(array, startInclusive, endExclusive));
    }

    /**
     * Get a float stream
     * @param array a float array
     * @return a float stream
     */
    public static Stream<Float> stream(float[] array) {
		return StreamSupport.stream(SpliteratorOperation.spliterator(
				array, Spliterator.ORDERED | Spliterator.IMMUTABLE), false);
	}

    /**
     * Get a float stream from a float array
     * @param array a float array
     * @param startInclusive the start position of the array (include)
     * @param endExclusive the end position of the array (exclude)
     * @return a float stream
     */
    public static Stream<Float> stream(float[] array, int startInclusive, int endExclusive) {
    	return StreamSupport.stream(SpliteratorOperation.spliterator(
    			array, startInclusive, endExclusive, Spliterator.ORDERED | Spliterator.IMMUTABLE), false);
    }

    /**
     * Get a int stream
     * @param array a int array
     * @return a int stream
     */
    public static Stream<Integer> stream(int[] array) {
    	return StreamOperation.valueOf(Arrays.stream(array));
    }

    /**
     * Get a int stream
     * @param array a int array
     * @param startInclusive the start position of the array (include)
     * @param endExclusive the end position of the array (exclude)
     * @return a int stream
     */
    public static Stream<Integer> stream(int[] array, int startInclusive, int endExclusive) {
    	return StreamOperation.valueOf(Arrays.stream(array, startInclusive, endExclusive));
    }

    /**
     * Get a long stream
     * @param array a long array
     * @return a long stream
     */
    public static Stream<Long> stream(long[] array) {
    	return StreamOperation.valueOf(Arrays.stream(array));
    }

    /**
     * Get a long stream
     * @param array a long array
     * @param startInclusive the start position of the array (include)
     * @param endExclusive the end position of the array (exclude)
     * @return a long stream
     */
    public static Stream<Long> stream(long[] array, int startInclusive, int endExclusive) {
    	return StreamOperation.valueOf(Arrays.stream(array, startInclusive, endExclusive));
    }

    /**
     * Get a short stream
     * @param array a short array
     * @return a short stream
     */
    public static Stream<Short> stream(short[] array) {
    	return StreamSupport.stream(SpliteratorOperation.spliterator(
				array, Spliterator.ORDERED | Spliterator.IMMUTABLE), false);
	}

    /**
     * Get a short stream
     * @param array a short array
     * @param startInclusive the start position of the array (include)
     * @param endExclusive the end position of the array (exclude)
     * @return a short stream
     */
    public static Stream<Short> stream(short[] array, int startInclusive, int endExclusive) {
    	return StreamSupport.stream(SpliteratorOperation.spliterator(
    			array, startInclusive, endExclusive, Spliterator.ORDERED | Spliterator.IMMUTABLE), false);
    }

	/**
     * It is a easy way could create a array.
     * 
     * @param clazz subclass of array
     * @param targets the method what you want to return
     * @param <T> the type of array
     * @return just return the targets
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> T[] toArray(Class<T> clazz , T... targets) {
        if (targets == null) return (T[]) Array.newInstance(clazz , 0);
        return targets;
    }

    /**
     * Looks like the python's range method.
     * <pre>{@code
     * int[] array = range(0, 10, 2);
     * }</pre>
     * the array will be {0, 2, 4, 6, 8}.
     * @param start start position (include)
     * @param end end position (exclude)
     * @param step the add step
     * @return a int array
     */
    public static int[] range(int start, int end, int step){
        int len = (end - start) / step;
        if((end - start) % step != 0) len++;
        int[] array = new int[len];
        for(int i=start,j=0; i < end; i+=step, j++){
            array[j] = i;
        }
        return array;
    }

	private ArrayOperation() {
        throw new AssertionError("No " + ArrayOperation.class + "  instances for you!");
    }
}
