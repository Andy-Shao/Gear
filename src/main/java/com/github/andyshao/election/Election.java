package com.github.andyshao.election;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 27, 2018<br>
 * Encoding: UNIX UTF-8
 * @author andy.shao
 *
 */
public interface Election {
    void elect(MasterElect elect) throws ElectionException;
    void cancel() throws ElectionException;
}