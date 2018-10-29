package org.easyarch.xbuffer.client.transport.serializer;

/**
 * Created by xingtianyu on 2018/10/29.
 */
public interface Serializer<T> {

    public byte[] serialize(T bean);

    public T deserialize(byte[] bytes, Class<T> clazz);

}
