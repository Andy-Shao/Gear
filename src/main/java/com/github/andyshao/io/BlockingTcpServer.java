package com.github.andyshao.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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
public class BlockingTcpServer implements TcpServer {
    public static final String EXCEPTION = BlockingTcpServer.class.getName() + "_EXCEPTION";
    public static final String SOCKET_CHANNEL = BlockingTcpServer.class.getName() + "_SOCKET_CHANNEL";
    protected Consumer<MessageContext> errorProcess = (context) -> {
        Exception e = (Exception) context.get(BlockingTcpServer.EXCEPTION);
        e.printStackTrace();
    };
    protected ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);
    protected volatile boolean isProcessing = false;
    protected volatile boolean isWaitingForClose = false;
    protected MessageFactory messageFactory;
    protected int port = 8000;
    protected ServerSocketChannel serverSocketChannel = null;

    public BlockingTcpServer(MessageFactory messageFactory) {
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
        if (this.serverSocketChannel != null) this.serverSocketChannel.close();
        this.executorService.shutdown();
    }

    private void myOpen() throws SocketException , IOException {
        this.isWaitingForClose = false;
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().setReuseAddress(true);
        this.serverSocketChannel.socket().bind(new InetSocketAddress(this.port));
        while (!this.isWaitingForClose) {
            final SocketChannel socketChannel = this.serverSocketChannel.accept();
            this.isProcessing = true;
            this.executorService.submit(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    final Socket socket = socketChannel.socket();
                    final MessageContext context = BlockingTcpServer.this.messageFactory.buildMessageContext();
                    context.put(TcpMessageContext.INPUT_INET_ADDRESS , socket.getInetAddress());
                    context.put(TcpMessageContext.INPUT_INET_PORT , socket.getPort());
                    context.put(MessageContext.IS_WAITING_FOR_RECEIVE , true);

                    try {
                        BlockingTcpServer.this.messageFactory.builMessageReadable(context).read(Channels.newChannel(socket.getInputStream()) , context);
                        context.put(MessageContext.IS_WAITING_FOR_RECEIVE , false);
                        context.put(MessageContext.IS_WAITING_FOR_DECODE , true);
                        BlockingTcpServer.this.messageFactory.buildMessageDecoder(context).decode(context);
                        context.put(MessageContext.IS_WAITING_FOR_DECODE , false);
                        context.put(MessageContext.IS_WAITING_FOR_PROCESS , true);
                        BlockingTcpServer.this.messageFactory.buildMessageProcess(context).process(context);
                        context.put(MessageContext.IS_WAITING_FOR_PROCESS , false);
                        context.put(MessageContext.IS_WAITING_FOR_ENCODE , true);
                        BlockingTcpServer.this.messageFactory.buildMessageEncoder(context).encode(context);
                        context.put(MessageContext.IS_WAITING_FOR_ENCODE , false);
                        context.put(MessageContext.IS_WAITING_FOR_SENDING , true);
                        BlockingTcpServer.this.messageFactory.buildMessageWritable(context).write(Channels.newChannel(socket.getOutputStream()) , context);
                        context.put(MessageContext.IS_WAITING_FOR_SENDING , false);
                    } catch (Exception e) {
                        context.put(BlockingTcpServer.SOCKET_CHANNEL , socketChannel);
                        context.put(BlockingTcpServer.EXCEPTION , e);
                        BlockingTcpServer.this.errorProcess.accept(context);
                    }
                    return null;
                }
            });
            this.isProcessing = false;
        }
    }

    @Override
    public void open() throws IOException {
        this.executorService.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                BlockingTcpServer.this.myOpen();
                return null;
            }
        });
    }

    public void setErrorProcess(Consumer<MessageContext> errorProcess) {
        this.errorProcess = errorProcess;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
