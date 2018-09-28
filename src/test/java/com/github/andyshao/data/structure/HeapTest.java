package com.github.andyshao.data.structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.PriorityQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeapTest {

    private final Double[] data = new Double[] { 1.23 , 3.21 , 4.56 , 0.37 , 1.11 , 4.23 };
    private volatile Heap<Double> heap;

    @BeforeEach
    public void before() {
        this.heap = Heap.<Double> defaultHeap(Double::compare);
    }

    @Test
    public void testExtract() {
        this.testInsert();

        Double head = this.heap.extract();
        PriorityQueue<Double> queue = new PriorityQueue<Double>(Double::compare);
        for (Double d : this.data)
            queue.add(d);
        Double first = queue.poll();

        assertEquals(this.heap.size() , this.data.length - 1);
        assertEquals(head , first);
    }

    @Test
    public void testInsert() {
        assertEquals(this.heap.size() , 0);

        for (Double d : this.data)
            this.heap.insert(d);

        assertEquals(this.heap.size() , this.data.length);
    }
}
