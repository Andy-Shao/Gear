package com.github.andyshao.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class BlockingTcpClientTest {

    public static void main(String[] args) throws IOException {
        System.out.println("Input message what you want to send: ");
        final BlockingEchoMessageFactory messageFactory = new BlockingEchoMessageFactory();
        try (TcpClient tcpClient = new BlockingTcpClient(messageFactory); BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
            MessageContext context = messageFactory.buildMessageContext();
            context.put(TcpMessageContext.OUTPUT_INET_ADDRESS , InetAddress.getLocalHost());
            context.put(TcpMessageContext.OUTPUT_INET_PORT, 8000);
            context.put(MessageContext.OUTPUT_MESSAGE_OBJECT , reader.readLine());
            tcpClient.open(context);
            tcpClient.send(context);
        }
        System.out.println("FINISHING!!!");
    }
}
