package com.github.andyshao.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import com.github.andyshao.nio.ByteBufferReaderTest;

public class BuffereReaderTest {
    @Test
    public void efficientTest() throws IOException , URISyntaxException {
        long time = 0L;
        try (FileReader fileReader = new FileReader(ByteBufferReaderTest.getBigFilePath().toFile()); BufferedReader reader = new BufferedReader(fileReader);) {
            time = System.currentTimeMillis();
            while (reader.readLine() != null);
            time = System.currentTimeMillis() - time;
        }
        System.out.println("BuffereReader read line use " + time + " millis secondes");
    }
}
