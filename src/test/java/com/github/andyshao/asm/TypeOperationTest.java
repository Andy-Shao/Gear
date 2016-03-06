package com.github.andyshao.asm;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class TypeOperationTest {

    @Test
    public void testGetArgumentClasses() {
        Assert.assertThat(TypeOperation.getArgumentClasses("(I)V") , Matchers.is(new Class<?>[] { int.class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("(Z)V") , Matchers.is(new Class<?>[] { boolean.class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("(C)V") , Matchers.is(new Class<?>[] { char.class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("(B)V") , Matchers.is(new Class<?>[] { byte.class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("(S)V") , Matchers.is(new Class<?>[] { short.class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("(F)V") , Matchers.is(new Class<?>[] { float.class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("(J)V") , Matchers.is(new Class<?>[] { long.class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("(D)V") , Matchers.is(new Class<?>[] { double.class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("(Ljava/lang/String;)V") ,
            Matchers.is(new Class<?>[] { String.class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("(Ljava/lang/Void;)V") ,
            Matchers.is(new Class<?>[] { Void.class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([I)V") , Matchers.is(new Class<?>[] { int[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([Z)V") , Matchers.is(new Class<?>[] { boolean[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([C)V") , Matchers.is(new Class<?>[] { char[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([B)V") , Matchers.is(new Class<?>[] { byte[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([S)V") , Matchers.is(new Class<?>[] { short[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([F)V") , Matchers.is(new Class<?>[] { float[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([J)V") , Matchers.is(new Class<?>[] { long[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([D)V") , Matchers.is(new Class<?>[] { double[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([Ljava/lang/String;)V") ,
            Matchers.is(new Class<?>[] { String[].class }));
    }

    @Test
    public void testGetReturnClass() {
        Assert.assertTrue(void.class.isAssignableFrom(TypeOperation.getReturnClasses("()V")));
        Assert.assertTrue(int.class.isAssignableFrom(TypeOperation.getReturnClasses("()I")));
    }
}
