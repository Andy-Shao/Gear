package com.github.andyshao.io;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class EchoMessageFactory implements MessageFactory {

    @Override
    public MessageDecoder buildMessageDecoder(MessageContext context) {
        return new MessageDecoder() {
            @Override
            public void decode(MessageContext context) throws IOException , UnsupportedEncodingException {
                byte[] input_message = (byte[]) context.get(MessageContext.INPUT_MESSAGE_BYTES);
                String message =
                    new String(input_message , (String) context.get(MessageContext.INPUT_MESSAGE_ENCODING));
                context.put(MessageContext.INPUT_MESSAGE_OBJECT , message);
                System.out.println("Decode Message: " + message);
            }
        };
    }

    @Override
    public MessageEncoder buildMessageEncoder(MessageContext context) {
        return new MessageEncoder() {

            @Override
            public void encode(MessageContext context) throws IOException , UnsupportedEncodingException {
                String output_message = (String) context.get(MessageContext.OUTPUT_MESSAGE_OBJECT);
                context.put(MessageContext.OUTPUT_MESSAGE_BYTES ,
                    output_message.getBytes((String) context.get(MessageContext.OUTPU_MESSAGE_ENCODING)));
                System.out.println("Encode Message: " + output_message);
            }
        };
    }

    @Override
    public MessageProcess buildMessageProcess(MessageContext context) {
        return new MessageProcess() {

            @Override
            public void process(MessageContext context) throws Exception {
                System.out.println("It's Processing...");
                String input_message = (String) context.get(MessageContext.INPUT_MESSAGE_OBJECT);
                context.put(MessageContext.OUTPUT_MESSAGE_OBJECT , input_message);
            }
        };
    }

}
