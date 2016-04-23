package com.github.andyshao.reflect;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.reflect.annotation.Generic;
import com.github.andyshao.reflect.annotation.MethodInfo;
import com.github.andyshao.reflect.annotation.Param;

@Generic(isGeneric = true, componentTypes = "Ljava/lang/Object;")
public class GenericTest<T> {
    public void
        listParam(@Param(value = "param" , genericInfo = @Generic(isGeneric = true , componentTypes = "Ljava/util/List;<Ljava/util/List;<Ljava/lang/String;>>") ) List<List<List<String>>> param) {

    }

    @Test
    public void listParamTesting() {
        Method method = MethodOperation.getMethod(GenericTest.class , "listParam" , List.class);
        GenericInfo[] infos = ParameterOperation.getMethodParamGenricInfo(method);
        Assert.assertThat(infos.length , Matchers.is(1));
        Assert.assertThat(infos[0].isGeneiric , Matchers.is(true));
        Assert.assertThat(infos[0].declareType == List.class , Matchers.is(true));
        Assert.assertThat(infos[0].componentTypes != null , Matchers.is(true));

        infos = infos[0].componentTypes;
        Assert.assertThat(infos.length , Matchers.is(1));
        Assert.assertThat(infos[0].isGeneiric , Matchers.is(true));
        Assert.assertThat(infos[0].declareType == List.class , Matchers.is(true));
        Assert.assertThat(infos[0].componentTypes != null , Matchers.is(true));

        infos = infos[0].componentTypes;
        Assert.assertThat(infos.length , Matchers.is(1));
        Assert.assertThat(infos[0].isGeneiric , Matchers.is(true));
        Assert.assertThat(infos[0].declareType == List.class , Matchers.is(true));
        Assert.assertThat(infos[0].componentTypes != null , Matchers.is(true));

        infos = infos[0].componentTypes;
        Assert.assertThat(infos.length , Matchers.is(1));
        Assert.assertThat(infos[0].isGeneiric , Matchers.is(false));
        Assert.assertThat(infos[0].declareType == String.class , Matchers.is(true));
        Assert.assertThat(infos[0].componentTypes == null , Matchers.is(true));
    }

    @MethodInfo(returnGenericInfo = @Generic(isGeneric = true , componentTypes = "Ljava/util/List;<Ljava/util/List;<Ljava/lang/String;>>") )
    public List<List<List<String>>> listReturn() {
        return null;
    }

    @Test
    public void listReturnTesting() {
        Method method = MethodOperation.getMethod(GenericTest.class , "listReturn");
        GenericInfo genericInfo = MethodOperation.getReturnGenericInfo(method);
        Assert.assertThat(genericInfo.isGeneiric , Matchers.is(true));
        Assert.assertThat(genericInfo.declareType == List.class , Matchers.is(true));
        Assert.assertThat(genericInfo.componentTypes != null , Matchers.is(true));

        GenericInfo[] infos = genericInfo.componentTypes;
        Assert.assertThat(infos.length , Matchers.is(1));
        Assert.assertThat(infos[0].isGeneiric , Matchers.is(true));
        Assert.assertThat(infos[0].declareType == List.class , Matchers.is(true));
        Assert.assertThat(infos[0].componentTypes != null , Matchers.is(true));

        infos = infos[0].componentTypes;
        Assert.assertThat(infos.length , Matchers.is(1));
        Assert.assertThat(infos[0].isGeneiric , Matchers.is(true));
        Assert.assertThat(infos[0].declareType == List.class , Matchers.is(true));
        Assert.assertThat(infos[0].componentTypes != null , Matchers.is(true));

        infos = infos[0].componentTypes;
        Assert.assertThat(infos.length , Matchers.is(1));
        Assert.assertThat(infos[0].isGeneiric , Matchers.is(false));
        Assert.assertThat(infos[0].declareType == String.class , Matchers.is(true));
        Assert.assertThat(infos[0].componentTypes == null , Matchers.is(true));
    }

    @MethodInfo(returnGenericInfo = @Generic(isGeneric = true , componentTypes = { "Ljava/lang/String;" , "Ljava/util/List;<Ljava/lang/Object;>" }) )
    public Map<String , List<Object>> mapReturn() {
        return null;
    }

    @Test
    public void mapReturnTesting() {
        Method method = MethodOperation.getMethod(GenericTest.class , "mapReturn");
        GenericInfo genericInfo = MethodOperation.getReturnGenericInfo(method);
        Assert.assertThat(genericInfo.isGeneiric , Matchers.is(true));
        Assert.assertThat(genericInfo.declareType == Map.class , Matchers.is(true));
        Assert.assertThat(genericInfo.componentTypes != null , Matchers.is(true));

        GenericInfo[] infos = genericInfo.componentTypes;
        Assert.assertThat(infos.length , Matchers.is(2));
        Assert.assertThat(infos[0].isGeneiric , Matchers.is(false));
        Assert.assertThat(infos[0].declareType == String.class , Matchers.is(true));
        Assert.assertThat(infos[0].componentTypes == null , Matchers.is(true));
        Assert.assertThat(infos[1].isGeneiric , Matchers.is(true));
        Assert.assertThat(infos[1].declareType == List.class , Matchers.is(true));
        Assert.assertThat(infos[1].componentTypes.length , Matchers.is(1));
        Assert.assertThat(infos[1].componentTypes[0].isGeneiric , Matchers.is(false));
        Assert.assertThat(infos[1].componentTypes[0].declareType == Object.class , Matchers.is(true));
        Assert.assertThat(infos[1].componentTypes[0].componentTypes == null , Matchers.is(true));
    }
}
