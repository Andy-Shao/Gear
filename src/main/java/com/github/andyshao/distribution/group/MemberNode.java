package com.github.andyshao.distribution.group;

import com.github.andyshao.lang.number.ByteSize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 27, 2018<br>
 * Encoding: UNIX UTF-8
 * @author andy.shao
 *
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MemberNode implements Serializable {
    @Serial
    private static final long serialVersionUID = 3307546608951426261L;
    @EqualsAndHashCode.Include
    private String name;
    @EqualsAndHashCode.Include
    private String host;
    @EqualsAndHashCode.Include
    private int port;
    private ByteSize memory;
    private int cpuNum;
    private Map<String , ? super Serializable> pros = new HashMap<>();
}
