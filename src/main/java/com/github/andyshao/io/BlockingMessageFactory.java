package com.github.andyshao.io;

import java.nio.ByteBuffer;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 9, 2015<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public interface BlockingMessageFactory extends MessageFactory {

    @Override
    public default MessageWritable buildMessageWritable(MessageContext context){
        return (channel , ctxt) -> {
            byte[] writeBytes = (byte[]) ctxt.get(MessageContext.OUTPUT_MESSAGE_BYTES);
            if(writeBytes.length == 0) return;
            ByteBuffer writeBuffer = ByteBuffer.wrap(writeBytes);
            while(writeBuffer.hasRemaining()) channel.write(writeBuffer);
        };
    }
}
