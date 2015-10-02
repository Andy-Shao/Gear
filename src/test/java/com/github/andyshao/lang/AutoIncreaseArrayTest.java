package com.github.andyshao.lang;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AutoIncreaseArrayTest {

    private volatile AutoIncreaseArray<Character> array;
    private final Character[] data = new Character[] { 'a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g' , 'h' , 'i' , 'j' , 'k' ,
        'l' , 'm' , 'n' , 'o' , 'p' , 'q' , 'r' , 's' , 't' , 'u' , 'v' , 'w' , 'x' , 'y' , 'z' };

    @Before
    public void before() {
        this.array = new AutoIncreaseArray<Character>();
    }

    @Test
    public void testForeach() {
        this.testInsertTail();

        String str = "";
        for (Character c : this.array)
            str += c;

        Assert.assertThat(str , Matchers.is("abcdefghijklmnopqrstuvwxyz"));
    }

    @Test
    public void testInsertHead() {
        Assert.assertThat(this.array.size() , Matchers.is(0));

        for (Character c : this.data)
            this.array.addHead(c);

        Assert.assertThat(this.array.size() , Matchers.is(this.data.length));
        Assert.assertThat(this.array.get(0) , Matchers.is(this.data[this.data.length - 1]));
        Assert.assertThat(this.array.get(this.data.length - 1) , Matchers.is(this.data[0]));
    }

    @Test
    public void testInsertTail() {
        Assert.assertThat(this.array.size() , Matchers.is(0));

        for (Character c : this.data)
            this.array.addTail(c);

        Assert.assertThat(this.array.size() , Matchers.is(this.data.length));
        Assert.assertThat(this.array.get(0) , Matchers.is(this.data[0]));
        Assert.assertThat(this.array.get(this.data.length - 1) , Matchers.is(this.data[this.data.length - 1]));
    }

    @Test
    public void testRemove() {
        this.testInsertTail();

        Character c = this.array.remove(this.data.length - 1);

        Assert.assertThat(this.array.size() , Matchers.is(this.data.length - 1));
        Assert.assertThat(c , Matchers.is('z'));
    }
}
