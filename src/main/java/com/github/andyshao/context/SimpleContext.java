package com.github.andyshao.context;

import java.io.Serial;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleContext extends ConcurrentHashMap<String , Object> implements Context {

    @Serial
    private static final long serialVersionUID = -1768345492765587828L;
}
