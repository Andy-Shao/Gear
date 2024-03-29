package com.github.andyshao.reflect;

import com.github.andyshao.asm.TypeOperation;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 4, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
@Deprecated
public final class GenericInfo {
    /**
     * analyze script
     * @param script script array
     * @return {@link GenericInfo} array
     */
    @Deprecated
    public static GenericInfo[] analyseScript(String... script) {
        GenericInfo[] result = new GenericInfo[script.length];
        for (int i = 0 ; i < result.length ; i++) {
            GenericInfo genericInfo = new GenericInfo();
            result[i] = genericInfo;
            genericInfo.declareType = TypeOperation.getClass(Type.getType(script[i]));
            if (script[i].indexOf('<') != -1 && script[i].indexOf('>') != -1) genericInfo.isGeneiric = true;
            else genericInfo.isGeneiric = false;
            if (genericInfo.isGeneiric) {
                int head = script[i].indexOf('<');
                int tail = script[i].lastIndexOf('>');
                genericInfo.componentTypes = GenericInfo.analyseScript(GenericInfo.splitScript(script[i].substring(head + 1 , tail)));
            }
        }
        return result;
    }

    /**
     * split script
     * @param script script
     * @return scripts
     */
    @Deprecated
    static String[] splitScript(String script) {
        List<String> result = new ArrayList<>();
        while (true) {
            script = script.trim();
            if (script.length() == 0) break;
            int firstTail = script.indexOf('>');
            if (firstTail == -1) firstTail = script.indexOf(';');
            if (firstTail == script.length() - 1 || firstTail == -1) {
                result.add(script);
                break;
            } else {
                result.add(script.substring(0 , firstTail + 1));
                script = script.substring(firstTail + 1);
            }
        }
        return result.toArray(new String[result.size()]);
    }

    /**component types*/
    public GenericInfo[] componentTypes = null;
    /**declared type*/
    public Class<?> declareType = null;
    /**generic tag*/
    public boolean isGeneiric = false;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GenericInfo) {
            GenericInfo that = (GenericInfo) obj;
            return Objects.equals(this.isGeneiric , that.isGeneiric) && Arrays.deepEquals(this.componentTypes , that.componentTypes) && Objects.equals(this.declareType , that.declareType);
        } else return false;
    }

    @Override
    public int hashCode() {
        int hashCode = Objects.hash(this.isGeneiric , this.declareType);
        hashCode = 31 * hashCode + (this.componentTypes == null ? 0 : this.componentTypes.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "Generic [declareType=" + this.declareType + ", componentTypes=" + Arrays.toString(this.componentTypes) + ", isGeneiric=" + this.isGeneiric + "]";
    }
}
