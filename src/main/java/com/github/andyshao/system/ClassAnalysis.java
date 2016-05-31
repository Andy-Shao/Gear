package com.github.andyshao.system;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.github.andyshao.reflect.ClassOperation;
import com.github.andyshao.reflect.FieldOperation;
import com.github.andyshao.reflect.MethodOperation;

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
public class ClassAnalysis implements Task {
    private static final String CLASS = "--class";
    private static final String FIELDS = "--fields";
    public static final String KEY_WORDS = "-classPrint";
    private static final String METHODS = "--methods";
    private volatile Task nextTask = Task.EMTPY_TASK;

    @Override
    public Task getNextTask() {
        return this.nextTask;
    }

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return false;
        else return args[0].equals(ClassAnalysis.KEY_WORDS);
    }

    @Override
    public void process(String[] args) {
        String[] commands = null;
        Class<?> clz = null;
        if (args.length <= 2 || !args[1].startsWith("--")) {
            commands = new String[] { ClassAnalysis.CLASS , ClassAnalysis.FIELDS , ClassAnalysis.METHODS };
            clz = ClassOperation.forName(args[1]);
        } else {
            int i = 1;
            List<String> tmp = new ArrayList<>();
            for (; i < args.length ; i++)
                if (args[i].startsWith("--")) tmp.add(args[i]);
                else break;
            clz = ClassOperation.forName(args[i]);
            commands = tmp.toArray(new String[tmp.size()]);
        }
        for (String command : commands)
            switch (command) {
            case METHODS:
                Method[] methods = MethodOperation.getAllMethods(clz);
                for (Method method : methods) {
                    StringBuilder info = new StringBuilder();
                    final int modifiers = method.getModifiers();
                    if (Modifier.isPublic(modifiers)) info.append("public ");
                    else if (Modifier.isPrivate(modifiers)) info.append("private ");
                    else if (Modifier.isProtected(modifiers)) info.append("protected ");
                    if (Modifier.isStatic(modifiers)) info.append("static ");
                    if (Modifier.isFinal(modifiers)) info.append("final ");
                    else if (Modifier.isAbstract(modifiers)) info.append("abstract ");
                    info.append(method.getReturnType().getName());
                    info.append(' ');
                    info.append(method.getName());
                    info.append('(');
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    for (int i = 0 ; i < parameterTypes.length ; i++) {
                        info.append(parameterTypes[i].getName());
                        if (i != parameterTypes.length - 1) info.append(',');
                    }
                    info.append(')');
                    info.append(';');
                    System.out.println(info);
                }
                break;
            case FIELDS:
                Field[] fields = FieldOperation.getAllField(clz);
                for (Field field : fields) {
                    StringBuilder info = new StringBuilder();
                    final int modifiers = field.getModifiers();
                    if (Modifier.isPublic(modifiers)) info.append("public ");
                    else if (Modifier.isPrivate(modifiers)) info.append("private ");
                    else if (Modifier.isProtected(modifiers)) info.append("protected ");
                    if (Modifier.isStatic(modifiers)) info.append("static ");
                    if (Modifier.isFinal(modifiers)) info.append("final ");
                    else if (Modifier.isVolatile(modifiers)) info.append("volatile ");
                    info.append(field.getType());
                    info.append(' ');
                    info.append(field.getName());
                    info.append(';');
                    System.out.println(info);
                }
                break;
            case CLASS: {
                StringBuffer info = new StringBuffer();
                final int modifers = clz.getModifiers();
                if (Modifier.isPublic(modifers)) info.append("public ");
                else if (Modifier.isPrivate(modifers)) info.append("private ");
                else if (Modifier.isProtected(modifers)) info.append("protected ");
                if (Modifier.isStatic(modifers)) info.append("static ");
                if (Modifier.isFinal(modifers)) info.append("final ");
                else if (Modifier.isAbstract(modifers)) info.append("abstract ");
                if (clz.isInterface()) info.append("interface ");
                else info.append("class ");
                info.append(clz.getName());
                info.append(';');
                System.out.println(info);
                break;
            }
            default:
                break;
            }
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
