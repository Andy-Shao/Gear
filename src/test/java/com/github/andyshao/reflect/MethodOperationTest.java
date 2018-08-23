package com.github.andyshao.reflect;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.Opcodes;

import com.github.andyshao.reflect.SignatureDetector.ClassSignature;

public class MethodOperationTest {

    @Test
    public void testgetReturnTypeInfo() {
        Method method = MethodOperation.getDeclaredMethod(Data.class , "testMethod" , List.class, int.class, String.class, Map.class);
        GenericNode returnType = MethodOperation.getReturnTypeInfo(method);
        GenericNode node;
        Assert.assertThat(returnType.isGeneiric() , Matchers.is(true));
        Assert.assertTrue(returnType.getDeclareType() == Optional.class);
        Assert.assertThat(returnType.getComponentTypes().size() , Matchers.is(1));
        node = returnType.getComponentTypes().get(0);
        Assert.assertTrue(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == CompletionStage.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(1));
        node = node.getComponentTypes().get(0);
        Assert.assertTrue(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == Map.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(2));
        List<GenericNode> nodes = node.getComponentTypes();
        node = nodes.get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == String.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        node = nodes.get(1);
        Assert.assertTrue(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == List.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(1));
        node = node.getComponentTypes().get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == String.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intMethod" , List.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        Assert.assertThat(returnType.isGeneiric() , Matchers.is(false));
        Assert.assertThat(returnType.getComponentTypes().size(), Matchers.is(0));
        Assert.assertTrue(returnType.getDeclareType() == int.class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intArrayMethod");
        returnType = MethodOperation.getReturnTypeInfo(method);
        Assert.assertThat(returnType.isGeneiric() , Matchers.is(false));
        Assert.assertThat(returnType.getComponentTypes().size() , Matchers.is(0));
        Assert.assertTrue(returnType.getDeclareType() == int[].class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "integerMethod" , long[].class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        Assert.assertThat(returnType.isGeneiric() , Matchers.is(false));
        Assert.assertThat(returnType.getComponentTypes().size() , Matchers.is(0));
        Assert.assertTrue(returnType.getDeclareType() == Integer.class);;
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intergerArrayMethod" , String.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        Assert.assertThat(returnType.isGeneiric() , Matchers.is(false));
        Assert.assertThat(returnType.getComponentTypes().size(), Matchers.is(0));
        Assert.assertTrue(returnType.getDeclareType() == Integer[].class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "listArrayMethod");
        returnType = MethodOperation.getReturnTypeInfo(method);
        Assert.assertThat(returnType.isGeneiric() , Matchers.is(true));
        Assert.assertTrue(returnType.getDeclareType() == List[].class);
        Assert.assertThat(returnType.getComponentTypes().size() , Matchers.is(1));
        node = returnType.getComponentTypes().get(0);
        Assert.assertThat(node.isGeneiric(), Matchers.is(false));
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        Assert.assertTrue(node.getDeclareType() == Object.class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "charParamMethod" , char.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        Assert.assertFalse(returnType.isGeneiric());
        Assert.assertThat(returnType.getComponentTypes().size() , Matchers.is(0));
        Assert.assertTrue(returnType.getDeclareType() == void.class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "voidMethod", String.class, double.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        Assert.assertFalse(returnType.isGeneiric());
        Assert.assertThat(returnType.getComponentTypes().size() , Matchers.is(0));
        Assert.assertTrue(returnType.getDeclareType() == Void.class);
        
        method = MethodOperation.getMethod(Data.class , "get" , int.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        Assert.assertFalse(returnType.isGeneiric());
        Assert.assertThat(returnType.getComponentTypes().size() , Matchers.is(0));
        Assert.assertTrue(returnType.getDeclareType() == null);
        
        method = MethodOperation.getMethod(Data.class , "process" , Object.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        Assert.assertFalse(returnType.isGeneiric());
        Assert.assertThat(returnType.getComponentTypes().size() , Matchers.is(0));
        Assert.assertTrue(returnType.getDeclareType() == null);
        
        method = MethodOperation.getMethod(Data.class , "process" , List.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        Assert.assertTrue(returnType.isGeneiric());
        Assert.assertThat(returnType.getComponentTypes().size(), Matchers.is(1));
        Assert.assertTrue(returnType.getDeclareType() == List.class);
        node = returnType.getComponentTypes().get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        Assert.assertTrue(node.getDeclareType() == null);
        
        method = MethodOperation.getMethod(Data.class , "process" , Map.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        Assert.assertTrue(returnType.isGeneiric());
        Assert.assertThat(returnType.getComponentTypes().size(), Matchers.is(2));
        Assert.assertTrue(returnType.getDeclareType() == Map.class);
        node = returnType.getComponentTypes().get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        Assert.assertTrue(node.getDeclareType() == null);
        Assert.assertThat(node.getTypeVariable() , Matchers.is("E"));
        node = returnType.getComponentTypes().get(1);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        Assert.assertTrue(node.getDeclareType() == null);
        Assert.assertThat(node.getTypeVariable() , Matchers.is("D"));
    }
    
    @Test
    public void testGetParameterTypesInfo() {
        ClassSignature signature = new SignatureDetector(Opcodes.ASM6).getSignature(Data.class);
        Method method = MethodOperation.getDeclaredMethod(Data.class , "testMethod" , List.class, int.class, String.class, Map.class);
        List<GenericNode> parameterTypesInfo = MethodOperation.getParameterTypesInfo(method , signature);
        Assert.assertThat(parameterTypesInfo.size() , Matchers.is(4));
        GenericNode node = parameterTypesInfo.get(0);
        Assert.assertTrue(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == List.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(1));
        node = node.getComponentTypes().get(0);
        Assert.assertTrue(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == List.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(1));
        node = node.getComponentTypes().get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == Integer.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        node = parameterTypesInfo.get(1);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == int.class);
        Assert.assertThat(node.getComponentTypes().size(), Matchers.is(0));
        node = parameterTypesInfo.get(2);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == String.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        node = parameterTypesInfo.get(3);
        Assert.assertTrue(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == Map.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(2));
        List<GenericNode> nodes = node.getComponentTypes();
        node = nodes.get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == String.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        node = nodes.get(1);
        Assert.assertTrue(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == List.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(1));
        node = node.getComponentTypes().get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == String.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intMethod" , List.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method , signature);
        Assert.assertTrue(parameterTypesInfo.size() == 1);
        node = parameterTypesInfo.get(0);
        Assert.assertTrue(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == List.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(1));
        node = node.getComponentTypes().get(0);
        Assert.assertTrue(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == List.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(1));
        node = node.getComponentTypes().get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == Integer.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intArrayMethod");
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method , signature);
        Assert.assertThat(parameterTypesInfo.size() , Matchers.is(0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "integerMethod" , long[].class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method , signature);
        Assert.assertThat(parameterTypesInfo.size() , Matchers.is(1));
        node = parameterTypesInfo.get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == long[].class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intergerArrayMethod" , String.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method , signature);
        Assert.assertThat(parameterTypesInfo.size() , Matchers.is(1));
        node = parameterTypesInfo.get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == String.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "mapMethod" , Map.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method , signature);
        Assert.assertThat(parameterTypesInfo.size() , Matchers.is(1));
        node = parameterTypesInfo.get(0);
        Assert.assertTrue(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == Map.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(2));
        nodes = node.getComponentTypes();
        node = nodes.get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == String.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        node = nodes.get(1);
        Assert.assertTrue(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == List[].class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(1));
        node = node.getComponentTypes().get(0);
        Assert.assertThat(node.isGeneiric() , Matchers.is(false));
        Assert.assertTrue(node.getDeclareType() == String.class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "charParamMethod" , char.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method);
        Assert.assertThat(parameterTypesInfo.size() , Matchers.is(1));
        node = parameterTypesInfo.get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == char.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "voidMethod" , String.class, double.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method);
        Assert.assertThat(parameterTypesInfo.size() , Matchers.is(2));
        node = parameterTypesInfo.get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == String.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        node = parameterTypesInfo.get(1);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == double.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "process" , Object.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method);
        Assert.assertThat(parameterTypesInfo.size() , Matchers.is(1));
        node = parameterTypesInfo.get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == null);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "process" , List.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method);
        Assert.assertThat(parameterTypesInfo.size() , Matchers.is(1));
        node = parameterTypesInfo.get(0);
        Assert.assertTrue(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == List.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(1));
        node = node.getComponentTypes().get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == null);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "process" , Map.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method);
        Assert.assertThat(parameterTypesInfo.size() , Matchers.is(1));
        node = parameterTypesInfo.get(0);
        Assert.assertTrue(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == Map.class);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(2));
        nodes = node.getComponentTypes();
        node = nodes.get(0);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == null);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        Assert.assertThat(node.getTypeVariable() , Matchers.is("E"));
        node = nodes.get(1);
        Assert.assertFalse(node.isGeneiric());
        Assert.assertTrue(node.getDeclareType() == null);
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        Assert.assertThat(node.getTypeVariable() , Matchers.is("D"));
    }
    
    public interface Data<T extends GenericNode> extends List<T> {
        Optional<CompletionStage<Map<String, List<String>>>> testMethod(List<List<Integer>> arg1, int arg2, String str, Map<String , List<String>> map);
        int intMethod(List<List<Integer>> arg1);
        int[] intArrayMethod();
        Integer integerMethod(long[] arg);
        Integer[] intergerArrayMethod(String arg);
        List<Object>[] listArrayMethod();
        Map<String, List<String>[]> mapMethod(Map<String, List<String>[]> arg);
        void charParamMethod(char c);
        Void voidMethod(String arg, double d);
        <E> E process(E arg);
        <E> List<E> process(List<E> arg);
        <E,D> Map<E,D> process(Map<E,D> arg);
    }
}
