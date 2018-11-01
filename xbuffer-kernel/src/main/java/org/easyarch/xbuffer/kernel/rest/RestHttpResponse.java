package org.easyarch.xbuffer.kernel.rest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.*;

/**
 * Created by xingtianyu on 2018/11/1.
 */
public class RestHttpResponse {

    private Channel channel;

    public RestHttpResponse(Channel channel) {
        this.channel = channel;
    }

    public void writeJson(String json){
        write(json.getBytes());
    }

    public void writeJson(String json,int status){
        write(json.getBytes(),status);
    }

    private void write(byte[] content){
        ByteBuf buf = Unpooled.wrappedBuffer(content);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,buf);
        response.headers().add(HttpHeaderNames.CONTENT_TYPE,"application/json");
        channel.writeAndFlush(response);
    }

    private void write(byte[] content,int status){
        HttpResponseStatus statusCode = HttpResponseStatus.valueOf(status);
        ByteBuf buf = Unpooled.wrappedBuffer(content);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, statusCode,buf);
        response.headers().add(HttpHeaderNames.CONTENT_TYPE,"application/json");
        channel.writeAndFlush(response);
    }

}
