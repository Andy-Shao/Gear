package com.github.andyshao.system;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Title: <br>
 * Description: <br>
 * Copyright: Copyright(c) 2021/7/27
 * Encoding: UNIX UTF-8
 *
 * @author Andy.Shao
 */
public class JarMain {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Task head = new NoArgumentTask();
        Task tail = setTask(head, new HelpTask());
        tail = setTask(tail, new InfoTask());
        tail = setTask(tail, new VersionTask());
        tail = setTask(tail, new SystemPropertiesTask());
        tail = setTask(tail, new JvmTask());
        tail = setTask(tail, new CleanJavadocTask());
        tail = setTask(tail, new HttpTask());
        tail = setTask(tail, new PrintChar());
        tail = setTask(tail, new SearchTask());
//        tail = setTask(tail, new ClassAnalysis());
        tail = setTask(tail, new EnvironmentTask());
        tail = setTask(tail, new NoMatchTask());
        setTask(tail, head);

        head.run(args);
    }

    public static Task setTask(Task head, Task tail) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Method setNextTask = head.getClass().getMethod("setNextTask", Task.class);
        setNextTask.invoke(head, tail);
        return tail;
    }
}
