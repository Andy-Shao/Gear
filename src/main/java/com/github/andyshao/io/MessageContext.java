package com.github.andyshao.io;

import java.util.Map;

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
public interface MessageContext extends Map<String , Object> {
    public static final String INPUT_INET_ADDRESS = "input_inet_address";
    public static final String INPUT_INET_PORT = "input_inet_port";
    public static final String INPUT_MESSAGE_BYTES = "input_message_bytes";
    public static final String INPUT_MESSAGE_ENCODING = "input_message_encoding";
    public static final String INPUT_MESSAGE_STRING = "input_message_String";
    public static final String IS_WAITING_FOR_RECEIVE = "is_waiting_for_receive";
    public static final String IS_WAITING_FOR_SENDING = "is_waiting_for_sending";
    public static final String MESSAGE_INBOUND_SIGN = "message_inbound_sign";
    public static final String MESSAGE_OUTBOUND_SIGN = "message_outbound_sign";
    public static final String OUTPU_INET_PORT = "output_inet_port";
    public static final String OUTPU_MESSAGE_ENCODING = "output_message_encoding";
    public static final String OUTPUT_INET_ADDRESS = "output_inet_address";
    public static final String OUTPUT_MESSAGE_BYTES = "output_message_bytes";
    public static final String OUTPUT_MESSAGE_STRING = "output_message_String";
}
