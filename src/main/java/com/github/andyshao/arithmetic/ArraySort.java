package com.github.andyshao.arithmetic;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import com.github.andyshao.lang.ArrayWrapper;
import com.github.andyshao.lang.Convert;

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
     * count sort(计数排序)<br>
     * 速度比快速排序快，但是占用空间巨大.稳定的排序能使具有相同
     * 数值的元素有相同的顺序.
     * 
     * @param array array
     * @param convert calculate the data to a integer
     * @param start start position(inclusive)
     * @param end end position(exclusive)
     * @param max the max value(k) of array clip which from start side to end
     *            side but never
     *            include the end side. the max = k + 1
     * @param <DATA> data type
     * @param <ARRAY> array type
     * @return the array which has been sorted.
     * @throws IllegalArgumentException if start bigger than or equal end.
     */
    @SuppressWarnings("unchecked")
    public static final <ARRAY , DATA> ARRAY ctsort(ARRAY array , Convert<DATA , Integer> convert , int start , int end , int max) {
        final ArrayWrapper arrayWrapper = ArrayWrapper.wrap(array);
        return (ARRAY) ArraySort.ctsort(arrayWrapper , convert , start , end , max).array();
    }

    @SuppressWarnings("unchecked")
    public static final <DATA> ArrayWrapper ctsort(ArrayWrapper array , Convert<DATA , Integer> convert , int start , int end , int max) {
        if (start > end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        int[] counts = new int[max];
        Arrays.fill(counts , 0);
        //Count the occurrences of each element.
        for (int i = start ; i < end ; i++) {
            Integer index = convert.convert((DATA) array.get(i));
            counts[index] = counts[index] + 1;
        }
        //Adjust each count to reflect the counts before it.
        for (int i = 1 ; i < max ; i++)
            counts[i] = counts[i] + counts[i - 1];

        Object temp = Array.newInstance(array.array().getClass().getComponentType() , array.length());
        ArrayWrapper tempWrapper = ArrayWrapper.wrap(temp);
        //Use the counts to position each element where it belongs.
        for (int i = end - 1 ; i >= start ; i--) {
            int index = convert.convert((DATA) array.get(i));
            tempWrapper.put(array.get(i) , counts[index] - 1 + start);
            counts[index] = counts[index] - 1;
        }

        //Prepare to pass back the sorted data.
        System.arraycopy(temp , start , array.array() , start , end - start);

        return array;
    }

    /**
     * insert sort(插入排序)
     * 
     * @param data array
     * @param comparator {@link Comparator}
     * @param start start position(inclusive)
     * @param end end position(exclusive)
     * @param <ARRAY> array type
     * @param <DATA> data type
     * @return if the data is null then return it.
     * @throws IllegalArgumentException if start bigger than or equal end
     */
    @SuppressWarnings("unchecked")
    public static final <ARRAY , DATA> ARRAY issort(final ARRAY data , int start , int end , Comparator<DATA> comparator) {
        final ArrayWrapper arrayWrapper = ArrayWrapper.wrap(data);
        return (ARRAY) ArraySort.issort(arrayWrapper , start , end , comparator).array();
    }

    @SuppressWarnings("unchecked")
    public static final <DATA> ArrayWrapper issort(final ArrayWrapper data , int start , int end , Comparator<DATA> comparator) {
        if (start > end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        //Repeatedly insert a key element among the sorted elements.
        for (int i = start + 1 ; i < end ; i++) {
            DATA temp = (DATA) data.get(i);
            int j = i - 1;

            //Determine the position at which to insert the key element.
            for (; j >= start && comparator.compare((DATA) data.get(j) , temp) > 0 ; j--)
                data.put(data.get(j) , j + 1);
            data.put(temp , j + 1);
        }
        return data;
    }

    @SuppressWarnings("unchecked")
    static final <DATA> ArrayWrapper merge(ArrayWrapper array , int start , int division , int end , Comparator<DATA> comparator) {
        //initialize the counters used in merging.
        int leftPos = start;
        int rightPos = division + 1;
        int tempPos = 0;
        Object temp = Array.newInstance(array.array().getClass().getComponentType() , end - start);
        ArrayWrapper tempWrapper = ArrayWrapper.wrap(temp);

        //Continue while either division has elements to merge.
        FIRST: while (leftPos <= division || rightPos < end) {
            if (leftPos > division) {
                //The left division has no more elements to merge.
                while (rightPos < end)
                    tempWrapper.put(array.get(rightPos++) , tempPos++);
                continue FIRST;
            } else if (rightPos >= end) {
                //The right division has no more elements to merge.
                while (leftPos <= division)
                    tempWrapper.put(array.get(leftPos++) , tempPos++);
                continue FIRST;
            }

            //Append the next ordered element to the merged elements.
            if (comparator.compare((DATA) array.get(leftPos) , (DATA) array.get(rightPos)) < 0) tempWrapper.put(array.get(leftPos++) , tempPos++);
            else tempWrapper.put(array.get(rightPos++) , tempPos++);
        }

        //Prepare to pass back the merged data.
        System.arraycopy(temp , 0 , array.array() , start , end - start);

        return array;
    }

    public static final <DATA> ArrayWrapper mgsort(ArrayWrapper data , int start , int end , Comparator<DATA> comparator) {
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

    /**
     * Merge sort(归并排序)<br>
     * 归并排序在所有情况下都能达到快速排序的平均性能.必须要有两倍于无序数据集
     * 的空间来运行算法.
     * 
     * @param data array which should be sorted
     * @param start start position(inclusive)
     * @param end end position(exclusive)
     * @param comparator {@link Comparator}
     * @param <DATA> data type
     * @param <ARRAY> array type
     * @return the array which has been sorted
     * @throws IllegalArgumentException if start bigger than or equal end
     */
    @SuppressWarnings("unchecked")
    public static final <ARRAY , DATA> ARRAY mgsort(ARRAY data , int start , int end , Comparator<DATA> comparator) {
        final ArrayWrapper arrayWrapper = ArrayWrapper.wrap(data);
        return (ARRAY) mgsort(arrayWrapper , start , end , comparator).array();
    }

    @SuppressWarnings("unchecked")
    static final <DATA> int partition(ArrayWrapper array , int start , int end , Comparator<DATA> comparator) {
        int[] r = new int[3];
        final Random random = new Random();
        int tail = end - 1;

        //Use the median-of-three method to find the partition value.
        r[0] = random.ints(start , tail).findFirst().getAsInt();
        r[1] = random.ints(start , tail).findFirst().getAsInt();
        r[2] = random.ints(start , tail).findFirst().getAsInt();
        ArraySort.<int[] , Integer> issort(r , 0 , r.length , (i1 , i2) -> {
            return Integer.compare(i1 , i2);
        });
        DATA pval = (DATA) array.get(r[1]);

        //Create two partitions around the partition value.
        start--;
        tail++;

        while (true) {
            //Move left until an element is found in the wrong partition.
            do
                tail--;
            while (comparator.compare((DATA) array.get(tail) , pval) > 0);

            //Move right until an element is found in the wrong partition.
            do
                start++;
            while (comparator.compare((DATA) array.get(start) , pval) < 0);

            if (start >= tail) //Stop partitioning when the left and right counters cross.
                break;
            else {
                //Swap the elements now under the left and right counters.
                DATA temp = (DATA) array.get(start);
                array.put(array.get(tail) , start);
                array.put(temp , tail);
            }
        }

        return tail;
    }

    public static final <DATA> ArrayWrapper qksort(final ArrayWrapper array , int start , int end , Comparator<DATA> comparator) {
        if (start >= end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        //Stop the recursion when it is not possible to partition further.
        while (start < end - 1) {
            int index = 0;
            //Determine where to partition the elements.
            if ((index = ArraySort.partition(array , start , end , comparator)) < 0) throw new SortException();

            //Recursively sort the left partition.
            ArraySort.qksort(array , start , ++index , comparator);

            //Iterate and sort the right partition.
            start = index;
        }
        return array;
    }

    /**
     * quick sort(快速排序)
     * 
     * @param array array
     * @param start the begin of sort(inclusive)
     * @param end the end of sort(exclusive)
     * @param comparator {@link Comparator}
     * @param <DATA> data type
     * @param <ARRAY> array type
     * @return the array which is sorted.
     * @throws SortException when the sort has error.
     * @throws IllegalArgumentException if start bigger than or equal end
     */
    @SuppressWarnings("unchecked")
    public static final <ARRAY , DATA> ARRAY qksort(final ARRAY array , int start , int end , Comparator<DATA> comparator) {
        final ArrayWrapper arrayWrapper = ArrayWrapper.wrap(array);
        return (ARRAY) qksort(arrayWrapper , start , end , comparator).array();
    }

    /**
     * radix sort(基数排序)<br>
     * 基数排序并不局限于对整数进行排序，只要能吧元素分割成整数，就可以排序.Should use the double
     * size of data's space.
     * 
     * @param array array
     * @param convert {@link Convert} the DATA covert to {@link Integer}
     * @param p 每个元素的位数.
     * @param k k&lt;=data.length at the comment time. 具体该选择什么值作为基数取决于数据本身，
     *            同时考虑到空间的限制.
     * @param start start position(inclusive)
     * @param end end position(exclusive)
     * @param <DATA> data type
     * @param <ARRAY> array type
     * @return the array which has been sorted.
     * @throws IllegalArgumentException if start bigger than or equal end.
     */
    @SuppressWarnings("unchecked")
    public static final <ARRAY , DATA> ARRAY rxsort(ARRAY array , Convert<DATA , Integer> convert , int p , int k , int start , int end) {
        final ArrayWrapper arrayWrapper = ArrayWrapper.wrap(array);
        return (ARRAY) rxsort(arrayWrapper , convert , p , k , start , end).array();
    }
    
    @SuppressWarnings("unchecked")
    public static final <DATA> ArrayWrapper rxsort(ArrayWrapper array , Convert<DATA , Integer> convert , int p , int k , int start , int end) {
        if (start >= end) throw new IllegalArgumentException(start + " can't bigger than or equal " + end);

        int[] counts = new int[k];
        Object temp = Array.newInstance(array.array().getClass().getComponentType() , array.length());
        ArrayWrapper tempWrapper = ArrayWrapper.wrap(temp);

        //Sort from the least significant position to the most significant.
        for (int n = 0 ; n < p ; n++) {
            //Initialize the counts.
            Arrays.fill(counts , 0);

            //Calculate the position value.
            int pval = (int) Math.pow(k , n);

            //Count the occurrences of each digit value.
            for (int j = start ; j < end ; j++) {
                int index = convert.convert((DATA) array.get(j)) / pval % k;
                counts[index] = counts[index] + 1;
            }

            //Adjust each count to reflect the counts before it.
            for (int i = 1 ; i < k ; i++)
                counts[i] = counts[i] + counts[i - 1];

            //Use the counts to position each element where it belongs.
            for (int j = end - 1 ; j >= start ; j--) {
                int index = convert.convert((DATA) array.get(j)) / pval % k;
                tempWrapper.put(array.get(j) , counts[index] - 1 + start);
                counts[index] = counts[index] - 1;
            }

            System.arraycopy(temp , start , array.array() , start , end - start);
        }

        return array;
    }

    private ArraySort() {
        throw new AssertionError("No com.github.andyshao.arithmetic.ArraySort instances for you!");
    }
}
