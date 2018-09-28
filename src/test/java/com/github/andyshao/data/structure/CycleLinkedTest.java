package com.github.andyshao.data.structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CycleLinkedTest {
    private final String[] data = new String[] { "Andy" , "Shao" };
    private volatile CycleLinked<String> linked;

    @BeforeEach
    public void before() {
        this.linked = CycleLinked.defaultCycleLinked(CycleLinkedElmt::defaultElmt);
    }

    private void fill() {
        for (int i = 0 ; i < this.data.length ; i++)
            this.linked.insNext(this.linked.tail() , this.data[i]);
    }

    @Test
    public void testInsert() {
        assertEquals(this.linked.size() , 0);
//        Assert.assertThat(this.linked.size() , Matchers.is(0));

        this.fill();

        Iterator<String> iterator = this.linked.iterator();
        assertEquals(iterator.next() , this.data[0]);
//        Assert.assertThat(iterator.next() , Matchers.is(this.data[0]));
        assertEquals(iterator.next() , this.data[1]);
//        Assert.assertThat(iterator.next() , Matchers.is(this.data[1]));

        assertEquals(this.linked.size() , 2);
//        Assert.assertThat(this.linked.size() , Matchers.is(2));
    }

    @Test
    public void testRevmoe() {
        this.fill();

        assertEquals(this.linked.size() , 2);
//        Assert.assertThat(this.linked.size() , Matchers.is(2));

        this.linked.clear();
    }

    @Test
    public void testSize() {
        assertEquals(this.linked.size() , 0);
//        Assert.assertThat(this.linked.size() , Matchers.is(0));
    }
}
