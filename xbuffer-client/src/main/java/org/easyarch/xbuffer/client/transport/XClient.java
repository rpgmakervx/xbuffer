package org.easyarch.xbuffer.client.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLogLevel;
import org.easyarch.xbuffer.client.transport.serializer.RpcEntity;

/**
 * Created by xingtianyu on 2018/10/30.
 */
public class XClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast("logging", new LoggingHandler(LogLevel.INFO));
                        socketChannel.pipeline().addLast(new RpcEntityDecoder());
                        socketChannel.pipeline().addLast(new RpcEntityEncoder());
                        socketChannel.pipeline().addLast(new RpcClientHandler());
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", 7777).sync();
            String payload = "hello world";
            RpcEntity entity = new RpcEntity((byte) 0x00,(byte) 0x01,payload.getBytes());
            future.channel().writeAndFlush(entity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
