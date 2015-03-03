package com.github.andyshao.arithmetic;

import java.util.Comparator;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.util.ArrayTools;

public class ArraySearchTest {
    private final Comparator<Integer> comparator = (i1 , i2) -> {
        return Integer.compare(i1 , i2);
    };
    private final int[] data = new int[] {
        0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9
    };
    private final Integer[] dataX = ArrayTools.pack_unpack(this.data , Integer[].class);

    @Test
    public void testBisearch() {
        int index = ArraySearch.bisearch(this.data , 3 , this.comparator);
        Assert.assertThat(index , Matchers.is(3));

        index = ArraySearch.bisearch(this.data , 15 , this.comparator);
        Assert.assertThat(index , Matchers.is(-1));
        
        index = ArraySearch.bisearch(this.dataX , 3 , this.comparator);
        Assert.assertThat(index , Matchers.is(3));
        
        index = ArraySearch.bisearch(this.dataX , 15 , this.comparator);
        Assert.assertThat(index , Matchers.is(-1));
    }
}
