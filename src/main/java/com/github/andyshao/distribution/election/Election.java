package com.github.andyshao.distribution.election;

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
    /**
     * elect operation
     * @param elect {@link MasterElect}
     * @throws ElectionException any type election error
     */
    void elect(MasterElect elect) throws ElectionException;

    /**
     * cancel operation
     * @throws ElectionException any type election error
     */
    void cancel() throws ElectionException;
}