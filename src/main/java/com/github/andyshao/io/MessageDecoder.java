package com.github.andyshao.io;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 4, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
@FunctionalInterface
public interface MessageDecoder {
    /**
     * Decode the message
     * 
     * @param context the message context
     * @throws IOException any kinds of IO exception
     * @throws UnsupportedEncodingException if the encoding is unsupported
     */
    public void decode(MessageContext context) throws IOException , UnsupportedEncodingException;
}
