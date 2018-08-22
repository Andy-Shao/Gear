package com.github.andyshao.reflect;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import com.github.andyshao.reflect.SignatureDetector.ClassSignature;

public class MethodOperationTest {

    @Test
    public void testgetReturnTypeInfo() {
        ClassSignature signature = new SignatureDetector(Opcodes.ASM6).getSignature(Data.class);
        Method method = MethodOperation.getDeclaredMethod(Data.class , "testMethod" , List.class, int.class, String.class, Map.class);
        MethodOperation.getReturnTypeInfo(method , signature);
    }
    
    public interface Data<T> extends Map<String, T> {
        Optional<CompletionStage<List<String>>> testMethod(List<List<Integer>> arg1, int arg2, String str, Map<String , List<String>> map);
        int intMethod(List<List<Integer>> arg1);
    }
    
    public class DataImpl {
        SoftReference<Map<String, Set<Optional<Long>>>> targs;
    }
}
