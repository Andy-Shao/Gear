package com.github.andyshao.data.structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.andyshao.data.structure.DoubleLinked.DoubleLinkedElmt;

public class DoubleLinkedTest {

    private String[] data = new String[] { "Andy" , "Shao" };
    private DoubleLinked<String> doubleLinked;

    @BeforeEach
    public void before() {
        this.doubleLinked = DoubleLinked.<String> defaultDoubleLinked(DoubleLinkedElmt::defaultElmt);
    }

    @Test
    public void testInsert() {
        assertEquals(this.doubleLinked.size() , 0);

        for (int i = 0 ; i < this.data.length ; i++)
            this.doubleLinked.insNext(this.doubleLinked.head() , this.data[i]);

        assertEquals(this.doubleLinked.size() , 2);
    }

    @Test
    public void testIterator() {
        this.testInsert();
        Iterator<String> iterator = this.doubleLinked.iterator();
        assertEquals(iterator.next() , this.data[0]);
        assertEquals(iterator.next() , this.data[1]);
    }

    @Test
    public void testRemove() {
        this.testInsert();
        assertEquals(this.doubleLinked.size() , 2);

        this.doubleLinked.clear();
        assertEquals(this.doubleLinked.size() , 0);

        this.testInsert();
        assertEquals(this.doubleLinked.size() , 2);

        do
            this.doubleLinked.remove(this.doubleLinked.head());
        while (this.doubleLinked.size() != 0);
        assertEquals(this.doubleLinked.size() , 0);
    }

    @Test
    public void testRemoveNext() {
        this.testInsert();

        String str = this.doubleLinked.remNext(this.doubleLinked.head());
        assertEquals(str , this.data[this.data.length - 1]);
    }

    @Test
    public void testSize() {
        assertEquals(this.doubleLinked.size() , 0);
    }
}
