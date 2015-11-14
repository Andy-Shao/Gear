package com.github.andyshao.io;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 10, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public interface TcpMessageContext extends MessageContext {
    public static final String INPUT_INET_ADDRESS = "input_inet_address";
    public static final String INPUT_INET_PORT = "input_inet_port";
    public static final String OUTPU_INET_PORT = "output_inet_port";
    public static final String OUTPUT_INET_ADDRESS = "output_inet_address";
}
