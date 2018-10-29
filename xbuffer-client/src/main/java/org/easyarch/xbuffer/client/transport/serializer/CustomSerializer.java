package org.easyarch.xbuffer.client.transport.serializer;


import java.nio.ByteBuffer;

/**
 * Created by xingtianyu on 2018/10/29.
 */
public class CustomSerializer implements Serializer<RpcEntity> {

    @Override
    public byte[] serialize(RpcEntity entity) {
        byte[] data = new byte[4 + 1 + 1 + entity.getLength()];
        ByteBuffer buffer = ByteBuffer.allocate(4 + 1 + 1 + entity.getLength());
        buffer.putInt(entity.getLength());
        buffer.put(entity.getSerialType());
        buffer.put(entity.getMethod());
        buffer.put(entity.getPayload());
        buffer.flip();
        buffer.get(data);
        return data;
    }

    @Override
    public RpcEntity deserialize(byte[] bytes, Class<RpcEntity> clazz) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.flip();
        int length = buffer.getInt();
        byte serType = buffer.get();
        byte method = buffer.get();
        byte[] payload = new byte[length];
        buffer.get(payload);
        return new RpcEntity(serType,method,payload);
    }
}
