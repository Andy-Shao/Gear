package com.github.andyshao.lang;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 5, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class GenericClassLoader extends ClassLoader {
    public Class<?> defineClass(String name , byte[] b) {
        return this.defineClass(name , b , 0 , b.length);
    }
}
