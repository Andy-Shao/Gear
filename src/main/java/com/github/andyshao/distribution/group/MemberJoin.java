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
    void onMembersChange(List<MemberNode> members);
    MemberNode selfNode();
}
