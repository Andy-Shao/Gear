package com.github.andyshao.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Dec 9, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class HttpTask implements Task {
    public static final String KEY_WORDS = "-http";
    private static final String STATUE_CODE = "--statusCode";
    private static final String STAUTS_FILE = "com/github/andyshao/system/HttpStatuesCode.properties";
    private volatile Task nextTask = Task.EMTPY_TASK;

    @Override
    public Task getNextTask() {
        return this.nextTask;
    }

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return false;
        else return args[0].equals(HttpTask.KEY_WORDS);
    }

    boolean isPrintAll(String[] args) {
        if (args.length < 3) return true;
        try {
            Integer.valueOf(args[2]);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    @Override
    public void process(String[] args) {
        if (args.length < 2) args = new String[] { args[0] , HttpTask.STATUE_CODE };
        switch (args[1]) {
        case STATUE_CODE:
            this.showStatueCode(args);
            break;
        default:
            String[] newArgs = new String[3];
            System.arraycopy(args , 0 , newArgs , 0 , 2);
            newArgs[2] = HelpTask.KEY_WORDS;
            this.nextTask.run(newArgs);
            break;
        }
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

    void showStatueCode(String[] args) {
        try (InputStream inputStream =
            Thread.currentThread().getContextClassLoader().getResourceAsStream(HttpTask.STAUTS_FILE);) {
            if (this.isPrintAll(args))
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {
                String line = "";
                while ((line = reader.readLine()) != null)
                    System.out.println(line);
            }
            else {
                Properties properties = new Properties();
                properties.load(inputStream);
                String message = properties.getProperty(args[2]);
                if(message != null) System.out.println(args[2] + " : " + message);
                else {
                    message = properties.getProperty("DEFINITION");
                    int index = message.indexOf(args[2].substring(0 , 1));
                    if(index != -1) {
                        message = message.substring(index , message.length());
                        index = message.indexOf(",");
                        message = message.substring(0 , index);
                    }
                    System.out.println(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
