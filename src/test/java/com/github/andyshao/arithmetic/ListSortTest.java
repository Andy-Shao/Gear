package com.github.andyshao.arithmetic;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ListSortTest {
    private final List<Integer> answer = Arrays.asList(310 , 311 , 312 , 313 , 314 , 315 , 316 , 317 , 318 , 319);
    private final List<Integer> clipAnswer = Arrays.asList(313 , 314 , 311 , 316 , 317 , 319 , 312 , 310 , 315 , 318);
    private final Comparator<Integer> comparator = (i1 , i2) -> Integer.compare(i1 , i2);

    private volatile List<Integer> data;

    @Before
    public void befor() {
        this.data = Arrays.asList(313 , 314 , 317 , 319 , 311 , 316 , 312 , 310 , 315 , 318);
    }

    @Test
    public void testIssort() {
        ListSort.issort(this.data , 2 , 6 , this.comparator);
        Assert.assertThat(this.data.equals(this.clipAnswer) , Matchers.is(true));

        ListSort.issort(this.data , 0 , this.data.size() , this.comparator);
        Assert.assertThat(this.data.equals(this.answer) , Matchers.is(true));
    }
}
