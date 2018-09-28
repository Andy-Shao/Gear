package com.github.andyshao.arithmetic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import com.github.andyshao.reflect.ArrayOperation;

public class ArraySearchTest {
    private final Comparator<Integer> comparator = (i1 , i2) -> Integer.compare(i1 , i2);
    private final int[] data = new int[] { 0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 };
    private final Integer[] dataX = ArrayOperation.pack_unpack(this.data , Integer[].class);

    @Test
    public void testBisearch() {
        int index = ArraySearch.bisearch(this.data , 3 , this.comparator);
        assertEquals(index , 3);

        index = ArraySearch.bisearch(this.data , 15 , this.comparator);
        assertEquals(index , -1);

        index = ArraySearch.bisearch(this.dataX , 3 , this.comparator);
        assertEquals(index , 3);

        index = ArraySearch.bisearch(this.dataX , 15 , this.comparator);
        assertEquals(index , -1);
    }
}
