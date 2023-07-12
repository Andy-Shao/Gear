package com.github.andyshao.system;

import com.github.andyshao.nio.BufferReader;
import com.github.andyshao.nio.ByteBufferReader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;

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
public class HelpTask implements Task {
    /**key words*/
    public static final String KEY_WORDS = "-help";
    private static final String TARGET = "com/github/andyshao/system/help.txt";
    private volatile Task nextTask = Task.EMPTY_TASK;

    @Override
    public Task getNextTask() {
        return this.nextTask;
    }

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return false;
        else return args[0].equals(HelpTask.KEY_WORDS);
    }

    @Override
    public void process(String[] args) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(HelpTask.TARGET);
            ByteBufferReader reader = new ByteBufferReader(Channels.newChannel(inputStream));) {
            reader.setFindSeparatePoint((buffer) -> new BufferReader.SeparatePoint(-1));
            System.out.println(new String(reader.read()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
