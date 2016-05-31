package com.github.andyshao.system;

import java.nio.file.Paths;

import com.github.andyshao.lang.GeneralSystemProperty;
import com.github.andyshao.reflect.PackageOperation;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 30, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class SearchTask implements Task {
    public static final String KEY_WORDS = "-search";
    private volatile Task nextTask = Task.EMTPY_TASK;

    @Override
    public Task getNextTask() {
        return this.nextTask;
    }

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return false;
        else return args[0].equals(SearchTask.KEY_WORDS);
    }

    @Override
    public void process(String[] args) {
        if (args.length < 2) throw new RuntimeException("args length is not enough");
        for (String className : PackageOperation.getClasses(Paths.get(GeneralSystemProperty.JAVA_HOME.value() , "lib" , "rt.jar")))
            if (className.matches(args[1])) System.out.println(className);
        for (Package pkg : Package.getPackages())
            for (String className : PackageOperation.getPackageClasseName(pkg))
                if (className.matches(args[1])) System.out.println(className);
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
