package com.github.andyshao.reflect;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Function;

import com.github.andyshao.lang.ArrayWrapper;
import com.github.andyshao.lang.Convert;

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
    public static <T> T backup(T array) {
        return ArrayOperation.splitArray(array , 0 , Array.getLength(array));
    }
    
    @SafeVarargs
	public static <T> Set<T> asSet(Set<T> set, T...ts){
    	for(T t : ts) set.add(t);
    	return set;
    }
    
    @SafeVarargs
	public static <T> List<T> asList(List<T> list, T...ts){
    	for(T t : ts) list.add(t);
    	return list;
    }
    
    @SafeVarargs
	public static <T> Queue<T> asQueue(Queue<T> queue, T...ts) {
    	for(T t : ts) queue.offer(t);
    	return queue;
    }
    
    @SafeVarargs
	public static <T> Queue<T> asQueue(T...ts) {
    	return asQueue(new ArrayBlockingQueue<T>(ts.length), ts);
    }
    
    @SafeVarargs
	public static <T> Set<T> asSet(T...ts) {
    	return asSet(new HashSet<>(), ts);
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

    private ArrayOperation() {
        throw new AssertionError("No " + ArrayOperation.class + "  instances for you!");
    }
}
