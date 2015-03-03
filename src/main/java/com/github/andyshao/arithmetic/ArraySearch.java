package com.github.andyshao.arithmetic;

import java.lang.reflect.Array;
import java.util.Comparator;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 3, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ArraySearch {
    private ArraySearch() {
        throw new AssertionError("No com.github.andyshao.arithmetic.ArraySearch instances for you!");
    }

    /**
     * binary search(二分查找)
     * 
     * @param sorted sorted array
     * @param target the data which want to be found out
     * @param comparator {@link Comparator}
     * @param <ARRAY> array type
     * @param <DATA> data type
     * @return if can't find out then return -1
     */
    @SuppressWarnings("unchecked")
    public static final <ARRAY , DATA> int bisearch(ARRAY sorted , DATA target , Comparator<DATA> comparator) {
        //Continue searching until the left and right indices cross.
        int left = 0;
        int right = Array.getLength(sorted) - 1;

        while (left <= right) {
            int middle = (left + right) / 2;

            int compare = comparator.compare((DATA) Array.get(sorted , middle) , target);
            //Prepare to search to the right of the middle index.
            if (compare < 0) left = middle + 1;
            //Prepare to search to the left of the middle index.
            else if (compare > 0) right = middle - 1;
            //Return the exact index where the data has been found.
            else return middle;
        }

        //Return that the data was not found.
        return -1;
    }

    /**
     * binary search(二分查找)
     * 
     * @param sorted sorted array
     * @param target the data which want to be found out
     * @param comparator {@link Comparator}
     * @param <DATA> data type
     * @return if can't find out then return -1
     */
    public static final <DATA> int bisearch(DATA[] sorted , DATA target , Comparator<DATA> comparator) {
        //Continue searching until the left and right indices cross.
        int left = 0;
        int right = sorted.length - 1;

        while (left <= right) {
            int middle = (left + right) / 2;

            int compare = comparator.compare(sorted[middle] , target);
            //Prepare to search to the right of the middle index.
            if (compare < 0) left = middle + 1;
            //Prepare to search to the left of the middle index.
            else if (compare > 0) right = middle - 1;
            //Return the exact index where the data has been found.
            else return middle;
        }

        //Return that the data was not found.
        return -1;
    }
}
