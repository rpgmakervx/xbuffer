package org.easyarch.xbuffer.kernel.mq;

import org.easyarch.xbuffer.kernel.mq.buffer.AbstractBuffer;
import org.easyarch.xbuffer.kernel.mq.buffer.Body;
import org.easyarch.xbuffer.kernel.mq.buffer.Event;
import org.easyarch.xbuffer.kernel.mq.buffer.Header;

import java.nio.ByteBuffer;

/**
 * @author xingtianyu(code4j) Created on 2018-11-5.
 */
public class BufferOperator {

    private AbstractBuffer buffer;

    public BufferOperator(AbstractBuffer buffer){
        this.buffer = buffer;
    }

    public void produce(XMessage message){
        String topicId = message.getTopicId();
        long timestamp = message.getTimestamp();
        byte[] content = message.getContent();
        int length = topicId.getBytes().length + 8 + content.length;
        ByteBuffer buffer = ByteBuffer.allocate(4 + length);
        buffer.putInt(length);
        buffer.put(topicId.getBytes());
        buffer.putLong(timestamp);
        buffer.put(content);
        buffer.flip();
        Header header = new Header(System.currentTimeMillis());
        Body body = new Body();
        body.put(buffer);
        Event event = new Event(header,body);
        this.buffer.push(event);
    }

    public XMessage consume(String topic,String clientId){
        return null;
    }
}
