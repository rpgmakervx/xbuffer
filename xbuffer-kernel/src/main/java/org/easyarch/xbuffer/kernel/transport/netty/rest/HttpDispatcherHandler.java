package org.easyarch.xbuffer.kernel.transport.netty.rest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.easyarch.xbuffer.kernel.transport.XServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xingtianyu(code4j) Created on 2018-10-30.
 */
public class HttpDispatcherHandler extends ChannelInboundHandlerAdapter{
    private static final Logger logger = LoggerFactory.getLogger(HttpDispatcherHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("read http message");
        FullHttpRequest request = (FullHttpRequest) msg;
        HttpMethod method = request.method();
        byte[] data = ByteBufUtil.getBytes(request.content());
        logger.info("request method:{}, data:{}",method,new String(data));
        ByteBuf buf = Unpooled.wrappedBuffer("{'message':'success'}".getBytes());
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,buf);
        response.headers().add(HttpHeaderNames.CONTENT_TYPE,"application/json");
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
