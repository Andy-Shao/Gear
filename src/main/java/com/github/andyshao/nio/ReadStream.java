package com.github.andyshao.nio;

import java.util.function.Consumer;

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
    ReadStream endWith(int index);

    ReadStream endWithNext(byte... bs);

    ReadStream fixLength(int size);

    void foreach(Consumer<Byte> consumer);

    byte[] read();

    ReadStream startWith(int index);

    ReadStream startWithNext(byte... bs);
}
