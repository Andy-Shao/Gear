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
    private volatile Task nextTask = Task.EMTPY_TASK;

    @Override
    public Task getNextTask() {
        return this.nextTask;
    }

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return true;
        else return false;
    }

    @Override
    public void process(String[] args) {
        this.run(new String[] { HelpTask.KEY_WORDS });
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
