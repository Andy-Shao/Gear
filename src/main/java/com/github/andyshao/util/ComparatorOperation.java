package com.github.andyshao.util;

import java.util.Comparator;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jun 13, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ComparatorOperation {
    public static Comparator<Byte> BYTE = (x , y) -> Byte.compare(x , y);
    public static Comparator<Character> CHARACTER = (x , y) -> Character.compare(x , y);
    public static Comparator<Double> DOUBLE = (x , y) -> Double.compare(x , y);
    public static Comparator<Float> FLOAT = (x , y) -> Float.compare(x , y);
    public static Comparator<Integer> INTEGER = (x , y) -> Integer.compare(x , y);
    public static Comparator<Long> LONG = (x , y) -> Long.compare(x , y);
    public static Comparator<Short> SHORT = (x , y) -> Short.compare(x , y);
    public static final Comparator<String> STRING = (String str1 , String str2) -> {
        if (str1 != null) return str1.compareTo(str2);
        else if (str2 != null) return str2.compareTo(str1);
        else return 0;
    };

    private ComparatorOperation() {
        throw new AssertionError("No " + ComparatorOperation.class + " installment for you!");
    }
}
