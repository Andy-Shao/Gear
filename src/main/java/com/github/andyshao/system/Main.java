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
public class Main {

    private static Task buildTask() {
        return new NoArgumentTask(new SystemPropertiesTask());
    }

    public static void main(String[] args) {
        Task myTask = Main.buildTask();
        myTask.run(args);
    }
}
