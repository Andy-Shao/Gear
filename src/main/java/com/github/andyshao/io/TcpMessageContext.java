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
    /**input inet address key*/
    public static final String INPUT_INET_ADDRESS = "input_inet_address";
    /**input inet port key*/
    public static final String INPUT_INET_PORT = "input_inet_port";
    /**output inet port key*/
    public static final String OUTPUT_INET_PORT = "output_inet_port";
    /**output inet address*/
    public static final String OUTPUT_INET_ADDRESS = "output_inet_address";
}
