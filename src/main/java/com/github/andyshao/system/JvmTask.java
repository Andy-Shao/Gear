package com.github.andyshao.system;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.file.Files;

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
    public static final String TYPE_PATH = "com/github/andyshao/system/typeDescription.txt";
    public static final String METHOD_PATH = "com/github/andyshao/system/methodDescriptors.txt";
    public static final String KEY_WORDS = "-jvm";
    public static final String SUB_OPTION_ARGS = "--args";
    public static final String SUB_OPTION_COMMAND = "--command";
    public static final String SUB_OPTION_TYPE = "--type";
    public static final String SUB_OPTION_METHOD = "--method";
    private volatile Task nextTask = Task.EMTPY_TASK;

    static byte[] readFile(String path) throws IOException{
        try (
                InputStream inputStream =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
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
            case SUB_OPTION_TYPE:
                System.out.println(new String(readFile(TYPE_PATH), "UTF-8"));
                break;
            case SUB_OPTION_METHOD:
                System.out.println(new String(readFile(METHOD_PATH), "UTF-8"));
                break;
            case SUB_OPTION_COMMAND:
                System.out.println(new String(readFile(COMMAND_PATH), "UTF-8"));
                break;

            case SUB_OPTION_ARGS:
            default:
                System.out.println(new String(readFile(ARGS_PATH), "UTF-8"));
                break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
