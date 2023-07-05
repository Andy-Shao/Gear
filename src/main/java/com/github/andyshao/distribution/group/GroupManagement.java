package com.github.andyshao.distribution.group;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 29, 2018<br>
 * Encoding: UNIX UTF-8
 * @author andy.shao
 *
 */
public interface GroupManagement {
    /**
     * Join the group
     * @param join {@link MemberJoin}
     * @throws GroupManageException any group manage error
     */
    void joinGroup(MemberJoin join) throws GroupManageException;

    /**
     * Cancel operation
     * @throws GroupManageException any group manage error
     */
    void cancel() throws GroupManageException;
}
