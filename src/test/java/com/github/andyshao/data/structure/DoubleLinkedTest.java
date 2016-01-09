package com.github.andyshao.data.structure;

import java.util.Iterator;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.andyshao.data.structure.DoubleLinked.DoubleLinkedElmt;

public class DoubleLinkedTest {

    private String[] data = new String[] { "Andy" , "Shao" };
    private DoubleLinked<String> doubleLinked;

    @Before
    public void before() {
        this.doubleLinked = DoubleLinked.<String> defaultDoubleLinked(DoubleLinkedElmt::defaultElmt);
    }

    @Test
    public void testInsert() {
        Assert.assertThat(this.doubleLinked.size() , Matchers.is(0));

        for (int i = 0 ; i < this.data.length ; i++)
            this.doubleLinked.insNext(this.doubleLinked.head() , this.data[i]);

        Assert.assertThat(this.doubleLinked.size() , Matchers.is(2));
    }

    @Test
    public void testIterator() {
        this.testInsert();
        Iterator<String> iterator = this.doubleLinked.iterator();
        Assert.assertThat(iterator.next() , Matchers.is(this.data[0]));
        Assert.assertThat(iterator.next() , Matchers.is(this.data[1]));
    }

    @Test
    public void testRemove() {
        this.testInsert();
        Assert.assertThat(this.doubleLinked.size() , Matchers.is(2));

        this.doubleLinked.clear();
        Assert.assertThat(this.doubleLinked.size() , Matchers.is(0));

        this.testInsert();
        Assert.assertThat(this.doubleLinked.size() , Matchers.is(2));

        do
            this.doubleLinked.remove(this.doubleLinked.head());
        while (this.doubleLinked.size() != 0);
        Assert.assertThat(this.doubleLinked.size() , Matchers.is(0));
    }

    @Test
    public void testRemoveNext() {
        this.testInsert();

        String str = this.doubleLinked.remNext(this.doubleLinked.head());
        Assert.assertThat(str , Matchers.is(this.data[this.data.length - 1]));
    }

    @Test
    public void testSize() {
        Assert.assertThat(this.doubleLinked.size() , Matchers.is(0));
    }
}
