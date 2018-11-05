package org.easyarch.xbuffer.kernel.mq;

import org.easyarch.xbuffer.kernel.mq.buffer.AbstractBuffer;

/**
 * @author xingtianyu(code4j) Created on 2018-11-5.
 */
public class BufferOperator {

    private AbstractBuffer buffer;

    public BufferOperator(AbstractBuffer buffer){
        this.buffer = buffer;
    }

    public void produce(XMessage message){

    }

    public XMessage consume(String topic,String clientId){
        return null;
    }
}
