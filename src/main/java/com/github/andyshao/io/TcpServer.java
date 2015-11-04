package com.github.andyshao.io;

import java.io.Closeable;
import java.io.IOException;

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
public interface TcpServer extends Closeable {

    /**
     * open and waiting for accept
     * 
     * @throws IOException any kinds of IO exception
     */
    public abstract void open() throws IOException;
}