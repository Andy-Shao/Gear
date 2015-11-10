package com.github.andyshao.io;

import java.util.Map;

import com.github.andyshao.lang.GeneralSystemProperty;

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
    public static final String INPUT_MESSAGE_BYTES = "input_message_bytes";
    public static final String INPUT_MESSAGE_ENCODING = "input_message_encoding";
    public static final String INPUT_MESSAGE_OBJECT = "input_message_object";
    public static final String IS_WAITING_FOR_DECODE = "is_waiting_for_decode";
    public static final String IS_WAITING_FOR_ENCODE = "is_waiting_for_encode";
    public static final String IS_WAITING_FOR_PROCESS = "is_waiting_for_process";
    public static final String IS_WAITING_FOR_RECEIVE = "is_waiting_for_receive";
    public static final String IS_WAITING_FOR_SENDING = "is_waiting_for_sending";
    public static final String OUTPU_MESSAGE_ENCODING = "output_message_encoding";
    public static final String OUTPUT_MESSAGE_BYTES = "output_message_bytes";
    public static final String OUTPUT_MESSAGE_OBJECT = "output_message_object";

    public default void cleanStatus() {
        String encoding = GeneralSystemProperty.FILE_ENCODING.value();
        this.put(MessageContext.INPUT_MESSAGE_ENCODING , encoding);
        this.put(MessageContext.OUTPU_MESSAGE_ENCODING , encoding);
        this.put(MessageContext.IS_WAITING_FOR_RECEIVE , false);
        this.put(MessageContext.IS_WAITING_FOR_SENDING , false);
        this.put(MessageContext.IS_WAITING_FOR_DECODE , false);
        this.put(MessageContext.IS_WAITING_FOR_ENCODE , false);
        this.put(MessageContext.IS_WAITING_FOR_PROCESS , false);
        this.put(MessageContext.INPUT_MESSAGE_BYTES , new byte[0]);
        this.put(MessageContext.INPUT_MESSAGE_OBJECT , "");
        this.put(MessageContext.OUTPUT_MESSAGE_BYTES , new byte[0]);
        this.put(MessageContext.OUTPUT_MESSAGE_OBJECT , "");
    }

    public default boolean isWaitingForDecode() {
        Boolean isWaitingForDecode = (Boolean) this.get(MessageContext.IS_WAITING_FOR_DECODE);
        Boolean isWaitingForReceive = (Boolean) this.get(MessageContext.IS_WAITING_FOR_RECEIVE);
        return isWaitingForDecode == true && isWaitingForReceive == false;
    }

    public default boolean isWaitingForEncode() {
        Boolean isWaitingForEncode = (Boolean) this.get(MessageContext.IS_WAITING_FOR_ENCODE);
        Boolean isWaitingForSending = (Boolean) this.get(MessageContext.IS_WAITING_FOR_SENDING);
        return isWaitingForEncode == true && isWaitingForSending == false;
    }

    public default boolean isWaitingForProcess() {
        Boolean isWaitingForProcess = (Boolean) this.get(MessageContext.IS_WAITING_FOR_PROCESS);
        return isWaitingForProcess == true;
    }

    public default boolean isWaitingForRecieve() {
        Boolean isWaitingForDecode = (Boolean) this.get(MessageContext.IS_WAITING_FOR_DECODE);
        Boolean isWaitingForReceive = (Boolean) this.get(MessageContext.IS_WAITING_FOR_RECEIVE);
        return isWaitingForDecode == false && isWaitingForReceive == true;
    }

    public default boolean isWaitingForSending() {
        Boolean isWaitingForEncode = (Boolean) this.get(MessageContext.IS_WAITING_FOR_ENCODE);
        Boolean isWaitingForSending = (Boolean) this.get(MessageContext.IS_WAITING_FOR_SENDING);
        return isWaitingForEncode == false && isWaitingForSending == true;
    }
}
