package com.github.andyshao.io;

import java.nio.ByteBuffer;

import com.github.andyshao.nio.ByteBufferOperation;
import com.github.andyshao.reflect.ArrayOperation;

public class UnblockingEchoMessageFactory implements UnblockingMessageFactory {

    @Override
    public MessageDecoder buildMessageDecoder(MessageContext context) {
        return (ctxt) -> {
            byte[] received_bytes = (byte[]) ctxt.get(MessageContext.INPUT_MESSAGE_BYTES);
            String received_message =
                new String(received_bytes , (String) ctxt.get(MessageContext.INPUT_MESSAGE_ENCODING));
            ctxt.put(MessageContext.INPUT_MESSAGE_OBJECT , received_message);
            ctxt.put(MessageContext.INPUT_MESSAGE_BYTES , new byte[0]);
        };
    }

    @Override
    public MessageEncoder buildMessageEncoder(MessageContext context) {
        return (ctxt) -> {
            String sending_message = (String) ctxt.get(MessageContext.OUTPUT_MESSAGE_OBJECT);
            BlockingEchoMessageFactory.addMessageHead(sending_message);
            byte[] sending_bytes = sending_message.getBytes((String) ctxt.get(MessageContext.OUTPU_MESSAGE_ENCODING));
            ctxt.put(MessageContext.OUTPUT_MESSAGE_BYTES , sending_bytes);
        };
    }

    @Override
    public MessageProcess buildMessageProcess(MessageContext context) {
        return (ctxt) -> {
            String receive_message = (String) ctxt.get(MessageContext.INPUT_MESSAGE_OBJECT);
            ctxt.put(MessageContext.OUTPUT_MESSAGE_OBJECT , receive_message);
        };
    }

    @Override
    public MessageReadable builMessageReadable(MessageContext context) {
        return (channel , ctxt) -> {
            byte[] read_bytes = (byte[]) ctxt.get(MessageContext.INPUT_MESSAGE_BYTES);
            ByteBuffer readBuffer = ByteBuffer.allocate(8 - read_bytes.length);
            channel.read(readBuffer);
            if (!readBuffer.hasRemaining()) ctxt.put(MessageContext.IS_WAITING_FOR_RECEIVE , false);
            readBuffer.flip();
            read_bytes =
                ArrayOperation.mergeArray(byte[].class , read_bytes , ByteBufferOperation.usedArray(readBuffer));
            ctxt.put(MessageContext.INPUT_MESSAGE_BYTES , read_bytes);
        };
    }

}
