package com.github.andyshao.io;

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
public interface MessageProcess {

    /**
     * Process the message
     * 
     * @param context the message context
     * @throws Exception any kinds of exception
     */
    public void process(MessageContext context) throws Exception;
}
