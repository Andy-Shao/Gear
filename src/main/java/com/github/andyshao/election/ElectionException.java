package com.github.andyshao.election;

import com.github.andyshao.exception.Result;
import com.github.andyshao.exception.ResultException;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 27, 2018<br>
 * Encoding: UNIX UTF-8
 * @author andy.shao
 *
 */
@SuppressWarnings("serial")
public class ElectionException extends ResultException {

    public ElectionException(Result<?> result) {
        super(result);
    }
    
    public ElectionException(Result<?> result, Throwable e) {
        super(result, e);
    }
}
