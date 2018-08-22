package com.github.andyshao.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.objectweb.asm.Type;

import com.github.andyshao.reflect.annotation.Generic;
import com.github.andyshao.reflect.annotation.MethodInfo;
import com.github.andyshao.reflect.annotation.Param;

@Ignore
@Deprecated
@Generic(isGeneric = true , componentTypes = "Ljava/lang/Object;")
public class GenericTest<T> {
    @Generic(isGeneric = true , componentTypes = { "Ljava/lang/String;" , "Ljava/lang/Object;" })
    Map<String , Object> fieldDemo;

    public void getClassGenericDefineTesting() {
        GenericInfo genericInfo = ClassOperation.getClassGenericInfo(GenericTest.class);
        Assert.assertThat(genericInfo.isGeneiric , Matchers.is(true));
        Assert.assertThat(genericInfo.declareType == GenericTest.class , Matchers.is(true));
        Assert.assertThat(genericInfo.componentTypes != null , Matchers.is(true));

        GenericInfo[] infos = genericInfo.componentTypes;
        Assert.assertThat(infos.length , Matchers.is(1));
        Assert.assertThat(infos[0].isGeneiric , Matchers.is(false));
        Assert.assertThat(infos[0].declareType == Object.class , Matchers.is(true));
        Assert.assertThat(infos[0].componentTypes == null , Matchers.is(true));
    }

    @Test
    public void getFieldGenericDefineTesting() {
        Field field = FieldOperation.getDeclaredField(GenericTest.class , "fieldDemo");
        GenericInfo genericInfo = FieldOperation.getFieldGenericInfo(field);
        Assert.assertThat(genericInfo.isGeneiric , Matchers.is(true));
        Assert.assertThat(genericInfo.declareType == Map.class , Matchers.is(true));
        Assert.assertThat(genericInfo.componentTypes != null , Matchers.is(true));

        GenericInfo[] infos = genericInfo.componentTypes;
        Assert.assertThat(infos.length , Matchers.is(2));
        Assert.assertThat(infos[0].isGeneiric , Matchers.is(false));
        Assert.assertThat(infos[0].declareType == String.class , Matchers.is(true));
        Assert.assertThat(infos[0].componentTypes == null , Matchers.is(true));
        Assert.assertThat(infos[1].isGeneiric , Matchers.is(false));
        Assert.assertThat(infos[1].declareType == Object.class , Matchers.is(true));
        Assert.assertThat(infos[1].componentTypes == null , Matchers.is(true));
    }

    public void
        listParam(@Param(value = "param" , genericInfo = @Generic(isGeneric = true , componentTypes = "Ljava/util/List;<Ljava/util/List;<Ljava/lang/String;>>") ) List<List<List<String>>> param) {

    }
    
    public CompletionStage<Optional<String>> forReturnType(){
        return null;
    }
    
    @Test
    public void testMethodType() {
        Method method = MethodOperation.getDeclaredMethod(GenericTest.class , "listParam" , List.class);
        Type returnType = Type.getReturnType(method);
        Assert.assertThat(returnType.getSort() , Matchers.is(Type.VOID));
        Type[] argumentTypes = Type.getArgumentTypes(method);
        Assert.assertThat(argumentTypes.length , Matchers.is(1));
        Assert.assertThat(argumentTypes[0].getDescriptor() , Matchers.is("Ljava/util/List;"));
        Assert.assertThat(argumentTypes[0].getClassName() , Matchers.is("java.util.List"));
        Assert.assertThat(Type.getMethodDescriptor(method), Matchers.is("(Ljava/util/List;)V"));
        
        method = MethodOperation.getDeclaredMethod(GenericTest.class , "forReturnType");
        System.out.println(Type.getMethodDescriptor(method));
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
