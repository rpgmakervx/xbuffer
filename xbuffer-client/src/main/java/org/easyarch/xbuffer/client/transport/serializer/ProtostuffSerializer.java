package org.easyarch.xbuffer.client.transport.serializer;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeEnv;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xingtianyu on 2018/10/30.
 */
public class ProtostuffSerializer implements Serializer<RpcEntity> {
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();
    public static Map<Class<?>, Set<String>> excludedFields = new ConcurrentHashMap<Class<?>, Set<String>>();
    public static Map<Class<?>, Set<String>> includedFields = new ConcurrentHashMap<Class<?>, Set<String>>();

    private Schema<RpcEntity> getSchema(Class<RpcEntity> cls, Set<String> exclutions) {
        Schema<RpcEntity> schema = (Schema<RpcEntity>) cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls, exclutions, RuntimeEnv.ID_STRATEGY);
            if (schema != null) {
                cachedSchema.put(cls, schema);
            }
        }
        return schema;
    }

    @Override
    public byte[] serialize(RpcEntity object) {
        if (object == null){
            return null;
        }
        Class<RpcEntity> clazz = (Class<RpcEntity>) object.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        Schema<RpcEntity> schema = getSchema(clazz, excludedFields.get(clazz));
        byte[] bytes = ProtobufIOUtil.toByteArray(object, schema, buffer);
        return bytes;
    }

    @Override
    public RpcEntity deserialize(byte[] bytes, Class<RpcEntity> clazz) {
        if (bytes == null){
            return null;
        }
        try {
            RpcEntity bean = clazz.newInstance();
            Schema<RpcEntity> schema = getSchema(clazz, excludedFields.get(clazz));
            ProtobufIOUtil.mergeFrom(bytes, bean, schema);
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
