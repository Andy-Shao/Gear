package com.github.andyshao.arithmetic;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import com.github.andyshao.convert.Convert;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 28, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ArraySort {

    /**
     * count sort(计数排序)
     * 
     * @param data array
     * @param convert calculate the data to a integer
     * @param start start position(inclusive)
     * @param end end position(exclusive)
     * @param max the max value(k) of array clip which from start side to end
     *            side but never
     *            include the end side. the max = k + 1
     * @param <DATA> array type
     * @return the array which has been sorted.
     * @throws IllegalArgumentException if start bigger than or equal end.
     */
    @SuppressWarnings("unchecked")
    public static final <DATA> DATA[] ctsort(
        DATA[] data , Convert<DATA , Integer> convert , int start , int end , int max) {
        if (start > end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        int[] counts = new int[max];
        Arrays.fill(counts , 0);
        //Count the occurrences of each element.
        for (int i = start ; i < end ; i++) {
            Integer index = convert.convert(data[i]);
            counts[index] = counts[index] + 1;
        }
        //Adjust each count to reflect the counts before it.
        for (int i = 1 ; i < max ; i++)
            counts[i] = counts[i] + counts[i - 1];

        DATA[] temp = (DATA[]) Array.newInstance(data.getClass().getComponentType() , data.length);
        //Use the counts to position each element where it belongs.
        for (int i = end - 1 ; i >= start ; i--) {
            int index = convert.convert(data[i]);
            temp[counts[index] - 1 + start] = data[i];
            counts[index] = counts[index] - 1;
        }

        //Prepare to pass back the sorted data.
        System.arraycopy(temp , start , data , start , end - start);

        return data;
    }

    /**
     * insert sort(插入排序)
     * 
     * @param data array
     * @param comparator {@link Comparator}
     * @param start start position(inclusive)
     * @param end end position(exclusive)
     * @param <DATA> data
     * @return if the data is null then return it.
     * @throws IllegalArgumentException if start bigger than or equal end
     */
    public static final <DATA> DATA[] issort(final DATA[] data , int start , int end , Comparator<DATA> comparator) {
        if (start > end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        //Repeatedly insert a key element among the sorted elements.
        for (int i = start + 1 ; i < end ; i++) {
            DATA temp = data[i];
            int j = i - 1;

            //Determine the position at which to insert the key element.
            for ( ; j >= start && comparator.compare(data[j] , temp) > 0 ; j--)
                data[j + 1] = data[j];
            data[j + 1] = temp;
        }
        return data;
    }

    /**
     * 
     * @param data array
     * @param start start position(inclusive)
     * @param division division position(inclusive)
     * @param end end position(exclusive)
     * @param comparator {@link Comparator}
     * @param <DATA>
     * @return the array which has been merged
     */
    @SuppressWarnings("unchecked")
    static final <DATA> DATA[] merge(DATA[] data , int start , int division , int end , Comparator<DATA> comparator) {
        //initialize the counters used in merging.
        int leftPos = start;
        int rightPos = division + 1;
        int tempPos = 0;
        DATA[] temp = (DATA[]) Array.newInstance(data.getClass().getComponentType() , end - start);

        //Continue while either division has elements to merge.
        FIRST: while (leftPos <= division || rightPos < end) {
            if (leftPos > division) {
                //The left division has no more elements to merge.
                while (rightPos < end)
                    temp[tempPos++] = data[rightPos++];
                continue FIRST;
            } else if (rightPos >= end) {
                //The right division has no more elements to merge.
                while (leftPos <= division)
                    temp[tempPos++] = data[leftPos++];
                continue FIRST;
            }

            //Append the next ordered element to the merged elements.
            if (comparator.compare(data[leftPos] , data[rightPos]) < 0) temp[tempPos++] = data[leftPos++];
            else temp[tempPos++] = data[rightPos++];
        }

        //Prepare to pass back the merged data.
        System.arraycopy(temp , 0 , data , start , end - start);

        return data;
    }

    /**
     * Merge sort(归并排序)
     * 
     * @param data array which should be sorted
     * @param start start position(inclusive)
     * @param end end position(exclusive)
     * @param comparator {@link Comparator}
     * @param <DATA> data type
     * @return the array which has been sorted
     * @throws IllegalArgumentException if start bigger than or equal end
     */
    public static final <DATA> DATA[] mgsort(DATA[] data , int start , int end , Comparator<DATA> comparator) {
        if (start > end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        //Stop the recursion when no more divisions can be made.
        if (start < end - 1) {
            int division;
            //Determine where to divide the elements.
            division = (start + end) / 2;

            //Recursively sort the two divisions.
            ArraySort.mgsort(data , start , division , comparator);
            ArraySort.mgsort(data , division , end , comparator);

            //Merge the two sorted divisions into a single sorted set.
            ArraySort.merge(data , start , division - 1 , end , comparator);
        }

        return data;
    }

    static final <DATA> int partition(DATA[] data , int start , int end , Comparator<DATA> comparator) {
        Integer[] r = new Integer[3];
        final Random random = new Random();
        int tail = end - 1;

        //Use the median-of-three method to find the partition value.
        r[0] = random.ints(start , tail).findFirst().getAsInt();
        r[1] = random.ints(start , tail).findFirst().getAsInt();
        r[2] = random.ints(start , tail).findFirst().getAsInt();
        ArraySort.<Integer> issort(r , 0 , r.length , (i1 , i2) -> {
            return Integer.compare(i1 , i2);
        });
        DATA pval = data[r[1]];

        //Create two partitions around the partition value.
        start--;
        tail++;

        while (true) {
            //Move left until an element is found in the wrong partition.
            do
                tail--;
            while (comparator.compare(data[tail] , pval) > 0);

            //Move right until an element is found in the wrong partition.
            do
                start++;
            while (comparator.compare(data[start] , pval) < 0);

            if (start >= tail) //Stop partitioning when the left and right counters cross.
            break;
            else {
                //Swap the elements now under the left and right counters.
                DATA temp = data[start];
                data[start] = data[tail];
                data[tail] = temp;
            }
        }

        return tail;
    }

    /**
     * quick sort(快速排序)
     * 
     * @param data array
     * @param start the begin of sort(inclusive)
     * @param end the end of sort(exclusive)
     * @param comparator {@link Comparator}
     * @param <DATA> data type
     * @return the array which is sorted.
     * @throws ArraySortException when the sort has error.
     * @throws IllegalArgumentException if start bigger than or equal end
     */
    public static final <DATA> DATA[] qksort(final DATA[] data , int start , int end , Comparator<DATA> comparator) {
        if (start >= end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        //Stop the recursion when it is not possible to partition further.
        while (start < end - 1) {
            int index = 0;
            //Determine where to partition the elements.
            if ((index = ArraySort.partition(data , start , end , comparator)) < 0) throw new ArraySortException();

            //Recursively sort the left partition.
            ArraySort.qksort(data , start , ++index , comparator);

            //Iterate and sort the right partition.
            start = index;
        }
        return data;
    }

    private ArraySort() {
        throw new AssertionError("No com.github.andyshao.arithmetic.ArraySort instances for you!");
    }
}
