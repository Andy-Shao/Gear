package com.github.andyshao.run;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

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
@EqualsAndHashCode(of = {"nodeName"})
@SuppressWarnings("serial")
public class AfterStartedNode implements Serializable{
    private NodeColor color = NodeColor.BLACK;
    @NonNull
    private String nodeName;
    private boolean isTail = true;
    
    public static enum NodeColor {
        BLACK,RED;
    }
}
