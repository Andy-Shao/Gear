package com.github.andyshao.io;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
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
public class BlockingTcpClient implements TcpClient {
    /**Exception*/
    public static final String EXCEPTION = BlockingTcpClient.class.getName() + "_EXCEPTION";
    /**error process*/
    protected Consumer<MessageContext> errorProcess = (context) -> {
    };
    /**process tag*/
    protected volatile boolean isProcessing = false;
    /**message factory*/
    protected MessageFactory messageFactory;
    /**socket channel*/
    protected SocketChannel socketChannel = null;

    /**
     * Build {@link BlockingTcpClient}
     * @param messageFactory {@link MessageFactory}
     */
    public BlockingTcpClient(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    public void close() throws IOException {
        if (this.socketChannel != null) {
            while (this.isProcessing)
                try {
                    TimeUnit.MICROSECONDS.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            this.socketChannel.close();
        }
    }

    @Override
    public void open(MessageContext context) throws IOException {
        this.socketChannel = SocketChannel.open();
        InetSocketAddress isa = new InetSocketAddress((InetAddress) context.get(TcpMessageContext.OUTPUT_INET_ADDRESS) , (Integer) context.get(TcpMessageContext.OUTPUT_INET_PORT));
        this.socketChannel.connect(isa);
    }

    @Override
    public void send(MessageContext context) throws IOException {
        this.send(context , this.messageFactory.buildMessageProcess(context));
    }

    @Override
    public void send(MessageContext context , MessageProcess process) throws IOException {
        try {
            this.isProcessing = true;
            Socket socket = this.socketChannel.socket();
            context.put(MessageContext.IS_WAITING_FOR_ENCODE , true);
            this.messageFactory.buildMessageEncoder(context).encode(context);
            context.put(MessageContext.IS_WAITING_FOR_ENCODE , false);
            context.put(MessageContext.IS_WAITING_FOR_SENDING , true);
            this.messageFactory.buildMessageWritable(context).write(Channels.newChannel(socket.getOutputStream()) , context);
            context.put(MessageContext.IS_WAITING_FOR_SENDING , false);
            context.put(MessageContext.IS_WAITING_FOR_RECEIVE , true);
            this.messageFactory.buildMessageReadable(context).read(Channels.newChannel(socket.getInputStream()) , context);
            context.put(MessageContext.IS_WAITING_FOR_RECEIVE , false);
            context.put(MessageContext.IS_WAITING_FOR_DECODE , true);
            this.messageFactory.buildMessageDecoder(context).decode(context);
            context.put(MessageContext.IS_WAITING_FOR_DECODE , false);
            context.put(MessageContext.IS_WAITING_FOR_PROCESS , true);
            process.process(context);
            context.put(MessageContext.IS_WAITING_FOR_PROCESS , false);
        } catch (Exception e) {
            context.put(BlockingTcpClient.EXCEPTION , e);
            this.errorProcess.accept(context);
            if (e instanceof IOException) throw (IOException) e;
            else throw new RuntimeException(e);
        } finally {
            this.isProcessing = false;
        }
    }

    /**
     * Set error process
     * @param errorProcess {@link MessageContext}
     */
    public void setErrorProcess(Consumer<MessageContext> errorProcess) {
        this.errorProcess = errorProcess;
    }
}
