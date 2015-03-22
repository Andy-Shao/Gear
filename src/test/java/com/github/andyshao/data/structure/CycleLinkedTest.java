package com.github.andyshao.data.structure;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CycleLinkedTest {
    private final String[] data = new String[] {
        "Andy" , "Shao"
    };
    private volatile CycleLinked<String> linked;

    @Before
    public void before() {
        this.linked = CycleLinked.defaultCycleLinked();
    }

    private void fill() {
        for (int i = 0 ; i < this.data.length ; i++)
            this.linked.insNext(null , this.data[i]);
    }

    @Test
    public void testInsert() {
        Assert.assertThat(this.linked.size() , Matchers.is(0));

        this.fill();

        for (String str : this.linked)
            System.out.println(str);

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
