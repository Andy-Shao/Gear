package com.github.andyshao.arithmetic;

import java.util.Arrays;
import java.util.Comparator;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.andyshao.convert.Convert;
import com.github.andyshao.util.ArrayTools;

public class ArraySortTest {
    private final Integer[] answer = new Integer[] {
        310 , 311 , 312 , 313 , 314 , 315 , 316 , 317 , 318 , 319
    };
    private final int[] answerX = ArrayTools.pack_unpack(this.answer , int[].class);
    private final Integer[] clipAnswer = new Integer[] {
        313 , 314 , 311 , 316 , 317 , 319 , 312 , 310 , 315 , 318
    };
    private final int[] clipAnswerX = ArrayTools.pack_unpack(this.clipAnswer , int[].class);
    private final Comparator<Integer> comparator = (i1 , i2) -> {
        return Integer.compare(i1 , i2);
    };
    private final Convert<Integer , Integer> convert = (in) -> {
        return in;
    };

    private volatile Integer[] data;
    private volatile int[] dataX;

    @Before
    public void before() {
        this.data = new Integer[] {
            313 , 314 , 317 , 319 , 311 , 316 , 312 , 310 , 315 , 318
        };
        this.dataX = ArrayTools.pack_unpack(this.data , int[].class);
    }

    @Test
    public void teatIssort() {
        ArraySort.issort(this.data , 2 , 6 , this.comparator);
        Assert.assertThat(Arrays.deepEquals(this.data , this.clipAnswer) , Matchers.is(true));

        ArraySort.issort(this.data , 0 , this.data.length , this.comparator);
        Assert.assertThat(Arrays.deepEquals(this.data , this.answer) , Matchers.is(true));

        ArraySort.issort(this.dataX , 2 , 6 , this.comparator);
        Assert.assertThat(Arrays.equals(this.dataX , this.clipAnswerX) , Matchers.is(true));

        ArraySort.issort(this.dataX , 0 , this.dataX.length , this.comparator);
        Assert.assertThat(Arrays.equals(this.dataX , this.answerX) , Matchers.is(true));
    }

    @Test
    public void testCsort() {
        int maxValue = 0;
        for (Integer i : this.data)
            maxValue = Math.max(maxValue , i);
        maxValue++;

        ArraySort.ctsort(this.data , this.convert , 2 , 6 , maxValue);
        Assert.assertThat(Arrays.deepEquals(this.data , this.clipAnswer) , Matchers.is(true));

        ArraySort.ctsort(this.data , this.convert , 0 , this.data.length , maxValue);
        Assert.assertThat(Arrays.deepEquals(this.data , this.answer) , Matchers.is(true));

        ArraySort.ctsort(this.dataX , this.convert , 2 , 6 , maxValue);
        Assert.assertThat(Arrays.equals(this.dataX , this.clipAnswerX) , Matchers.is(true));

        ArraySort.ctsort(this.dataX , this.convert , 0 , this.dataX.length , maxValue);
        Assert.assertThat(Arrays.equals(this.dataX , this.answerX) , Matchers.is(true));
    }

    @Test
    public void testMgsort() {
        ArraySort.mgsort(this.data , 2 , 6 , this.comparator);
        Assert.assertThat(Arrays.deepEquals(this.data , this.clipAnswer) , Matchers.is(true));

        ArraySort.mgsort(this.data , 0 , this.data.length , this.comparator);
        Assert.assertThat(Arrays.deepEquals(this.data , this.answer) , Matchers.is(true));
        
        ArraySort.mgsort(this.dataX , 2 , 6 , this.comparator);
        Assert.assertThat(Arrays.equals(this.dataX , this.clipAnswerX) , Matchers.is(true));
        
        ArraySort.mgsort(this.dataX , 0 , this.dataX.length , this.comparator);
        Assert.assertThat(Arrays.equals(this.dataX , this.answerX) , Matchers.is(true));
    }

    @Test
    public void testQksort() {
        ArraySort.qksort(this.data , 2 , 6 , this.comparator);
        Assert.assertThat(Arrays.deepEquals(this.data , this.clipAnswer) , Matchers.is(true));

        ArraySort.qksort(this.data , 0 , this.data.length , this.comparator);
        Assert.assertThat(Arrays.deepEquals(this.data , this.answer) , Matchers.is(true));
        
        ArraySort.qksort(this.dataX , 2 , 6 , this.comparator);
        Assert.assertThat(Arrays.equals(this.dataX , this.clipAnswerX) , Matchers.is(true));
        
        ArraySort.qksort(this.dataX , 0 , this.dataX.length , this.comparator);
        Assert.assertThat(Arrays.equals(this.dataX , this.answerX) , Matchers.is(true));
    }

    @Test
    public void testRxsort() {
        ArraySort.rxsort(this.data , this.convert , 3 , 10 , 2 , 6);
        Assert.assertThat(Arrays.deepEquals(this.data , this.clipAnswer) , Matchers.is(true));

        ArraySort.rxsort(this.data , this.convert , 3 , 10 , 0 , this.data.length);
        Assert.assertThat(Arrays.deepEquals(this.data , this.answer) , Matchers.is(true));

        ArraySort.rxsort(this.dataX , this.convert , 3 , 10 , 2 , 6);
        Assert.assertThat(Arrays.equals(this.dataX , this.clipAnswerX) , Matchers.is(true));

        ArraySort.rxsort(this.dataX , this.convert , 3 , 10 , 0 , this.dataX.length);
        Assert.assertThat(Arrays.equals(this.dataX , this.answerX) , Matchers.is(true));
    }
}
