package com.github.andyshao.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;

import com.github.andyshao.reflect.SignatureDetector.ClassSignature;

public class MethodOperationTest {

    @Test
    public void testgetReturnTypeInfo() {
        Method method = MethodOperation.getDeclaredMethod(Data.class , "testMethod" , List.class, int.class, String.class, Map.class);
        GenericNode returnType = MethodOperation.getReturnTypeInfo(method);
        GenericNode node;
        assertEquals(returnType.isGeneiric() , (true));
        assertTrue(returnType.getDeclareType() == Optional.class);
        assertEquals(returnType.getComponentTypes().size() , (1));
        node = returnType.getComponentTypes().get(0);
        assertTrue(node.isGeneiric());
        assertTrue(node.getDeclareType() == CompletionStage.class);
        assertEquals(node.getComponentTypes().size() , (1));
        node = node.getComponentTypes().get(0);
        assertTrue(node.isGeneiric());
        assertTrue(node.getDeclareType() == Map.class);
        assertEquals(node.getComponentTypes().size() , (2));
        List<GenericNode> nodes = node.getComponentTypes();
        node = nodes.get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == String.class);
        assertEquals(node.getComponentTypes().size() , (0));
        node = nodes.get(1);
        assertTrue(node.isGeneiric());
        assertTrue(node.getDeclareType() == List.class);
        assertEquals(node.getComponentTypes().size() , (1));
        node = node.getComponentTypes().get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == String.class);
        assertEquals(node.getComponentTypes().size() , (0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intMethod" , List.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        assertEquals(returnType.isGeneiric() , (false));
        assertEquals(returnType.getComponentTypes().size(), (0));
        assertTrue(returnType.getDeclareType() == int.class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intArrayMethod");
        returnType = MethodOperation.getReturnTypeInfo(method);
        assertEquals(returnType.isGeneiric() , (false));
        assertEquals(returnType.getComponentTypes().size() , (0));
        assertTrue(returnType.getDeclareType() == int[].class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "integerMethod" , long[].class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        assertEquals(returnType.isGeneiric() , (false));
        assertEquals(returnType.getComponentTypes().size() , (0));
        assertTrue(returnType.getDeclareType() == Integer.class);;
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intergerArrayMethod" , String.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        assertEquals(returnType.isGeneiric() , (false));
        assertEquals(returnType.getComponentTypes().size(), (0));
        assertTrue(returnType.getDeclareType() == Integer[].class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "listArrayMethod");
        returnType = MethodOperation.getReturnTypeInfo(method);
        assertEquals(returnType.isGeneiric() , (true));
        assertTrue(returnType.getDeclareType() == List[].class);
        assertEquals(returnType.getComponentTypes().size() , (1));
        node = returnType.getComponentTypes().get(0);
        assertEquals(node.isGeneiric(), (false));
        assertEquals(node.getComponentTypes().size() , (0));
        assertTrue(node.getDeclareType() == Object.class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "charParamMethod" , char.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        assertFalse(returnType.isGeneiric());
        assertEquals(returnType.getComponentTypes().size() , (0));
        assertTrue(returnType.getDeclareType() == void.class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "voidMethod", String.class, double.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        assertFalse(returnType.isGeneiric());
        assertEquals(returnType.getComponentTypes().size() , (0));
        assertTrue(returnType.getDeclareType() == Void.class);
        
        method = MethodOperation.getMethod(Data.class , "get" , int.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        assertFalse(returnType.isGeneiric());
        assertEquals(returnType.getComponentTypes().size() , (0));
        assertTrue(returnType.getDeclareType() == null);
        
        method = MethodOperation.getMethod(Data.class , "process" , Object.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        assertFalse(returnType.isGeneiric());
        assertEquals(returnType.getComponentTypes().size() , (0));
        assertTrue(returnType.getDeclareType() == null);
        
        method = MethodOperation.getMethod(Data.class , "process" , List.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        assertTrue(returnType.isGeneiric());
        assertEquals(returnType.getComponentTypes().size(), (1));
        assertTrue(returnType.getDeclareType() == List.class);
        node = returnType.getComponentTypes().get(0);
        assertFalse(node.isGeneiric());
        assertEquals(node.getComponentTypes().size() , (0));
        assertTrue(node.getDeclareType() == null);
        
        method = MethodOperation.getMethod(Data.class , "process" , Map.class);
        returnType = MethodOperation.getReturnTypeInfo(method);
        assertTrue(returnType.isGeneiric());
        assertEquals(returnType.getComponentTypes().size(), (2));
        assertTrue(returnType.getDeclareType() == Map.class);
        node = returnType.getComponentTypes().get(0);
        assertFalse(node.isGeneiric());
        assertEquals(node.getComponentTypes().size() , (0));
        assertTrue(node.getDeclareType() == null);
        assertEquals(node.getTypeVariable() , ("E"));
        node = returnType.getComponentTypes().get(1);
        assertFalse(node.isGeneiric());
        assertEquals(node.getComponentTypes().size() , (0));
        assertTrue(node.getDeclareType() == null);
        assertEquals(node.getTypeVariable() , ("D"));
    }
    
    @Test
    public void testGetParameterTypesInfo() {
        ClassSignature signature = new SignatureDetector(Opcodes.ASM6).getSignature(Data.class);
        Method method = MethodOperation.getDeclaredMethod(Data.class , "testMethod" , List.class, int.class, String.class, Map.class);
        List<GenericNode> parameterTypesInfo = MethodOperation.getParameterTypesInfo(method , signature);
        assertEquals(parameterTypesInfo.size() , (4));
        GenericNode node = parameterTypesInfo.get(0);
        assertTrue(node.isGeneiric());
        assertTrue(node.getDeclareType() == List.class);
        assertEquals(node.getComponentTypes().size() , (1));
        node = node.getComponentTypes().get(0);
        assertTrue(node.isGeneiric());
        assertTrue(node.getDeclareType() == List.class);
        assertEquals(node.getComponentTypes().size() , (1));
        node = node.getComponentTypes().get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == Integer.class);
        assertEquals(node.getComponentTypes().size() , (0));
        node = parameterTypesInfo.get(1);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == int.class);
        assertEquals(node.getComponentTypes().size(), (0));
        node = parameterTypesInfo.get(2);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == String.class);
        assertEquals(node.getComponentTypes().size() , (0));
        node = parameterTypesInfo.get(3);
        assertTrue(node.isGeneiric());
        assertTrue(node.getDeclareType() == Map.class);
        assertEquals(node.getComponentTypes().size() , (2));
        List<GenericNode> nodes = node.getComponentTypes();
        node = nodes.get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == String.class);
        assertEquals(node.getComponentTypes().size() , (0));
        node = nodes.get(1);
        assertTrue(node.isGeneiric());
        assertTrue(node.getDeclareType() == List.class);
        assertEquals(node.getComponentTypes().size() , (1));
        node = node.getComponentTypes().get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == String.class);
        assertEquals(node.getComponentTypes().size() , (0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intMethod" , List.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method , signature);
        assertTrue(parameterTypesInfo.size() == 1);
        node = parameterTypesInfo.get(0);
        assertTrue(node.isGeneiric());
        assertTrue(node.getDeclareType() == List.class);
        assertEquals(node.getComponentTypes().size() , (1));
        node = node.getComponentTypes().get(0);
        assertTrue(node.isGeneiric());
        assertTrue(node.getDeclareType() == List.class);
        assertEquals(node.getComponentTypes().size() , (1));
        node = node.getComponentTypes().get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == Integer.class);
        assertEquals(node.getComponentTypes().size() , (0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intArrayMethod");
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method , signature);
        assertEquals(parameterTypesInfo.size() , (0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "integerMethod" , long[].class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method , signature);
        assertEquals(parameterTypesInfo.size() , (1));
        node = parameterTypesInfo.get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == long[].class);
        assertEquals(node.getComponentTypes().size() , (0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "intergerArrayMethod" , String.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method , signature);
        assertEquals(parameterTypesInfo.size() , (1));
        node = parameterTypesInfo.get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == String.class);
        assertEquals(node.getComponentTypes().size() , (0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "mapMethod" , Map.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method , signature);
        assertEquals(parameterTypesInfo.size() , (1));
        node = parameterTypesInfo.get(0);
        assertTrue(node.isGeneiric());
        assertTrue(node.getDeclareType() == Map.class);
        assertEquals(node.getComponentTypes().size() , (2));
        nodes = node.getComponentTypes();
        node = nodes.get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == String.class);
        assertEquals(node.getComponentTypes().size() , (0));
        node = nodes.get(1);
        assertTrue(node.isGeneiric());
        assertTrue(node.getDeclareType() == List[].class);
        assertEquals(node.getComponentTypes().size() , (1));
        node = node.getComponentTypes().get(0);
        assertEquals(node.isGeneiric() , (false));
        assertTrue(node.getDeclareType() == String.class);
        
        method = MethodOperation.getDeclaredMethod(Data.class , "charParamMethod" , char.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method);
        assertEquals(parameterTypesInfo.size() , (1));
        node = parameterTypesInfo.get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == char.class);
        assertEquals(node.getComponentTypes().size() , (0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "voidMethod" , String.class, double.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method);
        assertEquals(parameterTypesInfo.size() , (2));
        node = parameterTypesInfo.get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == String.class);
        assertEquals(node.getComponentTypes().size() , (0));
        node = parameterTypesInfo.get(1);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == double.class);
        assertEquals(node.getComponentTypes().size() , (0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "process" , Object.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method);
        assertEquals(parameterTypesInfo.size() , (1));
        node = parameterTypesInfo.get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == null);
        assertEquals(node.getComponentTypes().size() , (0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "process" , List.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method);
        assertEquals(parameterTypesInfo.size() , (1));
        node = parameterTypesInfo.get(0);
        assertTrue(node.isGeneiric());
        assertTrue(node.getDeclareType() == List.class);
        assertEquals(node.getComponentTypes().size() , (1));
        node = node.getComponentTypes().get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == null);
        assertEquals(node.getComponentTypes().size() , (0));
        
        method = MethodOperation.getDeclaredMethod(Data.class , "process" , Map.class);
        parameterTypesInfo = MethodOperation.getParameterTypesInfo(method);
        assertEquals(parameterTypesInfo.size() , (1));
        node = parameterTypesInfo.get(0);
        assertTrue(node.isGeneiric());
        assertTrue(node.getDeclareType() == Map.class);
        assertEquals(node.getComponentTypes().size() , (2));
        nodes = node.getComponentTypes();
        node = nodes.get(0);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == null);
        assertEquals(node.getComponentTypes().size() , (0));
        assertEquals(node.getTypeVariable() , ("E"));
        node = nodes.get(1);
        assertFalse(node.isGeneiric());
        assertTrue(node.getDeclareType() == null);
        assertEquals(node.getComponentTypes().size() , (0));
        assertEquals(node.getTypeVariable() , ("D"));
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
