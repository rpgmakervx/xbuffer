package org.easyarch.xbuffer.client.transport.serializer;

/**
 * Created by xingtianyu on 2018/10/29.
 */
public enum SerializeType {
    JAVA((byte)0x00),JSON((byte)0x01),HESSIAN((byte)0x02),PROTOBUF((byte)0x03);
    private byte type;

    SerializeType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }
}
