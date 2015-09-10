package com.github.andyshao.system;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 10, 2015<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public class Main {

    public static void main(String[] args) {
        Task myTask = buildTask();
        myTask.run(0 , args);
    }

    private static Task buildTask() {
        return new NoArgumentTask(new SystemPropertiesTask());
    }
}
