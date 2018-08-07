package com.github.andyshao.reflect;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.github.andyshao.reflect.annotation.Generic;
import com.github.andyshao.reflect.annotation.Param;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jan 24, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ParameterOperation {
    public static GenericInfo[] getMethodParamGenricInfo(Method method) {
        GenericInfo[] result = new GenericInfo[method.getParameterCount()];
        Parameter[] parameters = method.getParameters();
        for (int i = 0 ; i < result.length ; i++) {
            Parameter parameter = parameters[i];
            Param param = parameter.getAnnotation(Param.class);
            if (param == null) continue;
            Generic generic = param.genericInfo();
            GenericInfo genericInfo = new GenericInfo();
            genericInfo.isGeneiric = generic.isGeneric();
            genericInfo.componentTypes = GenericInfo.analyseScript(generic.componentTypes());
            genericInfo.declareType = parameter.getType();
            result[i] = genericInfo;
        }
        return result;
    }
    
    public static GenericInfo getGenericInfoByParam(Parameter parameter) {
        Param param = parameter.getAnnotation(Param.class);
        if (param == null) return null;
        Generic generic = param.genericInfo();
        GenericInfo genericInfo = new GenericInfo();
        genericInfo.isGeneiric = generic.isGeneric();
        genericInfo.componentTypes = GenericInfo.analyseScript(generic.componentTypes());
        genericInfo.declareType = parameter.getType();
        return genericInfo;
    }

    /**
     * 
     * <p>
     * Gets the parameter name of method's
     * </p>
     * <br>
     * <font style="color:red;">NOTE: Cannot support interface</font>
     *
     * @param m method
     * @return the parameter name list
     */
    public static String[] getMethodParamNames(final Method m) {
        if (m.getDeclaringClass().isInterface()) throw new ReflectiveOperationException("No Support the interface!");
        final String[] paramNames = new String[m.getParameterTypes().length];
        final String path = m.getDeclaringClass().getName().replace('.' , '/') + ".class";
        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassReader cr = null;
        try (InputStream inputStream = m.getDeclaringClass().getClassLoader().getResourceAsStream(path)) {
            cr = new ClassReader(inputStream);
        } catch (IOException e) {
            throw new ClassNotFoundException(path);
        }
        cr.accept(new ClassVisitor(Opcodes.ASM6 , cw) {
            @Override
            public MethodVisitor visitMethod(final int access , final String name , final String desc , final String signature , final String[] exceptions) {
                final Type[] args = Type.getArgumentTypes(desc);
                //Same method name and parameter number
                if (!name.equals(m.getName()) || !ParameterOperation.sameType(args , m.getParameterTypes())) return super.visitMethod(access , name , desc , signature , exceptions);
                MethodVisitor v = this.cv.visitMethod(access , name , desc , signature , exceptions);
                return new MethodVisitor(Opcodes.ASM6 , v) {
                    @Override
                    public void visitLocalVariable(String name , String desc , String signature , Label start , Label end , int index) {
                        int i = index - 1;
                        //If it is static method then it is the first one.
                        //If it is not static method then the first one is 'this' and next is parameter of method's
                        if (Modifier.isStatic(m.getModifiers())) i = index;
                        if (i >= 0 && i < paramNames.length) paramNames[i] = name;
                        super.visitLocalVariable(name , desc , signature , start , end , index);
                    }

                };
            }
        } , 0);
        return paramNames;
    }

    public static String[] getParamNamesByAnnotation(Method method) {
        String[] result = new String[method.getParameterCount()];
        Parameter[] parameters = method.getParameters();
        for (int i = 0 ; i < result.length ; i++) {
            Parameter parameter = parameters[i];
            Param param = parameter.getAnnotation(Param.class);
            if (param != null) result[i] = param.value();
        }
        return result;
    }

    /**
     * 
     * <p>
     * Comparing the parameters' types
     * </p>
     *
     * @param types ASM type({@link Type})
     * @param clazzes java type({@link Class})
     * @return if it is equals then return true
     */
    private static boolean sameType(Type[] types , Class<?>[] clazzes) {
        if (types.length != clazzes.length) return false;

        for (int i = 0 ; i < types.length ; i++)
            if (!Type.getType(clazzes[i]).equals(types[i])) return false;
        return true;
    }

    private ParameterOperation() {
        throw new AssertionError("No support instance " + ParameterOperation.class + " for you!");
    }
}
