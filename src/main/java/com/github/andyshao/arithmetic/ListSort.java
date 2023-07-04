package com.github.andyshao.arithmetic;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jul 29, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ListSort {
    /**
     * insert sort(插入排序)
     * 
     * @param data data
     * @param comparator {@link Comparator}
     * @param start start position(inclusive)
     * @param end end position(exclusive)
     * @param <E> data type
     * @return if the data is null then return it.
     * @throws IllegalArgumentException if start bigger than or equal end
     */
    public static final <E> List<E> issort(List<E> data , int start , int end , Comparator<E> comparator) {
        if (start > end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        for (int i = start + 1 ; i < end ; i++) {
            E temp = data.get(i);
            int j = i - 1;

            //Determine the position at which to insert the key element.
            for (; j >= start && comparator.compare(data.get(j) , temp) > 0 ; j--)
                data.set(j + 1 , data.get(j));
            data.set(j + 1 , temp);
        }
        return data;
    }

    static final <E> int partition(List<E> data , int start , int end , Comparator<E> comparator) {
        int[] r = new int[3];
        final Random random = new Random();
        int tail = end - 1;

        //Use the median-of-three method to find the partition value.
        r[0] = random.ints(start , tail).findFirst().getAsInt();
        r[1] = random.ints(start , tail).findFirst().getAsInt();
        r[2] = random.ints(start , tail).findFirst().getAsInt();
        ArraySort.<int[] , Integer> issort(r , 0 , r.length , (i1 , i2) -> Integer.compare(i1 , i2));
        E pval = data.get(r[1]);

        //Create two partitions around the partition value.
        start--;
        tail++;

        while (true) {
            //Move left until an element is found in the wrong partition.
            do
                tail--;
            while (comparator.compare(data.get(tail) , pval) > 0);

            //Move right until an element is found in the wrong partition.
            do
                start++;
            while (comparator.compare(data.get(start) , pval) < 0);

            if (start >= tail) //Stop partitioning when the left and right counters cross.
                break;
            else {
                //Swap the elements now under the left and right counters.
                E temp = data.set(start , data.get(tail));
                data.set(tail , temp);
            }
        }

        return tail;
    }

    /**
     * gk sort
     * @param data a unordered list
     * @param start start position (inclusive)
     * @param end end position (exclusive)
     * @param comparator {@link Comparator}
     * @return a sorted list
     * @param <E> data type
     */
    public static final <E> List<E> qksort(final List<E> data , int start , int end , Comparator<E> comparator) {
        if (start >= end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        //Stop the recursion when it is not possible to partition further.
        while (start < end - 1) {
            int index = 0;
            //Determine where to partition the elements.
            if ((index = ListSort.partition(data , start , end , comparator)) < 0) throw new SortException();

            //Recursively sort the left partition.
            ListSort.qksort(data , start , ++index , comparator);

            //Iterate and sort the right partition.
            start = index;
        }
        return data;
    }

    private ListSort() {
        throw new AssertionError("No " + ListSort.class + " instances for you!");
    }
}
