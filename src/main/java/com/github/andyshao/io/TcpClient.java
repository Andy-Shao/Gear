package com.github.andyshao.io;

import java.io.Closeable;
import java.io.IOException;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 9, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public interface TcpClient extends Closeable {

    @Override
    public abstract void close() throws IOException;

    public abstract void open(MessageContext context) throws IOException;

    public abstract void send(MessageContext context) throws IOException;

    public abstract void send(MessageContext context , MessageProcess process) throws IOException;

}