package com.github.andyshao.arithmetic;

import java.util.Arrays;
import java.util.Comparator;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.andyshao.convert.Convert;

public class ArraySortTest {
    private final Integer[] answer = new Integer[] {
        0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9
    };
    private final Integer[] clipAnswer = new Integer[] {
        3 , 4 , 1 , 6 , 7 , 9 , 2 , 0 , 5 , 8
    };
    private volatile Integer[] data;
    private final Comparator<Integer> comparator = (i1 , i2) -> {
        return Integer.compare(i1 , i2);
    };

    @Before
    public void before() {
        this.data = new Integer[] {
            3 , 4 , 7 , 9 , 1 , 6 , 2 , 0 , 5 , 8
        };
    }

    @Test
    public void teatIssort() {
        ArraySort.issort(this.data , 2 , 6 , this.comparator);
        Assert.assertThat(Arrays.deepEquals(this.data , this.clipAnswer) , Matchers.is(true));

        ArraySort.<Integer> issort(this.data , 0 , this.data.length , this.comparator);
        Assert.assertThat(Arrays.deepEquals(this.data , this.answer) , Matchers.is(true));
    }

    @Test
    public void testQksort() {
        ArraySort.qksort(this.data , 2 , 6 , this.comparator);
        Assert.assertThat(Arrays.deepEquals(this.data , this.clipAnswer) , Matchers.is(true));

        ArraySort.qksort(this.data , 0 , this.data.length , this.comparator);
        Assert.assertThat(Arrays.deepEquals(this.data , this.answer) , Matchers.is(true));
    }

    @Test
    public void testMgsort() {
        ArraySort.mgsort(this.data , 2 , 6 , this.comparator);
        Assert.assertThat(Arrays.deepEquals(this.data , this.clipAnswer) , Matchers.is(true));

        ArraySort.mgsort(this.data , 0 , this.data.length , this.comparator);
        Assert.assertThat(Arrays.deepEquals(this.data , this.answer) , Matchers.is(true));
    }

    @Test
    public void testCsort() {
        final Convert<Integer , Integer> convert = (in) -> {
            return in;
        };
        
        ArraySort.ctsort(this.data , convert , 2 , 6 , 10);
        Assert.assertThat(Arrays.deepEquals(this.data , this.clipAnswer) , Matchers.is(true));
        
        ArraySort.<Integer> ctsort(this.data , convert , 0 , this.data.length , 10);
        Assert.assertThat(Arrays.deepEquals(this.data , this.answer) , Matchers.is(true));
    }
}
