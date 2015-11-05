package com.github.andyshao.io;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 5, 2015<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public interface MessageWritable {

    public void write(WritableByteChannel channel, MessageContext context) throws IOException;
}
