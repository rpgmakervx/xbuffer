package org.easyarch.xbuffer.kernel.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.easyarch.xbuffer.kernel.ClusterState;
import org.easyarch.xbuffer.kernel.rest.AbstractRestController;
import org.easyarch.xbuffer.kernel.rest.XHttpRequest;
import org.easyarch.xbuffer.kernel.rest.XHttpResponse;
import org.easyarch.xbuffer.kernel.rest.router.RestRouteTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xingtianyu(code4j) Created on 2018-10-30.
 */
public class HttpDispatcherHandler extends ChannelInboundHandlerAdapter{
    private static final Logger logger = LoggerFactory.getLogger(HttpDispatcherHandler.class);

    private RestRouteTable table = ClusterState.restRouteTable();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
        String url = request.uri();
        AbstractRestController controller = table.getController(url);
        XHttpRequest req = new XHttpRequest(request);
        XHttpResponse resp = new XHttpResponse(ctx.channel());
        controller.doAction(req,resp);
//        HttpMethod method = request.method();
//        byte[] data = ByteBufUtil.getBytes(request.content());
//        logger.info("request uri:{}, method:{}, data:{}",request.uri(),method,new String(data));
//        ByteBuf buf = Unpooled.wrappedBuffer("{'message':'success'}".getBytes());
//        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,buf);
//        response.headers().add(HttpHeaderNames.CONTENT_TYPE,"application/json");
//        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
