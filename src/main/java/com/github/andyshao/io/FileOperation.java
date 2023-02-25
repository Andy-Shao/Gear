package com.github.andyshao.io;

import java.io.File;
import java.nio.file.Path;

/**
 * Title:<br>
 * Descript:<br>
 * Copyright: Copyright(c) Feb 25, 2023<br>
 * Encoding: UNIX UTF-8
 *
 * @author Andy.Shao
 */
public final class FileOperation {
    private FileOperation() {
        throw new AssertionError(String.format("No %s construction support!", FileOperation.class.getName()));
    }

    public static void forceOmit(Path filePath){
        forceOmit(filePath.toFile());
    }

    public static void forceOmit(File file){
        if(file.isDirectory()){
            for(File child : file.listFiles())
                forceOmit(child);
        }
        file.delete();
    }
}
