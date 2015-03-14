package com.github.andyshao.data.structure;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SingleLinkedTest {

    private final String[] data = new String[] {
        "Andy" , "Shao" , "andy" , "shao"
    };
    private SingleLinked<String> linked;

    @Before
    public void before() {
        this.linked = SingleLinked.<String> defaultSingleLinked((data) -> CycleLinkedElmt.defaultElmt(data));
    }

    @Test
    public void testDestroy() {
        this.testInsert();

        Assert.assertThat(this.linked.size() , Matchers.is(this.data.length));

        this.linked.clear();

        Assert.assertThat(this.linked.size() , Matchers.is(0));
    }

    @Test
    public void testInsert() {
        Assert.assertThat(this.linked.size() , Matchers.is(0));

        for (int i = 0 ; i < this.data.length ; i++)
            this.linked.list_ins_next(this.linked.tail() , this.data[i]);

        Assert.assertThat(this.linked.size() , Matchers.is(this.data.length));
        Assert.assertThat(this.linked.head().data() , Matchers.is(this.data[0]));
    }

    @Test
    public void testItrator() {
        this.testInsert();

        String string = "";
        for (String str : this.linked)
            string += str;

        Assert.assertThat(string , Matchers.is("AndyShaoandyshao"));
    }

    @Test
    public void testRemove() {
        this.testInsert();
        Assert.assertThat(this.linked.size() , Matchers.is(this.data.length));

        this.linked.clear();
        Assert.assertThat(this.linked.size() , Matchers.is(0));

        this.testInsert();
        Assert.assertThat(this.linked.size() , Matchers.is(this.data.length));

        this.linked.list_rem_next(this.linked.head());
        Assert.assertThat(this.linked.size() , Matchers.is(this.data.length - 1));
    }

    @Test
    public void testSize() {
        Assert.assertThat(this.linked.size() , Matchers.is(0));

        this.testInsert();

        Assert.assertThat(this.linked.size() , Matchers.is(this.data.length));
    }
}
