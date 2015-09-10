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
        public boolean isDuty(String[] args) {
            return true;
        }

        @Override
        public Task nextTask() {
            return null;
        }

        @Override
        public void process(String[] args) {
            //do nothing...
        }
    };

    public boolean isDuty(String[] args);

    public Task nextTask();

    public void process(String[] args);

    public default void run(String[] args) {
        if (this.isDuty(args)) this.process(args);
        else this.nextTask().run(args);
    }
}
