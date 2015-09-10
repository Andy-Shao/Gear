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
        final Task head = new NoArgumentTask();
        Task tail = Main.setTask(head , new HelpTask());
        tail = Main.setTask(tail , new VersionTask());
        tail = Main.setTask(tail , new InfoTask());
        tail = Main.setTask(tail , new SystemPropertiesTask());
        tail = Main.setTask(tail , new JvmTask());
        tail = Main.setTask(tail , new NoMatchTask());
        return head;
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
