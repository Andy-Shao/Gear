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
public class NoArgumentTask implements Task {
    private final Task nextTask;

    public NoArgumentTask() {
        this(Task.EMTPY_TASK);
    }

    public NoArgumentTask(Task nextTask) {
        this.nextTask = nextTask;
    }

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return true;
        else return false;
    }

    @Override
    public Task nextTask() {
        return this.nextTask;
    }

    @Override
    public void process(String[] args) {
    }

}
