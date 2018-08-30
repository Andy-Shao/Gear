package com.github.andyshao.lang;

import java.util.Comparator;
import java.util.Objects;

import com.github.andyshao.reflect.ArrayOperation;

/**
 * 
 * Title: some usefu tools of java.lang.String<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 28, 2014<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class StringOperation {
    public static final Comparator<String> COMPARATOR = (String str1 , String str2) -> {
        if (str1 != null) return str1.compareTo(str2);
        else if (str2 != null) return str2.compareTo(str1);
        else return 0;
    };

    /**
     * 
     * @param str a string which should be flipped
     * @return A string which has flipped.
     */
    public static String flipString(String str) {
        return new String(ArrayOperation.flipArray(str.toCharArray()));
    }

    /**
     * 
     * @param str a string which should be tested.
     * @return if the str is null or "", return the true
     */
    public static boolean isEmptyOrNull(String str) {
        return str == null || str.isEmpty();
    }
    
    /**
     * 
     * @param str a string which should be tested
     * @return if str is null or after {@link String#trim()} and {@link String#isEmpty()} return true
     */
    public static boolean isTrimEmptyOrNull(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * more efficient<br>
     * replace the string
     * 
     * @param str the string which will be process
     * @param padding the right words will be inputed
     * @param index the right words will be inputed
     * @param length the length of string which should be replaced
     * @return the end of the string
     */
    static String replace(String str , String padding , int index , int length) {
        String result;
        if (index == -1) result = str;
        else if (index == 0) result = padding + str.substring(length);
        else if (index + 1 >= str.length()) result = str.substring(0 , index) + padding;
        else result = str.substring(0 , index) + padding + str.substring(index + length);

        return result;
    }

    /**
     * more efficient<br>
     * replace all of the key words
     * 
     * @param str the string which will be process
     * @param key the key words will be instead
     * @param padding the right words will be inputed
     * @return the end of the string
     */
    public static String replaceAll(String str , String key , String padding) {
        if(Objects.equals(key , padding)) return str;
        while (str.indexOf(key) != -1)
            str = StringOperation.replaceFirst(str , key , padding);
        return str;
    }

    /**
     * more efficient<br>
     * only replace the first time occur
     * 
     * @param str the string which will be process
     * @param key the key words will be insted
     * @param padding the right words will be inputed
     * @return the end of the string
     */
    public static String replaceFirst(String str , String key , String padding) {
        return StringOperation.replace(str , padding , str.indexOf(key) , key.length());
    }

    /**
     * more efficient<br>
     * only replace the last time occur
     * 
     * @param str the string which will be process
     * @param key the key words will be insted
     * @param padding the right words will be inputed
     * @return the end of the string
     */
    public static String replaceLast(String str , String key , String padding) {
        return StringOperation.replace(str , padding , str.lastIndexOf(key) , key.length());
    }

    /**
     * more efficient<br>
     * split the string by separator
     * 
     * @param str the string which will be process
     * @param separator the str will be splited by it
     * @return the end of string
     */
    public static String[] split(String str , String separator) {
        String[] result = new String[0];
        for (int index ; (index = str.indexOf(separator)) != -1 ;) {
            result = ArrayOperation.mergeArray(String[].class , result , new String[] { str.substring(0 , index) });
            str = str.substring(index + separator.length());
        }
        if (str.length() != 0) result = ArrayOperation.mergeArray(String[].class , result , new String[] { str });

        return result;
    }

    private StringOperation() {
        throw new AssertionError("Not allowed to instance " + StringOperation.class.getName());
    }
}
