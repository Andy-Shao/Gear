package com.github.andyshao.io;

import java.nio.ByteBuffer;

import com.github.andyshao.nio.ByteBufferOperation;

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
public interface UnblockingMessageFactory extends MessageFactory {

    @Override
    public default MessageWritable buildMessageWritable(MessageContext context) {
        return (channel , ctxt) -> {
            final byte[] writeBytes = (byte[]) ctxt.get(MessageContext.OUTPUT_MESSAGE_BYTES);
            if (writeBytes.length == 0) {
                ctxt.put(MessageContext.IS_WAITING_FOR_SENDING , false);
                return;
            }
            final ByteBuffer writeBuffer = ByteBuffer.wrap(writeBytes);
            channel.write(writeBuffer);
            if (writeBuffer.hasRemaining()) ctxt.put(MessageContext.OUTPUT_MESSAGE_BYTES ,
                ByteBufferOperation.usedArray(writeBuffer));
            else ctxt.put(MessageContext.IS_WAITING_FOR_SENDING , false);
        };
    }
}
