package com.github.andyshao.io;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.github.andyshao.nio.BufferReader;
import com.github.andyshao.nio.ByteBufferReader;

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
public abstract class BlockingTcpServer implements Closeable {
    private ExecutorService executorService = Executors
        .newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);
    private volatile boolean isWaitingForClose = false;
    protected MessageFactory messageFactory;
    private int port = 8000;

    public BlockingTcpServer(MessageFactory messageFactory) throws IOException {
        this.messageFactory = messageFactory;
    }

    @Override
    public void close() throws IOException {
        this.isWaitingForClose = false;
    }

    protected abstract void errorProcess(Socket socket , MessageContext context , Exception e);

    public int getPort() {
        return this.port;
    }

    public void open() throws IOException {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.socket().setReuseAddress(true);
            serverSocketChannel.socket().bind(new InetSocketAddress(this.port));
            while (this.isWaitingForClose)
                try (final SocketChannel socketChannel = serverSocketChannel.accept();) {
                    this.executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            final Socket socket = socketChannel.socket();
                            final MessageContext context = BlockingTcpServer.this.messageFactory.buildMessageContext();
                            context.put(MessageContext.INPUT_INET_ADDRESS , socket.getInetAddress());
                            context.put(MessageContext.INPUT_INET_PORT , socket.getPort());
                            context.put(MessageContext.IS_WAITING_FOR_RECEIVE , true);
                            context.put(MessageContext.IS_WAITING_FOR_SENDING , false);
                            context.put(MessageContext.MESSAGE_INBOUND_SIGN , false);
                            context.put(MessageContext.MESSAGE_OUTBOUND_SIGN , false);

                            try {
                                ByteBufferReader reader =
                                    new ByteBufferReader(Channels.newChannel(socket.getInputStream()));
                                reader.setFindSeparatePoint((buffer) -> new BufferReader.SeparatePoint(-1));
                                context.put(MessageContext.INPUT_MESSAGE_BYTES , reader.read());
                                context.put(MessageContext.MESSAGE_INBOUND_SIGN , true);
                                context.put(MessageContext.IS_WAITING_FOR_RECEIVE , false);
                                BlockingTcpServer.this.messageFactory.buildMessageDecoder(context).decode(context);
                                BlockingTcpServer.this.messageFactory.buildMessageProcess(context).process(context);
                                context.put(MessageContext.MESSAGE_INBOUND_SIGN , false);
                                context.put(MessageContext.MESSAGE_OUTBOUND_SIGN , true);
                                context.put(MessageContext.IS_WAITING_FOR_SENDING , true);
                                BlockingTcpServer.this.messageFactory.buildMessageEncoder(context).encode(context);
                                socket.getOutputStream().write(
                                    (byte[]) context.get(MessageContext.OUTPUT_MESSAGE_BYTES));
                            } catch (Exception e) {
                                BlockingTcpServer.this.errorProcess(socket , context , e);
                            }
                        }
                    });
                }
        }
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setWaitingForClose(boolean isWaitingForClose) {
        this.isWaitingForClose = isWaitingForClose;
    }
}
