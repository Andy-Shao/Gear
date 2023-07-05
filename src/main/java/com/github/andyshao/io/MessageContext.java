package com.github.andyshao.io;

import com.github.andyshao.lang.GeneralSystemProperty;

import java.util.concurrent.ConcurrentMap;

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
public interface MessageContext extends ConcurrentMap<String , Object> {
    /**input message bytes key*/
    public static final String INPUT_MESSAGE_BYTES = "input_message_bytes";
    /**input message encoding key*/
    public static final String INPUT_MESSAGE_ENCODING = "input_message_encoding";
    /**input message object key*/
    public static final String INPUT_MESSAGE_OBJECT = "input_message_object";
   /**is waiting for decode key*/
    public static final String IS_WAITING_FOR_DECODE = "is_waiting_for_decode";
    /**is waiting for encode key*/
    public static final String IS_WAITING_FOR_ENCODE = "is_waiting_for_encode";
    /**is waiting for process key*/
    public static final String IS_WAITING_FOR_PROCESS = "is_waiting_for_process";
    /**is waiting for receive key*/
    public static final String IS_WAITING_FOR_RECEIVE = "is_waiting_for_receive";
    /**is waiting for sending key*/
    public static final String IS_WAITING_FOR_SENDING = "is_waiting_for_sending";
    /**output message encoding key*/
    public static final String OUTPUT_MESSAGE_ENCODING = "output_message_encoding";
    /**output message bytes key*/
    public static final String OUTPUT_MESSAGE_BYTES = "output_message_bytes";
    /**output message object key*/
    public static final String OUTPUT_MESSAGE_OBJECT = "output_message_object";

    /**
     * clean all status values
     */
    public default void cleanStatus() {
        String encoding = GeneralSystemProperty.FILE_ENCODING.value();
        this.put(MessageContext.INPUT_MESSAGE_ENCODING , encoding);
        this.put(MessageContext.OUTPUT_MESSAGE_ENCODING, encoding);
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

    /**
     * is waiting for decode
     * @return if it is, then true
     */
    public default boolean isWaitingForDecode() {
        Boolean isWaitingForDecode = (Boolean) this.get(MessageContext.IS_WAITING_FOR_DECODE);
        Boolean isWaitingForReceive = (Boolean) this.get(MessageContext.IS_WAITING_FOR_RECEIVE);
        return isWaitingForDecode && !isWaitingForReceive;
    }

    /**
     * is waiting for encode
     * @return if it is, then true
     */
    public default boolean isWaitingForEncode() {
        Boolean isWaitingForEncode = (Boolean) this.get(MessageContext.IS_WAITING_FOR_ENCODE);
        Boolean isWaitingForSending = (Boolean) this.get(MessageContext.IS_WAITING_FOR_SENDING);
        return isWaitingForEncode && !isWaitingForSending;
    }

    /**
     * is waiting for process
     * @return if it is, then true
     */
    public default boolean isWaitingForProcess() {
        Boolean isWaitingForProcess = (Boolean) this.get(MessageContext.IS_WAITING_FOR_PROCESS);
        return isWaitingForProcess;
    }

    /**
     * is waiting for receive
     * @return if it is, then true
     */
    public default boolean isWaitingForReceive() {
        Boolean isWaitingForDecode = (Boolean) this.get(MessageContext.IS_WAITING_FOR_DECODE);
        Boolean isWaitingForReceive = (Boolean) this.get(MessageContext.IS_WAITING_FOR_RECEIVE);
        return !isWaitingForDecode && isWaitingForReceive;
    }

    /**
     * is waiting for sending
     * @return if it is, then true
     */
    public default boolean isWaitingForSending() {
        Boolean isWaitingForEncode = (Boolean) this.get(MessageContext.IS_WAITING_FOR_ENCODE);
        Boolean isWaitingForSending = (Boolean) this.get(MessageContext.IS_WAITING_FOR_SENDING);
        return !isWaitingForEncode && isWaitingForSending;
    }
}
