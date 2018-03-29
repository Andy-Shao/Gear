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
    void joinGroup(MemberJoin join) throws GroupManageException;
    void cancel() throws GroupManageException;
}
