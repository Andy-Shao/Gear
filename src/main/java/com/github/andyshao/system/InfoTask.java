package com.github.andyshao.system;

import java.io.File;
import java.io.IOException;
import java.nio.channels.Channels;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.github.andyshao.lang.GeneralSystemProperty;
import com.github.andyshao.nio.BufferReader;
import com.github.andyshao.nio.StringBufferReader;

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
public class InfoTask implements Task {
    public static final String KEY_WORDS = "-info";
    private static final String TARGET = "META-INF/MANIFEST.MF";
    private volatile Task nextTask = Task.EMTPY_TASK;

    @Override
    public boolean isDuty(String[] args) {
        if (args == null || args.length == 0) return false;
        if (args[0].equals(InfoTask.KEY_WORDS)) return true;
        return false;
    }

    @Override
    public Task nextTask() {
        return this.nextTask;
    }

    @Override
    public void process(String[] args) {
        args[0] = VersionTask.KEY_WORDS;
        this.run(args);
        try (final ZipFile zipFile = new ZipFile(new File(GeneralSystemProperty.JAVA_CLASS_PATH.value()))) {
            final ZipEntry zipEntry = zipFile.getEntry(InfoTask.TARGET);
            try (
                final StringBufferReader reader =
                    new StringBufferReader(Channels.newChannel(zipFile.getInputStream(zipEntry)));) {
                reader.setFindSeparatePoint((buffer) -> new BufferReader.SeparatePoint(-1));
                System.out.println(reader.read());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

}
