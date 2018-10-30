package org.easyarch.xbuffer.client.transport.serializer;

import java.util.Arrays;

/**
 * Created by xingtianyu on 2018/10/29.
 * 实体长度：4 + 1 + 1 + length
 */
public class RpcEntity {

    private int length;
    /**
     * 序列化方式
     */
    private byte serialType;
    /**
     * rpc指令
     */
    private byte method;
    /**
     * 负载数据
     */
    private byte[] payload;

    public RpcEntity(byte serialType, byte method, byte[] payload) {
        this.serialType = serialType;
        this.method = method;
        this.payload = payload;
        this.length = payload.length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getMethod() {
        return method;
    }

    public void setMethod(byte method) {
        this.method = method;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public byte getSerialType() {
        return serialType;
    }

    public void setSerialType(byte serialType) {
        this.serialType = serialType;
    }

    @Override
    public String toString() {
        return "RpcEntity{" +
                "length=" + length +
                ", serialType=" + serialType +
                ", method=" + method +
                ", payload=" + Arrays.toString(payload) +
                '}';
    }
}
