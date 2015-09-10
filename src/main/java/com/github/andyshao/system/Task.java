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
        public boolean isDuty(int index , String[] args) {
            return true;
        }

        @Override
        public Task nextTask() {
            return null;
        }

        @Override
        public void process(int index , String[] args) {
            //do nothing...
        }
    };

    public boolean isDuty(int index , String[] args);

    public Task nextTask();

    public void process(int index , String[] args);

    public default void run(int index , String[] args) {
        if (this.isDuty(index , args)) this.process(index , args);
        else this.nextTask().run(index , args);
    }
}
