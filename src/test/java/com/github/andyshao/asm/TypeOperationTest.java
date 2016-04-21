package com.github.andyshao.asm;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.Type;

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
        Assert.assertThat(TypeOperation.getArgumentClasses("(Ljava/lang/String;)V") , Matchers.is(new Class<?>[] { String.class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("(Ljava/lang/Void;)V") , Matchers.is(new Class<?>[] { Void.class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([I)V") , Matchers.is(new Class<?>[] { int[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([Z)V") , Matchers.is(new Class<?>[] { boolean[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([C)V") , Matchers.is(new Class<?>[] { char[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([B)V") , Matchers.is(new Class<?>[] { byte[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([S)V") , Matchers.is(new Class<?>[] { short[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([F)V") , Matchers.is(new Class<?>[] { float[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([J)V") , Matchers.is(new Class<?>[] { long[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([D)V") , Matchers.is(new Class<?>[] { double[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("([Ljava/lang/String;)V") , Matchers.is(new Class<?>[] { String[].class }));
        Assert.assertThat(TypeOperation.getArgumentClasses("(II)V") , Matchers.is(new Class<?>[] { int.class , int.class }));
    }

    @Test
    public void testGetReturnClass() {
        Assert.assertTrue(void.class.isAssignableFrom(TypeOperation.getReturnClass("()V")));
        Assert.assertTrue(int.class.isAssignableFrom(TypeOperation.getReturnClass("()I")));
        Assert.assertTrue(boolean.class.isAssignableFrom(TypeOperation.getReturnClass("()Z")));
        Assert.assertTrue(char.class.isAssignableFrom(TypeOperation.getReturnClass("()C")));
        Assert.assertTrue(byte.class.isAssignableFrom(TypeOperation.getReturnClass("()B")));
        Assert.assertTrue(short.class.isAssignableFrom(TypeOperation.getReturnClass("()S")));
        Assert.assertTrue(float.class.isAssignableFrom(TypeOperation.getReturnClass("()F")));
        Assert.assertTrue(long.class.isAssignableFrom(TypeOperation.getReturnClass("()J")));
        Assert.assertTrue(double.class.isAssignableFrom(TypeOperation.getReturnClass("()D")));
        Assert.assertTrue(String.class.isAssignableFrom(TypeOperation.getReturnClass("()Ljava/lang/String;")));
        Assert.assertTrue(Void.class.isAssignableFrom(TypeOperation.getReturnClass("()Ljava/lang/Void;")));
        Assert.assertTrue(int[].class.isAssignableFrom(TypeOperation.getReturnClass("()[I")));
        Assert.assertTrue(boolean[].class.isAssignableFrom(TypeOperation.getReturnClass("()[Z")));
        Assert.assertTrue(char[].class.isAssignableFrom(TypeOperation.getReturnClass("()[C")));
        Assert.assertTrue(byte[].class.isAssignableFrom(TypeOperation.getReturnClass("()[B")));
        Assert.assertTrue(short[].class.isAssignableFrom(TypeOperation.getReturnClass("()[S")));
        Assert.assertTrue(float[].class.isAssignableFrom(TypeOperation.getReturnClass("()[F")));
        Assert.assertTrue(long[].class.isAssignableFrom(TypeOperation.getReturnClass("()[J")));
        Assert.assertTrue(double[].class.isAssignableFrom(TypeOperation.getReturnClass("()[D")));
        Assert.assertTrue(String[].class.isAssignableFrom(TypeOperation.getReturnClass("()[Ljava/lang/String;")));
        Assert.assertTrue(Void[].class.isAssignableFrom(TypeOperation.getReturnClass("()[Ljava/lang/Void;")));
    }

    @Test
    public void testGetType() {
        Type type = Type.getType("Ljava/lang/Object;");
        Assert.assertTrue(TypeOperation.getClass(type) == Object.class);
        type = Type.getType("Ljava/util/List;<Ljava/lang/Object;>");
        Assert.assertTrue(TypeOperation.getClass(type) == List.class);
        type = Type.getType("Ljava/util/Map;<Ljava/lang/String;Ljava/lang/Object;>");
        Assert.assertTrue(TypeOperation.getClass(type) == Map.class);
        type = Type.getType("Ljava/util/function/BiFunction;<Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Boolean;>");
        Assert.assertTrue(TypeOperation.getClass(type) == BiFunction.class);
    }
}
