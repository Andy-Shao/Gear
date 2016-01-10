package com.github.andyshao.io;

import java.io.IOException;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 4, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class AsynchronousTcpServer implements TcpServer {
    protected AsynchronousServerSocketChannel asynchronousServerSocketChannel;
    protected ExecutorService executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);
    protected volatile boolean isProcessing = false;
    protected volatile boolean isWaitingForClose = false;
    protected MessageFactory messageFactory;
    protected int port = 8000;

    public AsynchronousTcpServer(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    public void close() throws IOException {
        this.isWaitingForClose = true;
        while (this.isProcessing)
            try {
                TimeUnit.MICROSECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        this.asynchronousServerSocketChannel.close();
    }

    @Override
    public void open() throws IOException {
        //TODO
    }

    public void setPort(int port) {
        this.port = port;
    }
}
