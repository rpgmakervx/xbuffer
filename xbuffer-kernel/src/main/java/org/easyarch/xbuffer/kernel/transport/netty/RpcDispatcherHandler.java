package org.easyarch.xbuffer.kernel.transport.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import org.easyarch.xbuffer.client.transport.serializer.RpcEntity;
import org.easyarch.xbuffer.kernel.buffer.FileBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xingtianyu on 2018/10/29.
 */
public class RpcDispatcherHandler extends SimpleChannelInboundHandler<RpcEntity> {

    private static final Logger logger = LoggerFactory.getLogger(RpcDispatcherHandler.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcEntity msg) throws Exception {
        logger.info("I got a rpc entity:{}",msg);
        RpcEntity response = new RpcEntity((byte) 0x10,(byte) 0x00,"I got your message".getBytes());
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("rpc handler error",cause);
    }
}
