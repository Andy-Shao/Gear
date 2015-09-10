package com.github.andyshao.system;

import java.lang.reflect.Field;

import com.github.andyshao.reflect.Reflects;

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
        Task head = new NoArgumentTask();
        head = Main.setTask(head , new HelpTask());
        head = Main.setTask(head , new VersionTask());
        head = Main.setTask(head , new InfoTask());
        head = Main.setTask(head , new SystemPropertiesTask());
        head = Main.setTask(head , new JvmTask());
        return Main.setTask(head , new NoMatchTask());
    }

    public static void main(String[] args) {
        Task myTask = Main.buildTask();
        myTask.run(args);
    }

    private static final Task setTask(Task head , Task tail) {
        Field nextTask_field = Reflects.getDeclaredField(head.getClass() , "nextTask");
        nextTask_field.setAccessible(true);
        Reflects.setFieldValue(head , nextTask_field , tail);
        return tail;
    }
}
