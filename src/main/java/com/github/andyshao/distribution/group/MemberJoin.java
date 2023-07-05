package com.github.andyshao.distribution.group;

import java.util.List;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 29, 2018<br>
 * Encoding: UNIX UTF-8
 * @author andy.shao
 *
 */
public interface MemberJoin {
    /**
     * on member change even method
     * @param members {@link MemberNode} list
     */
    void onMembersChange(List<MemberNode> members);

    /**
     * Itself
     * @return {@link MemberNode}
     */
    MemberNode selfNode();
}
