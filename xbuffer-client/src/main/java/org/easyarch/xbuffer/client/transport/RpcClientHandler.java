package org.easyarch.xbuffer.client.transport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.easyarch.xbuffer.client.transport.serializer.RpcEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xingtianyu on 2018/10/30.
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcEntity> {
    private static final Logger logger = LoggerFactory.getLogger(RpcClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcEntity msg) throws Exception {
        logger.info("server return:{}",msg);
    }
}
