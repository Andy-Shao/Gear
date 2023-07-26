package com.github.andyshao.run;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 30, 2017<br>
 * Encoding:UNIX UTF-8
 * @author andy.shao
 *
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AfterStartedNode implements Serializable{
    @Serial
    private static final long serialVersionUID = -2468151760859909130L;
    /**{@link NodeColor}*/
    private NodeColor color = NodeColor.BLACK;
    /**node name*/
    @NonNull
    @EqualsAndHashCode.Include
    private String nodeName;
    /**tail tag*/
    private boolean isTail = true;

    /**
     * The color of the bean
     */
    public static enum NodeColor {
        /**Black color*/
        BLACK,
        /**Red color*/
        RED;
    }
}
