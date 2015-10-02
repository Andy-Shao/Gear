package com.github.andyshao.lang;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StringOperationTest {
    private volatile String str;

    @Before
    public void before() {
        this.str = "178231785178";
    }

    @Test
    public void testFlipString() {
        Assert.assertThat(StringOperation.flipString("123") , Matchers.is("321"));
    }

    @Test
    public void testisEmptyOrNull() {
        Assert.assertThat(StringOperation.isEmptyOrNull(null) , Matchers.is(true));
        Assert.assertThat(StringOperation.isEmptyOrNull("") , Matchers.is(true));
        Assert.assertThat(StringOperation.isEmptyOrNull(this.str) , Matchers.is(false));
    }

    @Test
    public void testreplaceAll() {
        Assert.assertThat(StringOperation.replaceAll(this.str , "178" , "") , Matchers.is("235"));
    }

    @Test
    public void testreplaceFirst() {
        Assert.assertThat(StringOperation.replaceFirst(this.str , "178" , "") , Matchers.is("231785178"));
    }

    @Test
    public void testreplaceLast() {
        Assert.assertThat(StringOperation.replaceLast(this.str , "178" , "") , Matchers.is("178231785"));
    }

    @Test
    public void testsplit() {
        Assert.assertArrayEquals(StringOperation.split(this.str , "231") , new String[] { "178" , "785178" });
    }
}
