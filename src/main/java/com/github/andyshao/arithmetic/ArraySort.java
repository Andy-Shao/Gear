package com.github.andyshao.arithmetic;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Random;

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
     * insert sort(插入排序)
     * 
     * @param data array
     * @param comparator {@link Comparator}
     * @param start start positon
     * @param end end position
     * @param <DATA> data
     * @return if the data is null then return it.
     */
    public static final <DATA> DATA[] issort(final DATA[] data , int start , int end , Comparator<DATA> comparator) {
        if (data == null) return data;
        //Repeatedly insert a key element among the sorted elements.
        for (int i = start + 1 ; i <= end ; i++) {
            DATA temp = data[i];
            int j = i - 1;

            //Determine the position at which to insert the key element.
            for ( ; j >= 0 && comparator.compare(data[j] , temp) > 0 ; j--)
                data[j + 1] = data[j];
            data[j + 1] = temp;
        }
        return data;
    }

    @SuppressWarnings("unchecked")
    static final <DATA> DATA[] merge(DATA[] data , int start , int division , int end , Comparator<DATA> comparator) {
        DATA[] temp;
        int leftPos , rightPos , tempPos;

        //initialize the counters used in merging.
        leftPos = start;
        rightPos = division + 1;
        tempPos = 0;
        temp = (DATA[]) Array.newInstance(data.getClass().getComponentType() , end - start + 1);

        //Continue while either division has elements to merge.
        FIRST: while (leftPos <= division || rightPos <= end) {
            if (leftPos > division) {
                //The left division has no more elements to merge.
                while (rightPos <= end)
                    temp[tempPos++] = data[rightPos++];
                continue FIRST;
            } else if (rightPos > end) {
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
        System.arraycopy(temp , 0 , data , start , end - start + 1);

        return data;
    }

    /**
     * Merge sort(归并排序)
     * 
     * @param data array which should be sorted
     * @param start start position
     * @param end end position
     * @param comparator {@link Comparator}
     * @param <DATA> data type
     * @return the array which has been sorted
     */
    public static final <DATA> DATA[] mgsort(DATA[] data , int start , int end , Comparator<DATA> comparator) {
        //Stop the recursion when no more divisions can be made.
        if (start < end) {
            int division;
            //Determine where to divide the elements.
            division = (int) ((start + end - 1) / 2);

            //Recursively sort the two divisions.
            mgsort(data , start , division , comparator);
            mgsort(data , division + 1 , end , comparator);

            //Merge the two sorted divisions into a single sorted set.
            merge(data , start , division , end , comparator);
        }

        return data;
    }

    static final <DATA> int partition(DATA[] data , int start , int end , Comparator<DATA> comparator) {
        Integer[] r = new Integer[3];
        final Random random = new Random();

        //Use the median-of-three method to find the partition value.
        r[0] = random.ints(start , end).findFirst().getAsInt();
        r[1] = random.ints(start , end).findFirst().getAsInt();
        r[2] = random.ints(start , end).findFirst().getAsInt();
        ArraySort.<Integer> issort(r , 0 , r.length - 1 , (i1 , i2) -> {
            return Integer.compare(i1 , i2);
        });
        DATA pval = data[r[1]];

        //Create two partitions around the partition value.
        start--;
        end++;

        while (true) {
            //Move left until an element is found in the wrong partition.
            do
                end--;
            while (comparator.compare(data[end] , pval) > 0);

            //Move right until an element is found in the wrong partition.
            do
                start++;
            while (comparator.compare(data[start] , pval) < 0);

            if (start >= end) //Stop partitioning when the left and right counters cross.
            break;
            else {
                //Swap the elements now under the left and right counters.
                DATA temp = data[start];
                data[start] = data[end];
                data[end] = temp;
            }
        }

        return end;
    }

    /**
     * quick sort(快速排序)
     * 
     * @param data array
     * @param start the begin of sort
     * @param end the end of sort
     * @param comparator {@link Comparator}
     * @param <DATA> data type
     * @return the array which is sorted.
     * @throws ArraySortException when the sort has error.
     */
    public static final <DATA> DATA[] qksort(final DATA[] data , int start , int end , Comparator<DATA> comparator) {
        //Stop teh recursion when it is not possible to partition further.
        while (start < end) {
            int index = 0;
            //Determine where to partition the elements.
            if ((index = ArraySort.partition(data , start , end , comparator)) < 0) throw new ArraySortException();

            //Recursively sort the left partition.
            ArraySort.qksort(data , start , index , comparator);

            //Iterate and sort the right partition.
            start = index + 1;
        }
        return data;
    }

    private ArraySort() {
        throw new AssertionError("No com.github.andyshao.arithmetic.ArraySort instances for you!");
    }
}
