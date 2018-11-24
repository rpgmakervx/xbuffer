package org.easyarch.xbuffer.kernel.mq;

import org.easyarch.xbuffer.kernel.XConfig;
import org.easyarch.xbuffer.kernel.mq.buffer.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xingtianyu(code4j) Created on 2018-11-5.
 */
public class BufferOperator {

    private static ConcurrentHashMap<String,BufferOperator> operators = new ConcurrentHashMap<>();

    private AbstractClosableBuffer buffer;

    public BufferOperator(AbstractClosableBuffer buffer){
        this.buffer = buffer;
    }

    public void produce(XMessage message) throws IOException {
        ByteBuffer buffer = message.buffer();
//        long timestamp = message.getTimestamp();
//        byte[] content = message.getContent();
//        int length = 8 + content.length;
//        ByteBuffer buffer = ByteBuffer.allocate(4 + length);
//        System.out.println("put content.length:"+content.length);
//        buffer.putInt(length);
//        buffer.putLong(timestamp);
//        buffer.put(content);
//        buffer.flip();
        Header header = new Header(System.currentTimeMillis());
        Body body = new Body();
        body.put(buffer);
        Event event = new Event(header,body);
        this.buffer.push(event);
    }

    public XMessage consume(String topicId,String clientId) throws IOException {
        Event event = this.buffer.pop();
        if (event == null||event.isEmpty()){
            return null;
        }
        byte[] content = event.body().content();
        XMessage message = new XMessage(topicId,content);
        message.put(content);
        return message;
    }

    public static BufferOperator getOperator(String topicId){
        BufferOperator operator = operators.get(topicId);
        if (operator == null){
            operator = new BufferOperator(new MemoryBuffer(XConfig.dataDir() + File.separator + topicId));
            BufferOperator.addOperator(topicId,operator);
        }
        return operator;
    }

    public static void addOperator(String topicId,BufferOperator operator){
        operators.put(topicId,operator);
    }

    public void closeBuffer(){
        try {
            this.buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
