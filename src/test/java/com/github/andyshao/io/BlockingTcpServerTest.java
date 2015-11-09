package com.github.andyshao.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BlockingTcpServerTest {

    public static void main(String[] args) throws IOException {
        try (BlockingTcpServer blockingTcpServer = new BlockingTcpServer(new BlockingEchoMessageFactory());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
            blockingTcpServer.open();
            System.out.println("Service open!");
            reader.readLine();
            System.out.println("Service shutdown.");
        }
        System.out.println("END!!");
    }
}
