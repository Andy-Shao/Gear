package com.github.andyshao.system;

import com.github.andyshao.reflect.ArrayOperation;

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
public class NoMatchTask implements Task {
    private Task nextTask;

    @Override
    public Task getNextTask() {
        return this.nextTask;
    }

    private void goHelp() {
        this.getNextTask().run(new String[] { HelpTask.KEY_WORDS });
    }

    @Override
    public boolean isDuty(String[] args) {
        return true;
    }

    @Override
    public void process(String[] args) {
        if (args == null || args.length <= 1) {
            this.goHelp();
            return;
        }
        int position = 1;
        boolean status = true;
        do
            status = !args[position].startsWith("-");
        while (status && ++position < args.length);
        if (position == args.length - 1 && status) {
            this.goHelp();
            return;
        } else this.getNextTask().run(ArrayOperation.splitArray(args , position , args.length));
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
