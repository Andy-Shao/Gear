package com.github.andyshao.system;

import java.util.Properties;

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
public class SystemPropertiesTask implements Task {
    public static final String KEY_WORDS = "-sp";
    public final Task nextTask;

    public SystemPropertiesTask() {
        this(Task.EMTPY_TASK);
    }

    public SystemPropertiesTask(Task nextTask) {
        this.nextTask = nextTask;
    }

    @Override
    public boolean isDuty(int index , String[] args) {
        if (args == null || args.length == 0) return false;
        else if (args[0].endsWith(SystemPropertiesTask.KEY_WORDS)) return true;
        return false;
    }

    @Override
    public Task nextTask() {
        return this.nextTask;
    }

    @Override
    public void process(int index , String[] args) {
        if (args.length == 1) {
            Properties properties = System.getProperties();
            for (Object key : properties.keySet())
                System.out.println(key + " = " + properties.get(key));
        } else System.out.println(System.getProperty(args[1]));
    }

}
