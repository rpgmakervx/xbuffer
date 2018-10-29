package org.easyarch.xbuffer.client.transport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.easyarch.xbuffer.client.transport.serializer.RpcEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by xingtianyu on 2018/10/30.
 */
public class RpcEntityDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(RpcEntityDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 6){
            return;
        }
        int length = byteBuf.readInt();
        byte serType = byteBuf.readByte();
        byte method = byteBuf.readByte();
        byte[] payload = new byte[length];
        byteBuf.readBytes(payload);
        RpcEntity entity = new RpcEntity(serType,method,payload);
        list.add(entity);
    }
}
