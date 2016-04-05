package com.github.andyshao.lang;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class ClassTest {

    @Test
    public void testisPrimitive(){
        Assert.assertThat(int.class.isPrimitive() , Matchers.is(true));
        Assert.assertThat(boolean.class.isPrimitive() , Matchers.is(true));
        Assert.assertThat(short.class.isPrimitive() , Matchers.is(true));
        Assert.assertThat(char.class.isPrimitive() , Matchers.is(true));
        Assert.assertThat(float.class.isPrimitive() , Matchers.is(true));
        Assert.assertThat(double.class.isPrimitive() , Matchers.is(true));
        Assert.assertThat(long.class.isPrimitive() , Matchers.is(true));
        Assert.assertThat(byte.class.isPrimitive() , Matchers.is(true));
        Assert.assertThat(void.class.isPrimitive() , Matchers.is(true));
        Assert.assertThat(Integer.class.isPrimitive() , Matchers.is(false));
        Assert.assertThat(Boolean.class.isPrimitive() , Matchers.is(false));
        Assert.assertThat(Short.class.isPrimitive() , Matchers.is(false));
        Assert.assertThat(Character.class.isPrimitive() , Matchers.is(false));
        Assert.assertThat(Float.class.isPrimitive() , Matchers.is(false));
        Assert.assertThat(Double.class.isPrimitive() , Matchers.is(false));
        Assert.assertThat(Long.class.isPrimitive() , Matchers.is(false));
        Assert.assertThat(Byte.class.isPrimitive() , Matchers.is(false));
        Assert.assertThat(Void.class.isPrimitive() , Matchers.is(false));
    }
}
