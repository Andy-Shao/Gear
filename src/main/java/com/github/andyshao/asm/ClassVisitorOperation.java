package com.github.andyshao.asm;

import java.lang.reflect.Method;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 20, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ClassVisitorOperation {
    public static final int countBasicLocal(Method method) {
        int result = method.getParameterCount() + 1;
        Class<?>[] types = method.getParameterTypes();
        for (Class<?> parameter : types)
            if (long.class.isAssignableFrom(parameter) || double.class.isAssignableFrom(parameter)) result++;
        return result;
    }

    public static final int countBasicStack(Method method) {
        Class<?> type = method.getReturnType();
        if (type.isPrimitive()) {
            if (long.class.isAssignableFrom(type) || double.class.isAssignableFrom(type)) return 2;
            else if (void.class.isAssignableFrom(type)) return 0;
            else return 1;
        } else return 1;
    }

    private ClassVisitorOperation() {
        throw new AssertionError("No " + ClassVisitorOperation.class + " instance for you");
    }
}
