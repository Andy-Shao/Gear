package com.github.andyshao.lang;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 7, 2015<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 * @param <E> object type
 */
@FunctionalInterface
public interface ClassAssembly<E> {

    Class<E> assemble(byte[] bs);
}
