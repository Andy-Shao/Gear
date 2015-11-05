package com.github.andyshao.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.github.andyshao.nio.ByteBufferOperation;
import com.github.andyshao.reflect.ArrayOperation;

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
public abstract class UnblockingTcpServer implements TcpServer {
    public static final String EXCEPTION = UnblockingTcpServer.class.getName() + "_EXCEPTION";
    public static final String SOCKET_CHANNEL = UnblockingTcpServer.class.getName() + "_SOCKET_CHANNEL";
    protected Consumer<MessageContext> errorProcess = (context) -> {
    };
    protected ExecutorService executorService = Executors
        .newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);
    protected volatile boolean isWaitingForClose = false;
    protected MessageFactory messageFactory;
    protected int port = 8000;

    public UnblockingTcpServer(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    public void close() throws IOException {
        this.isWaitingForClose = true;
    }

    private void myOpen() throws IOException , SocketException , ClosedChannelException {
        this.isWaitingForClose = false;
        try (final Selector selector = Selector.open();
            final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();) {
            serverSocketChannel.socket().setReuseAddress(true);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(this.port));

            serverSocketChannel.register(selector , SelectionKey.OP_ACCEPT);
            while (selector.select() > 0 && !this.isWaitingForClose) {
                Set<SelectionKey> readyKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = readyKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = null;
                    try {
                        key = it.next();
                        it.remove();
                        if (key.isAcceptable()) this.processAcceptable(selector , key);
                        else if (key.isReadable()) this.processReadable(key);
                        else if (key.isWritable()) this.processWritable(key);
                    } catch (Exception e) {
                        if (key != null) {
                            Object attachment = key.attachment();
                            if (attachment != null) {
                                MessageContext context = (MessageContext) attachment;
                                context.put(UnblockingTcpServer.EXCEPTION , e);
                                this.errorProcess.accept(context);
                            }
                            key.cancel();
                            key.channel().close();
                        }
                        if (e instanceof IOException) throw e;
                        else throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public void open() throws IOException {
        this.executorService.submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                UnblockingTcpServer.this.myOpen();
                return null;
            }
        });
    }

    protected void processAcceptable(Selector selector , SelectionKey key) throws IOException {
        MessageContext context = this.messageFactory.buildMessageContext();
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = ssc.accept();
        context.put(UnblockingTcpServer.SOCKET_CHANNEL , socketChannel);
        socketChannel.configureBlocking(false);
        context.put(MessageContext.INPUT_INET_ADDRESS , socketChannel.socket().getInetAddress());
        context.put(MessageContext.INPUT_INET_PORT , socketChannel.socket().getPort());
        context.put(MessageContext.IS_WAITING_FOR_RECEIVE , true);
        socketChannel.register(selector , SelectionKey.OP_READ | SelectionKey.OP_WRITE , context);
    }

    protected void processReadable(SelectionKey key) throws IOException {
        final MessageContext context = (MessageContext) key.attachment();
        if (context.isWaitingForSending()) {
            byte[] writeBytes = (byte[]) context.get(MessageContext.OUTPUT_MESSAGE_BYTES);
            if (writeBytes.length != 0) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                context.put(UnblockingTcpServer.SOCKET_CHANNEL , socketChannel);
                ByteBuffer outputBuffer = ByteBuffer.wrap(writeBytes);
                int writeStatus = socketChannel.write(outputBuffer);
                if (writeStatus != 0) context.put(MessageContext.OUTPUT_MESSAGE_BYTES ,
                    ArrayOperation.splitArray(writeStatus , writeStatus , writeBytes.length));
            } else context.put(MessageContext.IS_WAITING_FOR_SENDING , false);
        }
    }

    protected void processWritable(SelectionKey key) throws IOException {
        final MessageContext context = (MessageContext) key.attachment();
        if (context.isWaitingForRecieve()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            context.put(UnblockingTcpServer.SOCKET_CHANNEL , socketChannel);
            ByteBuffer readBuff = ByteBuffer.allocate(1024);
            int readStatus = socketChannel.read(readBuff);
            readBuff.flip();
            byte[] info =
                ArrayOperation.mergeArray(byte[].class , (byte[]) context.get(MessageContext.INPUT_MESSAGE_BYTES) ,
                    ByteBufferOperation.usedArray(readBuff));
            context.put(MessageContext.INPUT_MESSAGE_BYTES , info);
            if (readStatus == -1) {
                context.put(MessageContext.IS_WAITING_FOR_RECEIVE , false);
                context.put(MessageContext.IS_WAITING_FOR_DECODE , true);
                this.executorService.submit(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        UnblockingTcpServer.this.messageFactory.buildMessageDecoder(context).decode(context);
                        context.put(MessageContext.IS_WAITING_FOR_DECODE , false);
                        context.put(MessageContext.IS_WAITING_FOR_PROCESS , true);
                        UnblockingTcpServer.this.messageFactory.buildMessageProcess(context).process(context);
                        context.put(MessageContext.IS_WAITING_FOR_PROCESS , false);
                        context.put(MessageContext.IS_WAITING_FOR_ENCODE , true);
                        UnblockingTcpServer.this.messageFactory.buildMessageEncoder(context).encode(context);
                        context.put(MessageContext.IS_WAITING_FOR_ENCODE , false);
                        context.put(MessageContext.IS_WAITING_FOR_SENDING , true);
                        return null;
                    }
                });
            }
        }
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
