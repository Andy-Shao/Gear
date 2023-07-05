package com.github.andyshao.distribution.election;

import java.util.List;

/**
 * 
 * Title: Master election<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 27, 2018<br>
 * Encoding: UNIX UTF-8
 * @author andy.shao
 *
 */
public interface MasterElect {
    /**
     * on master change even method
     * @param master {@link ElectionNode}
     */
    void onMasterChange(ElectionNode master);

    /**
     * on election member change even method
     * @param electionNodes {@link ElectionNode} list
     */
    void onElectMembersChange(List<ElectionNode> electionNodes);

    /**
     * Itself
     * @return {@link ElectionNode}
     */
    ElectionNode selfNode();
}
