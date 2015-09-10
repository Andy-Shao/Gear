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
    public volatile Task nextTask = Task.EMTPY_TASK;

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return false;
        else if (args[0].endsWith(SystemPropertiesTask.KEY_WORDS)) return true;
        return false;
    }

    @Override
    public Task nextTask() {
        return this.nextTask;
    }

    @Override
    public void process(String[] args) {
        if (args.length == 1) {
            Properties properties = System.getProperties();
            for (Object key : properties.keySet())
                System.out.println(key + " = " + properties.get(key));
        } else System.out.println(System.getProperty(args[1]));
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
