package com.github.andyshao.arithmetic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ListSortTest {
    private final List<Integer> answer = Arrays.asList(310 , 311 , 312 , 313 , 314 , 315 , 316 , 317 , 318 , 319);
    private final List<Integer> clipAnswer = Arrays.asList(313 , 314 , 311 , 316 , 317 , 319 , 312 , 310 , 315 , 318);

    private volatile List<Integer> data;

    @BeforeEach
    public void befor() {
        this.data = Arrays.asList(313 , 314 , 317 , 319 , 311 , 316 , 312 , 310 , 315 , 318);
    }

    @Test
    public void testIssort() {
        ListSort.issort(this.data , 2 , 6 , Integer::compare);
        assertTrue(this.data.equals(this.clipAnswer));

        ListSort.issort(this.data , 0 , this.data.size() , Integer::compare);
        assertTrue(this.data.equals(this.answer));
    }
}
