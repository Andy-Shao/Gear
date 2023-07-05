package com.github.andyshao.distribution.election;

import java.util.List;
import java.util.Optional;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 28, 2018<br>
 * Encoding: UNIX UTF-8
 * @author andy.shao
 *
 */
public interface MasterElectAlgorithm {
    /**
     * Find master {@link ElectionNode}
     * @param nodes {@link ElectionNode} list
     * @return the master {@link ElectionNode}
     */
    Optional<ElectionNode> findMaster(List<ElectionNode> nodes);
}
