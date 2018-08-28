package com.github.andyshao.reflect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Aug 23, 2018<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
@Data
@SuppressWarnings("serial")
public class GenericNode implements Serializable{
    private final List<GenericNode> componentTypes = new ArrayList<>();
    @Setter(AccessLevel.PACKAGE)
    private Class<?> declareType;
    @Setter(AccessLevel.PACKAGE)
    private boolean isGeneiric = false;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.PACKAGE)
    @Getter(AccessLevel.PACKAGE)
    private GenericNode parent;
    @EqualsAndHashCode.Exclude
    private String typeVariable;
}
