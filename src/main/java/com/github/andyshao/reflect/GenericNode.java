package com.github.andyshao.reflect;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
public class GenericNode implements Serializable{
    @Serial
    private static final long serialVersionUID = 3073863989335642029L;
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
    @Setter(AccessLevel.PACKAGE)
    @EqualsAndHashCode.Exclude
    private String typeVariable;

    public boolean isTypeVariable() {
        return Objects.isNull(this.getDeclareType()) && Objects.nonNull(this.getTypeVariable());
    }

    public boolean isMediocreClazz() {
        return !this.isGeneiric() && Objects.nonNull(this.getDeclareType());
    }
}
