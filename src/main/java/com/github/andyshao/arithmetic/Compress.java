package com.github.andyshao.arithmetic;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 9, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public interface Compress {

    public byte[] compress(byte[] data);

    public byte[] uncompress(byte[] data);
}
