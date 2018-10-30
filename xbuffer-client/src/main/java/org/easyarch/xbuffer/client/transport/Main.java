package org.easyarch.xbuffer.client.transport;

import org.easyarch.xbuffer.client.transport.serializer.RpcEntity;

/**
 * @author xingtianyu(code4j) Created on 2018-10-30.
 */
public class Main {

    public static void main(String[] args) {
        String payload = "hello world";
        RpcEntity entity = new RpcEntity((byte) 0x00,(byte) 0x01,payload.getBytes());
        XClient client = new XClient();
        client.connet("127.0.0.1",7777);
        client.send(entity);
    }
}
