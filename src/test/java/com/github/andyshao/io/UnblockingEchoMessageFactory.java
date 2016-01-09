package com.github.andyshao.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

import com.github.andyshao.nio.ByteBufferOperation;
import com.github.andyshao.reflect.ArrayOperation;

public class UnblockingEchoMessageFactory implements UnblockingMessageFactory {
    class MyMessageReadable implements MessageReadable {
        private static final String HEAD_BYTE = "HEAD_BYTE";
        private volatile boolean isReceiveBody = false;

        @Override
        public void read(ReadableByteChannel channel , MessageContext context) throws IOException {
            if (this.isReceiveBody) {
                int bodyLength = Integer.valueOf(new String((byte[]) context.get(MyMessageReadable.HEAD_BYTE) ,
                    (String) context.get(MessageContext.INPUT_MESSAGE_ENCODING)));
                byte[] bodyBytes = (byte[]) context.get(MessageContext.INPUT_MESSAGE_BYTES);
                ByteBuffer bodyBuffer = ByteBuffer.allocate(bodyLength - bodyBytes.length);
                channel.read(bodyBuffer);
                if (!bodyBuffer.hasRemaining()) context.put(MessageContext.IS_WAITING_FOR_RECEIVE , false);
                bodyBuffer.flip();
                bodyBytes =
                    ArrayOperation.mergeArray(byte[].class , bodyBytes , ByteBufferOperation.usedArray(bodyBuffer));
                context.put(MessageContext.INPUT_MESSAGE_BYTES , bodyBytes);
            } else {
                byte[] headBytes = (byte[]) context.get(MyMessageReadable.HEAD_BYTE);
                if (headBytes == null) headBytes = new byte[0];
                ByteBuffer headBuffer = ByteBuffer.allocate(8 - headBytes.length);
                channel.read(headBuffer);
                if (!headBuffer.hasRemaining()) {
                    this.isReceiveBody = true;
                    context.put(MessageContext.INPUT_MESSAGE_BYTES , new byte[0]);
                }
                headBuffer.flip();
                headBytes =
                    ArrayOperation.mergeArray(byte[].class , headBytes , ByteBufferOperation.usedArray(headBuffer));
                context.put(MyMessageReadable.HEAD_BYTE , headBytes);
            }
        }
    }

    public static final String MESSAGE_READABLE_OBJECT =
        UnblockingEchoMessageFactory.class.getName() + "_message_readable_object";

    @Override
    public MessageDecoder buildMessageDecoder(MessageContext context) {
        return (ctxt) -> {
            byte[] received_bytes = (byte[]) ctxt.get(MessageContext.INPUT_MESSAGE_BYTES);
            String received_message =
                new String(received_bytes , (String) ctxt.get(MessageContext.INPUT_MESSAGE_ENCODING));
            ctxt.put(MessageContext.INPUT_MESSAGE_OBJECT , received_message);
            System.out.println("Decoder: " + received_message);
        };
    }

    @Override
    public MessageEncoder buildMessageEncoder(MessageContext context) {
        return (ctxt) -> {
            String sending_message = (String) ctxt.get(MessageContext.OUTPUT_MESSAGE_OBJECT);
            sending_message = BlockingEchoMessageFactory.addMessageHead(sending_message);
            byte[] sending_bytes = sending_message.getBytes((String) ctxt.get(MessageContext.OUTPU_MESSAGE_ENCODING));
            ctxt.put(MessageContext.OUTPUT_MESSAGE_BYTES , sending_bytes);
            System.out.println("Encoder: " + sending_message);
        };
    }

    @Override
    public MessageProcess buildMessageProcess(MessageContext context) {
        return (ctxt) -> {
            System.out.println("Processing...");
            String receive_message = (String) ctxt.get(MessageContext.INPUT_MESSAGE_OBJECT);
            ctxt.put(MessageContext.OUTPUT_MESSAGE_OBJECT , receive_message);
        };
    }

    @Override
    public MessageReadable builMessageReadable(MessageContext context) {
        Object object = context.get(UnblockingEchoMessageFactory.MESSAGE_READABLE_OBJECT);
        if (object == null) {
            object = this.new MyMessageReadable();
            context.put(UnblockingEchoMessageFactory.MESSAGE_READABLE_OBJECT , object);
        }
        return (MessageReadable) object;
    }

}
