package org.easyarch.xbuffer.client.transport.serializer;

import com.caucho.hessian.io.JavaSerializer;

/**
 * Created by xingtianyu on 2018/10/30.
 */
public class SerializerFactory {

    public static Serializer<RpcEntity> getSerializer(SerializeType type){
        switch (type){
            case JAVA:
                return new CustomSerializer();
            case JSON:
                return null;

        }
        return null;
    }

}
