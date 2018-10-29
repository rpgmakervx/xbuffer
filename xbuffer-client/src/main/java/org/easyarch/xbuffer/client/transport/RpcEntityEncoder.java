package org.easyarch.xbuffer.client.transport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.easyarch.xbuffer.client.transport.serializer.RpcEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xingtianyu on 2018/10/30.
 */
public class RpcEntityEncoder extends MessageToByteEncoder<RpcEntity> {
    private static final Logger logger = LoggerFactory.getLogger(RpcEntityEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcEntity msg, ByteBuf out) throws Exception {
        if (msg == null){
            return;
        }
        out.writeInt(msg.getLength());
        out.writeByte(msg.getSerialType());
        out.writeByte(msg.getMethod());
        out.writeBytes(msg.getPayload());
    }
}
