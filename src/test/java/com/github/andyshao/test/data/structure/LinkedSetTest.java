package com.github.andyshao.test.data.structure;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.andyshao.data.structure.SingleLinked;
import com.github.andyshao.test.data.structure.LinkedSet;

@Deprecated
public class LinkedSetTest {
    private final String[] data = new String[] {
        "Andy" , "shao"
    };
    private volatile LinkedSet<String> set;

    @Before
    public void before() {
        this.set = LinkedSet.DEFAULT_SET(SingleLinked.DEFAULT_SINGLE_LINKED());
    }

    @Test
    public void testInsert() {
        Assert.assertThat(this.set.size() , Matchers.is(0));

        for (int i = 0 ; i < this.data.length ; i++)
            this.set.set_insert(this.data[i]);

        Assert.assertThat(this.set.size() , Matchers.is(this.data.length));

        for (int i = 0 ; i < this.data.length ; i++)
            this.set.set_insert(this.data[i]);

        Assert.assertThat(this.set.size() , Matchers.is(this.data.length));
    }

    @Test
    public void testRemove() {
        Assert.assertThat(this.set.size() , Matchers.is(0));

        for (int i = 0 ; i < this.data.length ; i++)
            this.set.set_insert(this.data[i]);

        Assert.assertThat(this.set.size() , Matchers.is(this.data.length));

        this.set.clean();

        Assert.assertThat(this.set.size() , Matchers.is(0));
    }

    @Test
    public void testSize() {
        Assert.assertThat(this.set.size() , Matchers.is(0));
    }
}
