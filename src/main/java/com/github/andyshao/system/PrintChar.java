package com.github.andyshao.system;

import com.github.andyshao.reflect.ArrayOperation;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 25, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class PrintChar implements Task {
    public static final String KEY_WORDS = "-printChar";
    private volatile Task nextTask = Task.EMTPY_TASK;

    @Override
    public Task getNextTask() {
        return this.nextTask;
    }

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return false;
        else return args[0].equals(PrintChar.KEY_WORDS);
    }

    @Override
    public void process(String[] args) {
        if (args.length < 2) args = ArrayOperation.mergeArray(String[].class , args , new String[] { String.valueOf((int) 'α') , String.valueOf((int) 'ω') });
        final int startPosition = Integer.valueOf(args[1]);
        int endposition = startPosition;
        if (args.length >= 3) endposition = Integer.valueOf(args[2]);

        for (int i = startPosition ; i <= endposition ; i++) {
            System.out.print((char) i + " ");
            if ((i - startPosition + 1) % 5 == 0) System.out.println();
        }
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }
}
