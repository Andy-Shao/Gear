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

    /**
     * open the connection
     * @param context {@link MessageContext}
     * @throws IOException any IO error
     */
    public abstract void open(MessageContext context) throws IOException;

    /**
     * send the message
     * @param context {@link MessageContext}
     * @throws IOException any IO error
     */
    public abstract void send(MessageContext context) throws IOException;

    /**
     * send the message
     * @param context {@link MessageContext}
     * @param process {@link MessageProcess}
     * @throws IOException any IO error
     */
    public abstract void send(MessageContext context , MessageProcess process) throws IOException;

}