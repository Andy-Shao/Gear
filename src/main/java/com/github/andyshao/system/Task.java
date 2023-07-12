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
    /**empty task*/
    public static final Task EMPTY_TASK = new Task() {

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

    /**
     * get next task
     * @return next task
     */
    public Task getNextTask();

    /**
     * is duty
     * @param args argument
     * @return if it is then true
     */
    public boolean isDuty(String[] args);

    /**
     * process task
     * @param args argument
     */
    public void process(String[] args);

    /**
     * run task
     * @param args argument
     */
    public default void run(String[] args) {
        if (this.isDuty(args)) this.process(args);
        else this.getNextTask().run(args);
    }
}
