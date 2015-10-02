package com.github.andyshao.data.structure;

import java.util.Iterator;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CycleLinkedTest {
    private final String[] data = new String[] { "Andy" , "Shao" };
    private volatile CycleLinked<String> linked;

    @Before
    public void before() {
        this.linked = CycleLinked.defaultCycleLinked((data) -> CycleLinkedElmt.defaultElmt(data));
    }

    private void fill() {
        for (int i = 0 ; i < this.data.length ; i++)
            this.linked.insNext(this.linked.tail() , this.data[i]);
    }

    @Test
    public void testInsert() {
        Assert.assertThat(this.linked.size() , Matchers.is(0));

        this.fill();

        Iterator<String> iterator = this.linked.iterator();
        Assert.assertThat(iterator.next() , Matchers.is(this.data[0]));
        Assert.assertThat(iterator.next() , Matchers.is(this.data[1]));

        Assert.assertThat(this.linked.size() , Matchers.is(2));
    }

    @Test
    public void testRevmoe() {
        this.fill();

        Assert.assertThat(this.linked.size() , Matchers.is(2));

        this.linked.clear();
    }

    @Test
    public void testSize() {
        Assert.assertThat(this.linked.size() , Matchers.is(0));
    }
}
