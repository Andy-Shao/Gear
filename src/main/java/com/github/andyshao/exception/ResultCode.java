package com.github.andyshao.exception;

import java.io.Serializable;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 22, 2018<br>
 * Encoding: UNIX UTF-8
 * @author andy.shao
 *
 */
public interface ResultCode extends Serializable {

    /**
     * Get the code
     * @return code
     */
    String getCode();

    /**
     * Get the message
     * @return the message
     */
    String getMessage();

}