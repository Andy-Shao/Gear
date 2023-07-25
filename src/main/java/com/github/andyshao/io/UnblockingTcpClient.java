package com.github.andyshao.io;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
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
public class UnblockingTcpClient implements TcpClient {
    class SendTask {
        protected Date date;
        protected MessageContext messageContext;
        protected MessageProcess messageProcess;

        public SendTask(MessageContext messageContext , MessageProcess messageProcess) {
            this.messageContext = messageContext;
            this.messageProcess = messageProcess;
            this.date = new Date();
        }
    }

    /**exception*/
    public static final String EXCEPTION = UnblockingTcpClient.class.getName() + "_EXCEPTION";
    /**socket channel*/
    public static final String SOCKET_CHANNEL = UnblockingTcpClient.class.getName() + "_SOCKET_CHANNEL";
    /**error process*/
    protected Consumer<MessageContext> errorProcess = (context) -> {
        Exception e = (Exception) context.get(UnblockingTcpClient.EXCEPTION);
        e.printStackTrace();
    };
    /**{@link ExecutorService}*/
    protected ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);

    /**is processing (default value is false)*/
    protected volatile boolean isProcessing = false;

    /**is waiting for close (default value is false)*/
    protected volatile boolean isWaitingForClose = false;
    /**{@link MessageFactory}*/
    protected MessageFactory messageFactory;
    /**{@link Selector}*/
    protected Selector selector;
    /**send task deque*/
    protected ConcurrentLinkedDeque<SendTask> sendTasks = new ConcurrentLinkedDeque<UnblockingTcpClient.SendTask>();
    /**{@link SocketChannel}*/
    protected SocketChannel socketChannel;

    /**
     * Build {@link UnblockingTcpClient}
     * @param messageFactory {@link MessageFactory}
     */
    public UnblockingTcpClient(MessageFactory messageFactory) {
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
        if (this.selector != null) this.selector.close();
        if (this.socketChannel != null) this.socketChannel.close();
        this.executorService.shutdown();
    }

    void myOpen() throws IOException {
        while (this.selector.select() > 0 && !this.isWaitingForClose) {
            Set<SelectionKey> readyKeys = this.selector.selectedKeys();
            Iterator<SelectionKey> it = readyKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                if (key.isWritable()) this.processWrite(key);
                else if (key.isReadable()) this.processRead(key);
            }
        }
    }

    @Override
    public void open(MessageContext context) throws IOException {
        this.isWaitingForClose = false;
        this.socketChannel = SocketChannel.open();
        InetAddress is = (InetAddress) context.get(TcpMessageContext.OUTPUT_INET_ADDRESS);
        InetSocketAddress isa = new InetSocketAddress(is , (Integer) context.get(TcpMessageContext.OUTPUT_INET_PORT));
        this.socketChannel.connect(isa);
        this.socketChannel.configureBlocking(false);
        this.selector = Selector.open();
        this.socketChannel.register(this.selector , SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        this.executorService.submit(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                try {
                    UnblockingTcpClient.this.myOpen();
                } catch (Exception e) {
                    context.put(UnblockingTcpClient.EXCEPTION , e);
                    context.put(UnblockingTcpClient.SOCKET_CHANNEL , UnblockingTcpClient.this.socketChannel);
                    UnblockingTcpClient.this.errorProcess.accept(context);
                    throw e;
                }
                return null;
            }
        });
    }

    private void processRead(SelectionKey key) throws IOException {
        if (this.sendTasks.size() == 0) return;
        MessageContext context = this.sendTasks.getLast().messageContext;
        if (context.isWaitingForReceive()) {
            this.messageFactory.buildMessageReadable(context).read((SocketChannel) key.channel() , context);
            if (!context.isWaitingForReceive()) {
                context.put(MessageContext.IS_WAITING_FOR_DECODE , true);
                this.executorService.submit(new Callable<Void>() {

                    @Override
                    public Void call() throws Exception {
                        try {
                            UnblockingTcpClient.this.messageFactory.buildMessageDecoder(context).decode(context);
                            context.put(MessageContext.IS_WAITING_FOR_DECODE , false);
                            context.put(MessageContext.IS_WAITING_FOR_PROCESS , true);
                            UnblockingTcpClient.this.sendTasks.getLast().messageProcess.process(context);
                            UnblockingTcpClient.this.sendTasks.removeLast();
                            context.put(MessageContext.IS_WAITING_FOR_PROCESS , false);
                        } catch (Exception e) {
                            context.put(UnblockingTcpClient.EXCEPTION , e);
                            context.put(UnblockingTcpClient.SOCKET_CHANNEL , key.channel());
                            UnblockingTcpClient.this.errorProcess.accept(context);
                            throw e;
                        }
                        return null;
                    }
                });

            }
        }
    }

    private void processWrite(SelectionKey key) throws IOException {
        if (this.sendTasks.size() == 0) return;
        MessageContext context = this.sendTasks.getLast().messageContext;
        if (context.isWaitingForEncode()) {
            this.messageFactory.buildMessageEncoder(context).encode(context);
            context.put(MessageContext.IS_WAITING_FOR_ENCODE , false);
            context.put(MessageContext.IS_WAITING_FOR_SENDING , true);
        }
        if (context.isWaitingForSending()) this.messageFactory.buildMessageWritable(context).write((SocketChannel) key.channel() , context);
    }

    @Override
    public void send(MessageContext context) throws IOException {
        this.send(context , this.messageFactory.buildMessageProcess(context));
    }

    @Override
    public void send(MessageContext context , MessageProcess process) throws IOException {
        context.put(MessageContext.IS_WAITING_FOR_ENCODE , true);
        this.sendTasks.add(new SendTask(context , process));
    }

    /**
     * Set error process
     * @param errorProcess {@link Consumer}
     */
    public void setErrorProcess(Consumer<MessageContext> errorProcess) {
        this.errorProcess = errorProcess;
    }

    /**
     * Set {@link ExecutorService}
     * @param executorService {@link ExecutorService}
     */
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
