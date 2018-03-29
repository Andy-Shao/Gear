package com.github.andyshao.distribution.election;

import java.util.List;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 27, 2018<br>
 * Encoding: UNIX UTF-8
 * @author andy.shao
 *
 */
public interface MasterElect {
    void onMasterChange(ElectionNode master);
    void onElectMembersChange(List<ElectionNode> electionNodes);
    ElectionNode selfNode();
}
