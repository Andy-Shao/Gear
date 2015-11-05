package com.github.andyshao.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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
public class BlockingShortTcpClient implements Closeable {
    public static final String EXCEPTION = BlockingShortTcpClient.class.getName() + "_EXCEPTION";
    protected Consumer<MessageContext> errorProcess = (context) -> {
    };
    protected volatile boolean isProcessing = false;
    protected MessageFactory messageFactory;
    protected SocketChannel socketChannel = null;

    public BlockingShortTcpClient(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    public void close() throws IOException {
        while (this.isProcessing)
            try {
                TimeUnit.MICROSECONDS.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        this.socketChannel.close();
    }

    protected void read(MessageContext context) throws IOException {
        InputStream inputStream = this.socketChannel.socket().getInputStream();
        ByteBufferReader reader = new ByteBufferReader(Channels.newChannel(inputStream));
        reader.setFindSeparatePoint((buffer) -> new BufferReader.SeparatePoint(-1));
        context.put(MessageContext.INPUT_MESSAGE_BYTES , reader.read());
    }

    public void send(MessageContext context) throws IOException {
        try {
            this.isProcessing = true;
            this.socketChannel = SocketChannel.open();
            InetSocketAddress isa =
                new InetSocketAddress((InetAddress) context.get(MessageContext.OUTPUT_INET_ADDRESS) ,
                    (Integer) context.get(MessageContext.OUTPU_INET_PORT));
            this.socketChannel.connect(isa);
            context.put(MessageContext.IS_WAITING_FOR_ENCODE , true);
            this.messageFactory.buildMessageEncoder(context).encode(context);
            context.put(MessageContext.IS_WAITING_FOR_ENCODE , false);
            context.put(MessageContext.IS_WAITING_FOR_SENDING , true);
            this.write(context);
            context.put(MessageContext.IS_WAITING_FOR_SENDING , false);
            context.put(MessageContext.IS_WAITING_FOR_RECEIVE , true);
            this.read(context);
            context.put(MessageContext.IS_WAITING_FOR_RECEIVE , false);
            context.put(MessageContext.IS_WAITING_FOR_DECODE , true);
            this.messageFactory.buildMessageDecoder(context).decode(context);
            context.put(MessageContext.IS_WAITING_FOR_DECODE , false);
            context.put(MessageContext.IS_WAITING_FOR_PROCESS , true);
            this.messageFactory.buildMessageProcess(context).process(context);
            context.put(MessageContext.IS_WAITING_FOR_PROCESS , false);
        } catch (Exception e) {
            context.put(BlockingShortTcpClient.EXCEPTION , e);
            this.errorProcess.accept(context);
            if (e instanceof IOException) throw (IOException) e;
            else throw new RuntimeException(e);
        } finally {
            this.isProcessing = false;
        }
    }

    public void setErrorProcess(Consumer<MessageContext> errorProcess) {
        this.errorProcess = errorProcess;
    }

    protected void write(MessageContext context) throws IOException {
        byte[] writeBytes = (byte[]) context.get(MessageContext.OUTPUT_MESSAGE_BYTES);
        if (writeBytes.length != 0) {
            OutputStream outputStream = this.socketChannel.socket().getOutputStream();
            WritableByteChannel channel = Channels.newChannel(outputStream);
            ByteBuffer writeBuffer = ByteBuffer.wrap(writeBytes);
            while (writeBuffer.remaining() != 0)
                channel.write(writeBuffer);
        }
    }
}
