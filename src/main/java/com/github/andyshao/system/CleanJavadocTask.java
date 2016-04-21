package com.github.andyshao.system;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import com.github.andyshao.lang.GeneralSystemProperty;
import com.github.andyshao.nio.ByteBufferOperation;
import com.github.andyshao.reflect.ArrayOperation;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 20, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class CleanJavadocTask implements Task {
    private static final String HEAD = "/**";
    public static final String KEY_WORDS = "-cleanJavadoc";
    private static final String TAIL = "*/";
    private volatile Task nextTask = Task.EMTPY_TASK;

    @Override
    public Task getNextTask() {
        return this.nextTask;
    }

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return false;
        return args[0].equals(CleanJavadocTask.KEY_WORDS);
    }

    private void myProcess(final File file , String encoding) throws IOException {
        if (file.isFile()) {
            System.out.println("Process " + file.getPath());
            final Path path = file.toPath();
            final ByteBuffer context = ByteBuffer.wrap(Files.readAllBytes(path));
            final byte[] head = CleanJavadocTask.HEAD.getBytes(encoding);
            final byte[] tail = CleanJavadocTask.TAIL.getBytes(encoding);
            byte[] tmp = new byte[0];

            int index = -1;
            while ((index = ByteBufferOperation.indexOf(context , head)) != -1) {
                tmp = ArrayOperation.mergeArray(byte[].class , tmp , ByteBufferOperation.getBytes(context , context.position() , index - context.position()));
                context.position(index);
                context.position(ByteBufferOperation.indexOf(context , tail) + tail.length);
            }
            tmp = ArrayOperation.mergeArray(byte[].class , tmp , ByteBufferOperation.usedArray(context));
            Files.write(path , tmp);
        } else if (file.isDirectory()) for (File child : file.listFiles())
            this.myProcess(child , encoding);
    }

    @Override
    public void process(String[] args) {
        if (args.length < 2 || args[1] == null) {
            System.out.println("You have to input the filePath.");
            System.out.println("For detail of this command try to use -help");
            return;
        }
        String filePath = args[1];
        String encoding = null;
        if (args.length >= 3) encoding = args[2];
        if (encoding == null) encoding = GeneralSystemProperty.FILE_ENCODING.value();
        try {
            this.myProcess(new File(filePath) , encoding);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
