package com.github.andyshao.data.structure;

import java.util.PriorityQueue;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HeapTest {

    private final Double[] data = new Double[] { 1.23 , 3.21 , 4.56 , 0.37 , 1.11 , 4.23 };
    private volatile Heap<Double> heap;

    @Before
    public void before() {
        this.heap = Heap.<Double> defaultHeap((d1 , d2) -> d1.compareTo(d2));
    }

    @Test
    public void testExtract() {
        this.testInsert();

        Double head = this.heap.extract();
        PriorityQueue<Double> queue = new PriorityQueue<Double>((d1 , d2) -> d1.compareTo(d2));
        for (Double d : this.data)
            queue.add(d);
        Double first = queue.poll();

        Assert.assertThat(this.heap.size() , Matchers.is(this.data.length - 1));
        Assert.assertThat(head , Matchers.is(first));
    }

    @Test
    public void testInsert() {
        Assert.assertThat(this.heap.size() , Matchers.is(0));

        for (Double d : this.data)
            this.heap.insert(d);

        Assert.assertThat(this.heap.size() , Matchers.is(this.data.length));
    }
}
