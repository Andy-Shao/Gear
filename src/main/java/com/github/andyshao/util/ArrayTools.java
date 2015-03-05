package com.github.andyshao.util;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.function.Function;

import com.github.andyshao.convert.Convert;

/**
 * Some tools of {@link Array}<br>
 * <p style="color:orange;">
 * At least JDK1.8
 * </p>
 * 
 * @author Andy
 */
public final class ArrayTools {
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
    public static <K , V> Map<K , V> convertToMap(
        Convert<Object , K> convertK , Convert<Object , V> convertV , Map<K , V> map , Object[]... arrays) {
        for (Object[] array : arrays)
            map.put(convertK.convert(array[0]) , convertV.convert(array[1]));
        return map;
    }

    /**
     * find out the location of first item.
     * 
     * @param array
     *            the array which is processed
     * @param item The item which should be removed
     * @param <T> the type of array
     * @return if can't find out anything then return -1
     */
    public static <T> int findFirstItem(T array , Object item) {
        for (int i = 0 ; i < Array.getLength(array) ; i++)
            if (Array.get(array , i).equals(item)) return i;
        return -1;
    }

    /**
     * find out the location of last item.
     * 
     * @param array
     *            the array which is processed
     * @param item
     *            The item which should be removed
     * @param <T> the type of array
     * @return if can't find out anything then return -1
     */
    public static <T> int findLastItem(T array , Object item) {
        for (int i = Array.getLength(array) - 1 ; i >= 0 ; i--)
            if (Array.get(array , i).equals(item)) return i;
        return -1;
    }

    /**
     * 
     * @param array the array which is processed.
     * @param <T> the type of array
     * @return A flip array.
     */
    @SuppressWarnings("unchecked")
    public static <T> T flipArray(T array) {
        int length = Array.getLength(array);
        if (length == 0) return array;

        T temp = (T) Array.newInstance(array.getClass().getComponentType() , length);
        for (int i = length - 1 , b = 0 ; i >= 0 ; i-- , b++)
            Array.set(temp , b , Array.get(array , i));
        return temp;
    }

    /**
     * 
     * @param array the array which is processed.
     * @param index the address of value
     * @param nullDefault the default value
     * @param <T> the type of array
     * @return the value which index is index.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValue(T array , int index , T nullDefault) {
        if (ArrayTools.isAbove(array , index)) return nullDefault;
        return (T) Array.get(array , index);
    }

    /**
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
     * 
     * @param array the array which is processed.
     * @param index the address of value
     * @param <T> the type of array
     * @return if the index doesn't above array and the value is null , return
     *         true
     */
    public static <T> boolean isEmpty(T array , int index) {
        return ArrayTools.isAbove(array , index) ? false : Array.get(array , index) == null;
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
     * Try to convert the array.<br>
     * eg.<br>
     * int[] -- Integer[]<br>
     * int[] -- long[]<br>
     * Take Note: long[] can't convert to int[].
     * It is low efficient. It need use a for loop copy array.
     * 
     * @param in input array
     * @param outClazz output array type
     * @param <IN> Input array type
     * @param <OUT> Ouput array type
     * @return output array
     */
    public static <IN , OUT> OUT pack_unpack(IN in , Class<OUT> outClazz) {
        return ArrayTools.pack_unpack(in , outClazz , (Object input) -> {
            return input;
        });
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
     * @param <OUT> Ouput array type
     * @return output array
     */
    @SuppressWarnings("unchecked")
    public static final <IN , OUT> OUT pack_unpack(IN in , Class<OUT> outClazz , Function<Object , Object> function) {
        OUT result = (OUT) Array.newInstance(outClazz.getComponentType() , Array.getLength(in));
        for (int i = 0 ; i < Array.getLength(in) ; i++)
            Array.set(result , i , function.apply(Array.get(in , i)));

        return result;
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
        ARRAY: for (int i = 0 ; i < Array.getLength(array) ; i++)
            if (Array.get(array , i).equals(item)) {
                array = ArrayTools.removeItem(array , i);
                continue ARRAY;
            }
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
        int index = ArrayTools.findFirstItem(array , item);
        if (index >= 0) array = ArrayTools.removeItem(array , index);
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
        return ArrayTools.removeItem(array , i , i + 1);
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
        if (start < 0 || end < 0 || end <= start) throw new IllegalArgumentException(
            "The argument that start or end is not right.");
        if (start >= Array.getLength(array)) return array;

        T head =
            start == 0 ? ArrayTools.splitArray(array , end , Array.getLength(array)) : ArrayTools.splitArray(array , 0 ,
                start);
        T tail =
            start == 0 ? (T) Array.newInstance(array.getClass().getComponentType() , 0) : ArrayTools.splitArray(array ,
                end , Array.getLength(array));
        array = ArrayTools.mergeArray((Class<T>) array.getClass() , head , tail);

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
        int index = ArrayTools.findLastItem(array , item);
        if (index >= 0) array = ArrayTools.removeItem(array , index);
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
     * @param targets the method what you want to return
     * @param <T> the type of array
     * @return just return the targets
     */
    @SafeVarargs
    public static <T> T[] toArray(T... targets) {
        return targets;
    }

    private ArrayTools() {
        throw new AssertionError("No ArrayTools instances for you!");
    }
}
