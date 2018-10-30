package org.easyarch.xbuffer.kernel.transport;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.easyarch.xbuffer.kernel.transport.netty.HttpDispatcherHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;

/**
 * @author xingtianyu(code4j) Created on 2018-10-30.
 */
public class XHttpServer {
    private static final Logger logger = LoggerFactory.getLogger(XServer.class);

    private ServerBootstrap bootstrap;
    private EventLoopGroup boss;
    private EventLoopGroup workers;

    private ThreadFactory bossThreadFactory= new ThreadFactoryBuilder()
            .setDaemon(true)
            .setNameFormat("xbuffer[http-bossThread]-%d")
            .build();
    private ThreadFactory workThreadFactory= new ThreadFactoryBuilder()
            .setDaemon(true)
            .setNameFormat("xbuffer[http-workThread]-%d")
            .build();

    public void start(int port){
        logger.info("http server listen port:{}",port);
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
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("logging", new LoggingHandler(LogLevel.INFO));
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast("compress", new HttpContentCompressor(9));
                        pipeline.addLast("aggregator", new HttpObjectAggregator(10*1024*1024));
                        pipeline.addLast("decompress", new HttpContentDecompressor());
                        pipeline.addLast(new ChunkedWriteHandler());
                        pipeline.addLast(new HttpDispatcherHandler());
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
