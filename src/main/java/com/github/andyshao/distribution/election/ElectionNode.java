package com.github.andyshao.distribution.election;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.github.andyshao.lang.number.ByteSize;

import lombok.Data;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 27, 2018<br>
 * Encoding: UNIX UTF-8
 * @author andy.shao
 *
 */
@Data
@SuppressWarnings("serial")
public class ElectionNode implements Serializable {
    private String name;
    private String host;
    private int port;
    private ByteSize memory;
    private int cpuNum;
    private Map<String , ? super Serializable> pros = new HashMap<>();
}
