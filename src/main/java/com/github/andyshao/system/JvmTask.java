package com.github.andyshao.system;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.channels.Channels;

import com.github.andyshao.nio.BufferReader;
import com.github.andyshao.nio.ByteBufferReader;

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
public class JvmTask implements Task {
    public static final String ARGS_PATH = "com/github/andyshao/system/jvm.txt";
    public static final String COMMAND_PATH = "com/github/andyshao/system/jvmCommand.txt";
    public static final String KEY_WORDS = "-jvm";
    public static final String SUB_OPTION_ARGS = "--args";
    public static final String SUB_OPTION_COMMAND = "--command";
    private volatile Task nextTask = Task.EMTPY_TASK;

    private byte[] doArgs() throws IOException , URISyntaxException {
        try (
            InputStream inputStream =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(JvmTask.ARGS_PATH);
            ByteBufferReader reader = new ByteBufferReader(Channels.newChannel(inputStream));) {
            reader.setFindSeparatePoint((buffer) -> new BufferReader.SeparatePoint(-1));
            return reader.read();
        }
    }

    private byte[] doCommand() throws IOException , URISyntaxException {
        try (
            InputStream inputStream =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(JvmTask.COMMAND_PATH);
            ByteBufferReader reader = new ByteBufferReader(Channels.newChannel(inputStream));) {
            reader.setFindSeparatePoint((buffer) -> new BufferReader.SeparatePoint(-1));
            return reader.read();
        }
    }

    @Override
    public Task getNextTask() {
        return this.nextTask;
    }

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length <= 0) return false;
        if (args[0].equals(JvmTask.KEY_WORDS)) return true;
        return false;
    }

    @Override
    public void process(String[] args) {
        String arg = args.length > 1 ? args[1] : "";
        try {
            switch (arg) {
            case SUB_OPTION_COMMAND:
                System.out.println(new String(this.doCommand()));
                break;

            case SUB_OPTION_ARGS:
            default:
                System.out.println(new String(this.doArgs()));
                break;
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
