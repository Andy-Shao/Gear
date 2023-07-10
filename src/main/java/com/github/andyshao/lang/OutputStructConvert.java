package com.github.andyshao.lang;

/**
 * 
 * Title: Adding Output factory argument in Convert<br>
 * Descript:<br>
 * Copyright: Copryright(c) 5 Jun 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 * @param <IN> the input
 * @param <OUT> the output
 */
public interface OutputStructConvert<IN , OUT> extends Convert<IN , OUT> {
    /**
     * convert operation
     * @param in input
     * @param factory {@link OutputFactory}
     * @return the output
     */
    OUT convert(IN in, OutputFactory<OUT> factory);

    /**
     * output factory
     * @param <E> data type
     */
    @FunctionalInterface
    interface OutputFactory<E> {
        E build();
    }
 }
