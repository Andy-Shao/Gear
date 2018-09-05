package com.github.andyshao.context;

import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("serial")
public class SimpleContext extends ConcurrentHashMap<String , Object> implements Context {

}
