package com.github.andyshao.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
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
public class UnblockingTcpServer implements TcpServer {
    /**exception*/
    public static final String EXCEPTION = UnblockingTcpServer.class.getName() + "_EXCEPTION";
    /**socket channel*/
    public static final String SOCKET_CHANNEL = UnblockingTcpServer.class.getName() + "_SOCKET_CHANNEL";
    /**error process*/
    protected Consumer<MessageContext> errorProcess = (context) -> {
        Exception e = (Exception) context.get(UnblockingTcpServer.EXCEPTION);
        e.printStackTrace();
    };
    /**executor service*/
    protected ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);
    /**is processing*/
    protected volatile boolean isProcessing = false;
    /**is waiting for close*/
    protected volatile boolean isWaitingForClose = false;
    /**message factory*/
    protected MessageFactory messageFactory;
    /**port*/
    protected int port = 8000;
    /**selector*/
    protected Selector selector = null;
    /**server socket channel*/
    protected ServerSocketChannel serverSocketChannel = null;

    /**
     * build {@link UnblockingTcpServer}
     * @param messageFactory {@link MessageFactory}
     */
    public UnblockingTcpServer(MessageFactory messageFactory) {
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
        this.selector.close();
        this.serverSocketChannel.close();
        this.executorService.shutdown();
    }

    private void myOpen() throws IOException , SocketException , ClosedChannelException {
        this.isWaitingForClose = false;
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().setReuseAddress(true);
        this.serverSocketChannel.configureBlocking(false);
        this.serverSocketChannel.socket().bind(new InetSocketAddress(this.port));

        this.serverSocketChannel.register(this.selector , SelectionKey.OP_ACCEPT);
        while (this.selector.select() > 0 && !this.isWaitingForClose) {
            Set<SelectionKey> readyKeys = this.selector.selectedKeys();
            Iterator<SelectionKey> it = readyKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = null;
                try {
                    key = it.next();
                    it.remove();
                    if (key.isAcceptable()) this.processAcceptable(this.selector , key);
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

    /**
     * process acceptable
     * @param selector {@link Selector}
     * @param key {@link SelectionKey}
     * @throws IOException IO error
     */
    protected void processAcceptable(Selector selector , SelectionKey key) throws IOException {
        final MessageContext context = this.messageFactory.buildMessageContext();
        final ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        final SocketChannel socketChannel = ssc.accept();
        context.put(UnblockingTcpServer.SOCKET_CHANNEL , socketChannel);
        socketChannel.configureBlocking(false);
        final Socket socket = socketChannel.socket();
        context.put(TcpMessageContext.INPUT_INET_ADDRESS , socket.getInetAddress());
        context.put(TcpMessageContext.INPUT_INET_PORT , socket.getPort());
        context.put(MessageContext.IS_WAITING_FOR_RECEIVE , true);
        socketChannel.register(selector , SelectionKey.OP_READ | SelectionKey.OP_WRITE , context);
    }

    /**
     * process readable
     * @param key {@link SelectionKey}
     * @throws IOException IO error
     */
    protected void processReadable(SelectionKey key) throws IOException {
        final MessageContext context = (MessageContext) key.attachment();
        if (context.isWaitingForReceive()) {
            final SocketChannel socketChannel = (SocketChannel) key.channel();
            context.put(UnblockingTcpServer.SOCKET_CHANNEL , socketChannel);
            this.messageFactory.buildMessageReadable(context).read(socketChannel , context);
            if (!context.isWaitingForReceive()) {
                context.put(MessageContext.IS_WAITING_FOR_DECODE , true);
                this.executorService.submit(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        try {
                            UnblockingTcpServer.this.messageFactory.buildMessageDecoder(context).decode(context);
                            context.put(MessageContext.IS_WAITING_FOR_DECODE , false);
                            context.put(MessageContext.IS_WAITING_FOR_PROCESS , true);
                            UnblockingTcpServer.this.messageFactory.buildMessageProcess(context).process(context);
                            context.put(MessageContext.IS_WAITING_FOR_PROCESS , false);
                            context.put(MessageContext.IS_WAITING_FOR_ENCODE , true);
                            UnblockingTcpServer.this.messageFactory.buildMessageEncoder(context).encode(context);
                            context.put(MessageContext.IS_WAITING_FOR_ENCODE , false);
                            context.put(MessageContext.IS_WAITING_FOR_SENDING , true);
                        } catch (Exception e) {
                            context.put(UnblockingTcpServer.EXCEPTION , e);
                            context.put(UnblockingTcpServer.SOCKET_CHANNEL , socketChannel);
                            UnblockingTcpServer.this.errorProcess.accept(context);
                            throw e;
                        }
                        return null;
                    }
                });
            }
        }
    }

    /**
     * process writable
     * @param key {@link SelectionKey}
     * @throws IOException IO error
     */
    protected void processWritable(SelectionKey key) throws IOException {
        final MessageContext context = (MessageContext) key.attachment();
        if (context.isWaitingForSending()) {
            final SocketChannel socketChannel = (SocketChannel) key.channel();
            context.put(UnblockingTcpServer.SOCKET_CHANNEL , socketChannel);
            this.messageFactory.buildMessageWritable(context).write(socketChannel , context);
        }
    }

    /**
     * set error process
     * @param errorProcess {@link Consumer}
     */
    public void setErrorProcess(Consumer<MessageContext> errorProcess) {
        this.errorProcess = errorProcess;
    }

    /**
     * set executor service
     * @param executorService {@link ExecutorService}
     */
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * set port
     * @param port port number
     */
    public void setPort(int port) {
        this.port = port;
    }
}
