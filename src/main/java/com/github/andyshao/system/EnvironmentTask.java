package com.github.andyshao.system;

import java.util.Map;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Aug 22, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class EnvironmentTask implements Task {
    public static final String KEY_WORDS = "-env";
    public volatile Task nextTask = Task.EMPTY_TASK;

    @Override
    public Task getNextTask() {
        return this.nextTask;
    }

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return false;
        else return args[0].endsWith(EnvironmentTask.KEY_WORDS);
    }

    @Override
    public void process(String[] args) {
        if (args.length == 1) {
            Map<String , String> env = System.getenv();
            for (Object key : env.keySet())
                System.out.println(key + " = " + env.get(key));
        } else System.out.println(System.getenv(args[1]));
    }

    /**
     * set next task
     * @param nextTask next task
     */
    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
