package com.github.andyshao.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UnblockingTcpServerTest {

    public static void main(String[] args) throws IOException {
        try (TcpServer tcpServer = new UnblockingTcpServer(new UnblockingEchoMessageFactory()); BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
            tcpServer.open();
            System.out.println("Service open!");
            reader.readLine();
            System.out.println("Service shutdown.");
        }
        System.out.println("END!!");
    }
}
