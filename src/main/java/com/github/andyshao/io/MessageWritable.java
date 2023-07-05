package com.github.andyshao.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 5, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public interface MessageWritable {

    /**
     * Blocking {@link MessageWritable}
     */
    public static final MessageWritable BLOCKING_MESSAGE_WRITABLE = (channel , context) -> {
        if ((Boolean) context.get(MessageContext.IS_WAITING_FOR_SENDING)) {
            byte[] information = (byte[]) context.get(MessageContext.OUTPUT_MESSAGE_BYTES);
            if (information.length != 0) {
                ByteBuffer buffer = ByteBuffer.wrap(information);
                while (buffer.hasRemaining())
                    channel.write(buffer);
            }
        }
    };

    /**
     * write message
     * @param channel {@link WritableByteChannel}
     * @param context {@link MessageContext}
     * @throws IOException any IO error
     */
    public void write(WritableByteChannel channel , MessageContext context) throws IOException;
}
