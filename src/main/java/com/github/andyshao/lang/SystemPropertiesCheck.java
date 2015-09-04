package com.github.andyshao.lang;

import java.util.Properties;

/**
 * 
 * Title: Use to check the java system properties by console.<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 4, 2015<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public class SystemPropertiesCheck {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            Properties properties = System.getProperties();
            for (Object key : properties.keySet())
                System.out.println(key + " : " + properties.get(key));
        } else System.out.println(args[0] + " : " + System.getProperty(args[0]));
    }
}
