package com.github.andyshao.nio;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 29, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public interface ReadStream {
    default ReadStream endWith(int index) throws IOException{
        //TODO
        return null;
    }

    default ReadStream endWithNext(byte... bs) throws IOException{
        //TODO
        return null;
    }

    default ReadStream fixLength(int size) throws IOException{
        //TODO
        return null;
    }

    int read(ByteBuffer bs) throws IOException;

    default ReadStream startWith(int index) throws IOException{
        //TODO
        return null;
    }

    default ReadStream startWithNext(byte... bs) throws IOException{
        //TODO
        return null;
    }
}
