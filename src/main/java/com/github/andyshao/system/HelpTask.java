package com.github.andyshao.system;

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
public class HelpTask implements Task {
    public static final String KEY_WORDS = "-help";
    private volatile Task nextTask = Task.EMTPY_TASK;

    @Override
    public boolean isDuty(String[] args) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Task nextTask() {
        return this.nextTask;
    }

    @Override
    public void process(String[] args) {
        // TODO Auto-generated method stub

    }

}
