package org.easyarch.xbuffer.kernel.netty;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.easyarch.xbuffer.client.transport.RpcEntityDecoder;
import org.easyarch.xbuffer.client.transport.RpcEntityEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;

/**
 * Created by xingtianyu on 2018/10/28.
 */
public class XServer {

    private static final Logger logger = LoggerFactory.getLogger(XServer.class);

    private ServerBootstrap bootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup workers;

    private ThreadFactory bossThreadFactory= new ThreadFactoryBuilder()
            .setDaemon(true)
            .setNameFormat("xbuffer[tcp-bossThread]-%d")
            .build();
    private ThreadFactory workThreadFactory= new ThreadFactoryBuilder()
            .setDaemon(true)
            .setNameFormat("xbuffer[tcp-workThread]-%d")
            .build();

    public void start(int port){
        logger.info("server listen port:{}",port);
        boss = new NioEventLoopGroup(1, bossThreadFactory);
        workers = new NioEventLoopGroup(4, workThreadFactory);
        bootstrap = new ServerBootstrap();
        bootstrap.group(boss, workers).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast("logging", new LoggingHandler(LogLevel.INFO));
                        socketChannel.pipeline().addLast(new RpcEntityDecoder());
                        socketChannel.pipeline().addLast(new RpcEntityEncoder());
                        socketChannel.pipeline().addLast(new RpcDispatcherHandler());
                    }
                });
        try {
            ChannelFuture sync = this.bootstrap.bind(port).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void shutdown() {
        try {
            boss.shutdownGracefully();
            workers.shutdownGracefully();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
