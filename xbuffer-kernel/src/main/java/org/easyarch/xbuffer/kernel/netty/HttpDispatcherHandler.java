package org.easyarch.xbuffer.kernel.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.easyarch.xbuffer.kernel.ClusterState;
import org.easyarch.xbuffer.kernel.rest.AbstractRestController;
import org.easyarch.xbuffer.kernel.rest.RestHttpRequest;
import org.easyarch.xbuffer.kernel.rest.RestHttpResponse;
import org.easyarch.xbuffer.kernel.rest.RestMethod;
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
        RestHttpRequest req = new RestHttpRequest(request);
        RestHttpResponse resp = new RestHttpResponse(ctx.channel());
        AbstractRestController controller = table.getController(req);
        if (controller == null){
            logger.info("null url:{}",request.uri());
        }
        controller.doAction(req,resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
