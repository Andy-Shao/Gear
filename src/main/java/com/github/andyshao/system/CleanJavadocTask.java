package com.github.andyshao.system;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 20, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class CleanJavadocTask implements Task {
    public static final String KEY_WORDS = "-cleanJavadoc";
    private volatile Task nextTask = Task.EMTPY_TASK;

    @Override
    public Task getNextTask() {
        return this.nextTask;
    }

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return false;
        return args[0].equals(KEY_WORDS);
    }

    @Override
    public void process(String[] args) {
        // TODO Auto-generated method stub

    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
