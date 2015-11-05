package com.github.andyshao.io;

import java.io.IOException;
import java.nio.channels.ReadableByteChannel;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 5, 2015<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public interface MessageReadable {

    public void read(ReadableByteChannel channel, MessageContext context) throws IOException;
}
