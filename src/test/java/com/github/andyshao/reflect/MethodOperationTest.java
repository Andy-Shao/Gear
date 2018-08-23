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
        ClassSignature signature = new SignatureDetector(Opcodes.ASM6).getSignature(Data.class);
        Method method = MethodOperation.getDeclaredMethod(Data.class , "testMethod" , List.class, int.class, String.class, Map.class);
        GenericNode returnType = MethodOperation.getReturnTypeInfo(method , signature);
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
        returnType = MethodOperation.getReturnTypeInfo(method, signature);
        Assert.assertThat(returnType.isGeneiric() , Matchers.is(false));
        Assert.assertThat(returnType.getComponentTypes().size(), Matchers.is(0));
        Assert.assertTrue(returnType.getDeclareType() == int.class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intArrayMethod");
        returnType = MethodOperation.getReturnTypeInfo(method , signature);
        Assert.assertThat(returnType.isGeneiric() , Matchers.is(false));
        Assert.assertThat(returnType.getComponentTypes().size() , Matchers.is(0));
        Assert.assertTrue(returnType.getDeclareType() == int[].class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "integerMethod" , long[].class);
        returnType = MethodOperation.getReturnTypeInfo(method , signature);
        Assert.assertThat(returnType.isGeneiric() , Matchers.is(false));
        Assert.assertThat(returnType.getComponentTypes().size() , Matchers.is(0));
        Assert.assertTrue(returnType.getDeclareType() == Integer.class);;
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intergerArrayMethod" , String.class);
        returnType = MethodOperation.getReturnTypeInfo(method , signature);
        Assert.assertThat(returnType.isGeneiric() , Matchers.is(false));
        Assert.assertThat(returnType.getComponentTypes().size(), Matchers.is(0));
        Assert.assertTrue(returnType.getDeclareType() == Integer[].class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "listArrayMethod");
        returnType = MethodOperation.getReturnTypeInfo(method , signature);
        Assert.assertThat(returnType.isGeneiric() , Matchers.is(true));
        Assert.assertTrue(returnType.getDeclareType() == List[].class);
        Assert.assertThat(returnType.getComponentTypes().size() , Matchers.is(1));
        node = returnType.getComponentTypes().get(0);
        Assert.assertThat(node.isGeneiric(), Matchers.is(false));
        Assert.assertThat(node.getComponentTypes().size() , Matchers.is(0));
        Assert.assertTrue(node.getDeclareType() == Object.class);
    }
    
    public interface Data<T> extends Map<String, T> {
        Optional<CompletionStage<Map<String, List<String>>>> testMethod(List<List<Integer>> arg1, int arg2, String str, Map<String , List<String>> map);
        int intMethod(List<List<Integer>> arg1);
        int[] intArrayMethod();
        Integer integerMethod(long[] arg);
        Integer[] intergerArrayMethod(String arg);
        List<Object>[] listArrayMethod();
        Map<String, List<String>[]> mapMethod(Map<String, List<String>[]> arg);
    }
}
