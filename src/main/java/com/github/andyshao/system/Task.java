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
public interface Task {
    public static final Task EMTPY_TASK = new Task() {

        @Override
        public Task getNextTask() {
            return null;
        }

        @Override
        public boolean isDuty(String[] args) {
            return true;
        }

        @Override
        public void process(String[] args) {
            //do nothing...
        }
    };

    public Task getNextTask();

    public boolean isDuty(String[] args);

    public void process(String[] args);

    public default void run(String[] args) {
        if (this.isDuty(args)) this.process(args);
        else this.getNextTask().run(args);
    }
}
