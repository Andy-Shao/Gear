package com.github.andyshao.arithmetic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.reflect.ArrayOperation;

public class ArraySortTest {
    private final Integer[] answer = new Integer[] { 310 , 311 , 312 , 313 , 314 , 315 , 316 , 317 , 318 , 319 };
    private final int[] answerX = ArrayOperation.pack_unpack(this.answer , int[].class);
    private final Integer[] clipAnswer = new Integer[] { 313 , 314 , 311 , 316 , 317 , 319 , 312 , 310 , 315 , 318 };
    private final int[] clipAnswerX = ArrayOperation.pack_unpack(this.clipAnswer , int[].class);
    private final Comparator<Integer> comparator = (i1 , i2) -> Integer.compare(i1 , i2);
    private final Convert<Integer , Integer> convert = (in) -> in;

    private volatile Integer[] data;
    private volatile int[] dataX;

    @BeforeEach
    public void before() {
        this.data = new Integer[] { 313 , 314 , 317 , 319 , 311 , 316 , 312 , 310 , 315 , 318 };
        this.dataX = ArrayOperation.pack_unpack(this.data , int[].class);
    }

    @Test
    public void teatIssort() {
        ArraySort.issort(this.data , 2 , 6 , this.comparator);
        assertTrue(Arrays.deepEquals(this.data , this.clipAnswer));

        ArraySort.issort(this.data , 0 , this.data.length , this.comparator);
        assertTrue(Arrays.deepEquals(this.data , this.answer));

        ArraySort.issort(this.dataX , 2 , 6 , this.comparator);
        assertTrue(Arrays.equals(this.dataX , this.clipAnswerX));

        ArraySort.issort(this.dataX , 0 , this.dataX.length , this.comparator);
        assertTrue(Arrays.equals(this.dataX , this.answerX));
    }

    @Test
    public void testCsort() {
        int maxValue = 0;
        for (Integer i : this.data)
            maxValue = Math.max(maxValue , i);
        maxValue++;

        ArraySort.ctsort(this.data , this.convert , 2 , 6 , maxValue);
        assertTrue(Arrays.deepEquals(this.data , this.clipAnswer));

        ArraySort.ctsort(this.data , this.convert , 0 , this.data.length , maxValue);
        assertTrue(Arrays.deepEquals(this.data , this.answer));

        ArraySort.ctsort(this.dataX , this.convert , 2 , 6 , maxValue);
        assertTrue(Arrays.equals(this.dataX , this.clipAnswerX));

        ArraySort.ctsort(this.dataX , this.convert , 0 , this.dataX.length , maxValue);
        assertTrue(Arrays.equals(this.dataX , this.answerX));
    }

    @Test
    public void testMgsort() {
        ArraySort.mgsort(this.data , 2 , 6 , this.comparator);
        assertTrue(Arrays.deepEquals(this.data , this.clipAnswer));

        ArraySort.mgsort(this.data , 0 , this.data.length , this.comparator);
        assertTrue(Arrays.deepEquals(this.data , this.answer));

        ArraySort.mgsort(this.dataX , 2 , 6 , this.comparator);
        assertTrue(Arrays.equals(this.dataX , this.clipAnswerX));

        ArraySort.mgsort(this.dataX , 0 , this.dataX.length , this.comparator);
        assertTrue(Arrays.equals(this.dataX , this.answerX));
    }

    @Test
    public void testQksort() {
        ArraySort.qksort(this.data , 2 , 6 , this.comparator);
        assertTrue(Arrays.deepEquals(this.data , this.clipAnswer));

        ArraySort.qksort(this.data , 0 , this.data.length , this.comparator);
        assertTrue(Arrays.deepEquals(this.data , this.answer));

        ArraySort.qksort(this.dataX , 2 , 6 , this.comparator);
        assertTrue(Arrays.equals(this.dataX , this.clipAnswerX));

        ArraySort.qksort(this.dataX , 0 , this.dataX.length , this.comparator);
        assertTrue(Arrays.equals(this.dataX , this.answerX));
    }

    @Test
    public void testRxsort() {
        ArraySort.rxsort(this.data , this.convert , 3 , 10 , 2 , 6);
        assertTrue(Arrays.deepEquals(this.data , this.clipAnswer));

        ArraySort.rxsort(this.data , this.convert , 3 , 10 , 0 , this.data.length);
        assertTrue(Arrays.deepEquals(this.data , this.answer));

        ArraySort.rxsort(this.dataX , this.convert , 3 , 10 , 2 , 6);
        assertTrue(Arrays.equals(this.dataX , this.clipAnswerX));

        ArraySort.rxsort(this.dataX , this.convert , 3 , 10 , 0 , this.dataX.length);
        assertTrue(Arrays.equals(this.dataX , this.answerX));
    }
}
