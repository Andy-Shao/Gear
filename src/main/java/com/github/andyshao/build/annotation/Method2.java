package com.github.andyshao.build.annotation;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 11, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
class Method2 {
    private final Method method;

    public Method2(Method method) {
        this.method = method;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Method2) {
            Method2 that = (Method2) obj;
            if (Objects.equals(this.method , that.method)) return true;
            return Objects.equals(this.method.getName() , that.method.getName())
                && Objects.equals(this.method.getParameterTypes() , that.method.getParameterTypes());
        }
        return false;
    }

    public Method getMethod() {
        return this.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.method);
    }
}
