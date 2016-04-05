package com.github.andyshao.reflect;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.reflect.annotation.Generic;
import com.github.andyshao.reflect.annotation.MethodInfo;

public class GenericTest {
    @MethodInfo(returnGenericInfo = @Generic(isGeneric = true ,
        componentTypes = { "Ljava/lang/String;" , "Ljava/util/List;<Ljava/lang/Object;>" }) )
    public Map<String , List<Object>> mapReturn() {
        return null;
    }
    
    @Test
    public void test(){
        Method method = MethodOperation.getMethod(GenericTest.class , "mapReturn");
        GenericInfo genericInfo = MethodOperation.getReturnGenericInfo(method);
        Assert.assertThat(genericInfo.isGeneiric , Matchers.is(true));
        Assert.assertThat(genericInfo.declareType == Map.class , Matchers.is(true));
        
        GenericInfo[] infos = genericInfo.componentTypes;
        Assert.assertThat(infos[0].isGeneiric , Matchers.is(false));
        Assert.assertThat(infos[0].declareType == String.class, Matchers.is(true));
        Assert.assertThat(infos[0].componentTypes == null , Matchers.is(true));
        Assert.assertThat(infos[1].isGeneiric , Matchers.is(true));
        Assert.assertThat(infos[1].declareType == List.class , Matchers.is(true));
        Assert.assertThat(infos[1].componentTypes[0].isGeneiric , Matchers.is(false));
        Assert.assertThat(infos[1].componentTypes[0].declareType == Object.class , Matchers.is(true));
        Assert.assertThat(infos[1].componentTypes[0].componentTypes == null , Matchers.is(true));
    }
}
