package com.github.andyshao.system;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 10, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class Main {
    public static void main(String[] args) throws IOException , ScriptException {
        final ScriptEngineManager factory = new ScriptEngineManager();
        final ScriptEngine engine = factory.getEngineByName("nashorn");

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/github/andyshao/system/system.js");
            Reader reader = new InputStreamReader(inputStream);) {
            engine.eval(reader);
        }
        ((Task) engine.get("head")).run(args);
    }
}
