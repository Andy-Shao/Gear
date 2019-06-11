package com.github.andyshao.context;

import java.util.concurrent.ConcurrentMap;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 5, 2018<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 */
public interface Context extends ConcurrentMap<String , Object>, CommonContext {
}
