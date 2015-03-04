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
    public static final <ARRAY , DATA> ARRAY ctsort(
        ARRAY array , Convert<DATA , Integer> convert , int start , int end , int max) {
        if (start > end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        int[] counts = new int[max];
        Arrays.fill(counts , 0);
        //Count the occurrences of each element.
        for (int i = start ; i < end ; i++) {
            Integer index = convert.convert((DATA) Array.get(array , i));
            counts[index] = counts[index] + 1;
        }
        //Adjust each count to reflect the counts before it.
        for (int i = 1 ; i < max ; i++)
            counts[i] = counts[i] + counts[i - 1];

        ARRAY temp = (ARRAY) Array.newInstance(array.getClass().getComponentType() , Array.getLength(array));
        //Use the counts to position each element where it belongs.
        for (int i = end - 1 ; i >= start ; i--) {
            int index = convert.convert((DATA) Array.get(array , i));
            Array.set(temp , counts[index] - 1 + start , Array.get(array , i));
            counts[index] = counts[index] - 1;
        }

        //Prepare to pass back the sorted data.
        System.arraycopy(temp , start , array , start , end - start);

        return array;
    }

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
     * @param <DATA> array type
     * @return the array which has been sorted.
     * @throws IllegalArgumentException if start bigger than or equal end.
     */
    @SuppressWarnings("unchecked")
    public static final <DATA> DATA[] ctsort(
        DATA[] array , Convert<DATA , Integer> convert , int start , int end , int max) {
        if (start > end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        int[] counts = new int[max];
        Arrays.fill(counts , 0);
        //Count the occurrences of each element.
        for (int i = start ; i < end ; i++) {
            Integer index = convert.convert(array[i]);
            counts[index] = counts[index] + 1;
        }
        //Adjust each count to reflect the counts before it.
        for (int i = 1 ; i < max ; i++)
            counts[i] = counts[i] + counts[i - 1];

        DATA[] temp = (DATA[]) Array.newInstance(array.getClass().getComponentType() , array.length);
        //Use the counts to position each element where it belongs.
        for (int i = end - 1 ; i >= start ; i--) {
            int index = convert.convert(array[i]);
            temp[counts[index] - 1 + start] = array[i];
            counts[index] = counts[index] - 1;
        }

        //Prepare to pass back the sorted data.
        System.arraycopy(temp , start , array , start , end - start);

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
    public static final <ARRAY , DATA> ARRAY
        issort(final ARRAY data , int start , int end , Comparator<DATA> comparator) {
        if (start > end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        //Repeatedly insert a key element among the sorted elements.
        for (int i = start + 1 ; i < end ; i++) {
            DATA temp = (DATA) Array.get(data , i);
            int j = i - 1;

            //Determine the position at which to insert the key element.
            for ( ; j >= start && comparator.compare((DATA) Array.get(data , j) , temp) > 0 ; j--)
                Array.set(data , j + 1 , Array.get(data , j));
            Array.set(data , j + 1 , temp);
        }
        return data;
    }

    /**
     * insert sort(插入排序)
     * 
     * @param data array
     * @param comparator {@link Comparator}
     * @param start start position(inclusive)
     * @param end end position(exclusive)
     * @param <DATA> data type
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
     * @param array array
     * @param start start position(inclusive)
     * @param division division position(inclusive)
     * @param end end position(exclusive)
     * @param comparator {@link Comparator}
     * @param <DATA> data type
     * @return the array which has been merged
     */
    @SuppressWarnings("unchecked")
    static final <DATA> DATA[] merge(DATA[] array , int start , int division , int end , Comparator<DATA> comparator) {
        //initialize the counters used in merging.
        int leftPos = start;
        int rightPos = division + 1;
        int tempPos = 0;
        DATA[] temp = (DATA[]) Array.newInstance(array.getClass().getComponentType() , end - start);

        //Continue while either division has elements to merge.
        FIRST: while (leftPos <= division || rightPos < end) {
            if (leftPos > division) {
                //The left division has no more elements to merge.
                while (rightPos < end)
                    temp[tempPos++] = array[rightPos++];
                continue FIRST;
            } else if (rightPos >= end) {
                //The right division has no more elements to merge.
                while (leftPos <= division)
                    temp[tempPos++] = array[leftPos++];
                continue FIRST;
            }

            //Append the next ordered element to the merged elements.
            if (comparator.compare(array[leftPos] , array[rightPos]) < 0) temp[tempPos++] = array[leftPos++];
            else temp[tempPos++] = array[rightPos++];
        }

        //Prepare to pass back the merged data.
        System.arraycopy(temp , 0 , array , start , end - start);

        return array;
    }

    /**
     * 
     * @param array array
     * @param start start position(inclusive)
     * @param division division position(inclusive)
     * @param end end position(exclusive)
     * @param comparator {@link Comparator}
     * @param <DATA> data type
     * @param <ARRAY> array type
     * @return the array which has been merged
     */
    @SuppressWarnings("unchecked")
    static final <ARRAY , DATA> ARRAY merge(
        ARRAY array , int start , int division , int end , Comparator<DATA> comparator) {
        //initialize the counters used in merging.
        int leftPos = start;
        int rightPos = division + 1;
        int tempPos = 0;
        ARRAY temp = (ARRAY) Array.newInstance(array.getClass().getComponentType() , end - start);

        //Continue while either division has elements to merge.
        FIRST: while (leftPos <= division || rightPos < end) {
            if (leftPos > division) {
                //The left division has no more elements to merge.
                while (rightPos < end)
                    Array.set(temp , tempPos++ , Array.get(array , rightPos++));
                continue FIRST;
            } else if (rightPos >= end) {
                //The right division has no more elements to merge.
                while (leftPos <= division)
                    Array.set(temp , tempPos++ , Array.get(array , leftPos++));
                continue FIRST;
            }

            //Append the next ordered element to the merged elements.
            if (comparator.compare((DATA) Array.get(array , leftPos) , (DATA) Array.get(array , rightPos)) < 0) Array.set(
                temp , tempPos++ , Array.get(array , leftPos++));
            else Array.set(temp , tempPos++ , Array.get(array , rightPos++));
        }

        //Prepare to pass back the merged data.
        System.arraycopy(temp , 0 , array , start , end - start);

        return array;
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
    public static final <ARRAY, DATA> ARRAY mgsort(ARRAY data, int start, int end, Comparator<DATA> comparator){
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

    @SuppressWarnings("unchecked")
    static final <ARRAY , DATA> int partition(ARRAY array , int start , int end , Comparator<DATA> comparator) {
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
        DATA pval = (DATA) Array.get(array , r[1]);

        //Create two partitions around the partition value.
        start--;
        tail++;

        while (true) {
            //Move left until an element is found in the wrong partition.
            do
                tail--;
            while (comparator.compare((DATA) Array.get(array , tail) , pval) > 0);

            //Move right until an element is found in the wrong partition.
            do
                start++;
            while (comparator.compare((DATA) Array.get(array , start) , pval) < 0);

            if (start >= tail) //Stop partitioning when the left and right counters cross.
            break;
            else {
                //Swap the elements now under the left and right counters.
                DATA temp = (DATA) Array.get(array , start);
                Array.set(array , start , Array.get(array , tail));
                Array.set(array , tail , temp);
            }
        }

        return tail;
    }

    static final <DATA> int partition(DATA[] array , int start , int end , Comparator<DATA> comparator) {
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
        DATA pval = array[r[1]];

        //Create two partitions around the partition value.
        start--;
        tail++;

        while (true) {
            //Move left until an element is found in the wrong partition.
            do
                tail--;
            while (comparator.compare(array[tail] , pval) > 0);

            //Move right until an element is found in the wrong partition.
            do
                start++;
            while (comparator.compare(array[start] , pval) < 0);

            if (start >= tail) //Stop partitioning when the left and right counters cross.
            break;
            else {
                //Swap the elements now under the left and right counters.
                DATA temp = array[start];
                array[start] = array[tail];
                array[tail] = temp;
            }
        }

        return tail;
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
     * @throws ArraySortException when the sort has error.
     * @throws IllegalArgumentException if start bigger than or equal end
     */
    public static final <ARRAY , DATA> ARRAY qksort(
        final ARRAY array , int start , int end , Comparator<DATA> comparator) {
        if (start >= end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        //Stop the recursion when it is not possible to partition further.
        while (start < end - 1) {
            int index = 0;
            //Determine where to partition the elements.
            if ((index = ArraySort.partition(array , start , end , comparator)) < 0) throw new ArraySortException();

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
     * @return the array which is sorted.
     * @throws ArraySortException when the sort has error.
     * @throws IllegalArgumentException if start bigger than or equal end
     */
    public static final <DATA> DATA[] qksort(final DATA[] array , int start , int end , Comparator<DATA> comparator) {
        if (start >= end) throw new IllegalArgumentException(start + " bigger than or equal " + end);
        //Stop the recursion when it is not possible to partition further.
        while (start < end - 1) {
            int index = 0;
            //Determine where to partition the elements.
            if ((index = ArraySort.partition(array , start , end , comparator)) < 0) throw new ArraySortException();

            //Recursively sort the left partition.
            ArraySort.qksort(array , start , ++index , comparator);

            //Iterate and sort the right partition.
            start = index;
        }
        return array;
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
    public static final <ARRAY , DATA> ARRAY rxsort(
        ARRAY array , Convert<DATA , Integer> convert , int p , int k , int start , int end) {
        if (start >= end) throw new IllegalArgumentException(start + " can't bigger than or equal " + end);

        int[] counts = new int[k];
        ARRAY temp = (ARRAY) Array.newInstance(array.getClass().getComponentType() , Array.getLength(array));

        //Sort from the least significant position to the most significant.
        for (int n = 0 ; n < p ; n++) {
            //Initialize the counts.
            Arrays.fill(counts , 0);

            //Calculate the position value.
            int pval = (int) Math.pow(k , n);

            //Count the occurrences of each digit value.
            for (int j = start ; j < end ; j++) {
                int index = convert.convert((DATA) Array.get(array , j)) / pval % k;
                counts[index] = counts[index] + 1;
            }

            //Adjust each count to reflect the counts before it.
            for (int i = 1 ; i < k ; i++)
                counts[i] = counts[i] + counts[i - 1];

            //Use the counts to position each element where it belongs.
            for (int j = end - 1 ; j >= start ; j--) {
                int index = convert.convert((DATA) Array.get(array , j)) / pval % k;
                Array.set(temp , counts[index] - 1 + start , Array.get(array , j));
                counts[index] = counts[index] - 1;
            }

            System.arraycopy(temp , start , array , start , end - start);
        }

        return array;
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
     * @return the array which has been sorted.
     * @throws IllegalArgumentException if start bigger than or equal end.
     */
    @SuppressWarnings("unchecked")
    public static final <DATA> DATA[] rxsort(
        DATA[] array , Convert<DATA , Integer> convert , int p , int k , int start , int end) {
        if (start >= end) throw new IllegalArgumentException(start + " can't bigger than or equal " + end);

        int[] counts = new int[k];
        DATA[] temp = (DATA[]) Array.newInstance(array.getClass().getComponentType() , array.length);

        //Sort from the least significant position to the most significant.
        for (int n = 0 ; n < p ; n++) {
            //Initialize the counts.
            Arrays.fill(counts , 0);

            //Calculate the position value.
            int pval = (int) Math.pow(k , n);

            //Count the occurrences of each digit value.
            for (int j = start ; j < end ; j++) {
                int index = convert.convert(array[j]) / pval % k;
                counts[index] = counts[index] + 1;
            }

            //Adjust each count to reflect the counts before it.
            for (int i = 1 ; i < k ; i++)
                counts[i] = counts[i] + counts[i - 1];

            //Use the counts to position each element where it belongs.
            for (int j = end - 1 ; j >= start ; j--) {
                int index = convert.convert(array[j]) / pval % k;
                temp[counts[index] - 1 + start] = array[j];
                counts[index] = counts[index] - 1;
            }

            System.arraycopy(temp , start , array , start , end - start);
        }

        return array;
    }

    private ArraySort() {
        throw new AssertionError("No com.github.andyshao.arithmetic.ArraySort instances for you!");
    }
}
