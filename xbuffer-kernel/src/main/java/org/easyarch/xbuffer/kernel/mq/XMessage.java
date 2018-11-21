package org.easyarch.xbuffer.kernel.mq;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

/**
 * @author xingtianyu(code4j) Created on 2018-11-5.
 */
public class XMessage {

    private String topicId;

    private long timestamp;

    private byte[] content;

    public XMessage(String topicId,byte[] content){
        if (topicId == null){
            throw new NullPointerException("topicId can not be null");
        }
        if (content == null){
            throw new NullPointerException("content can not be null");
        }
        this.topicId  = topicId;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public void put(byte[] content){
        ByteBuffer buffer = ByteBuffer.wrap(content);
        long length = buffer.getInt();
        System.out.println("content length:"+content.length);
        System.out.println("xmessage length:"+length);
        this.timestamp = buffer.getLong();
        System.out.println("xmessage timestamp:"+timestamp);
        this.content = new byte[(int) (length - 8)];
        buffer.get(this.content);
    }

    public ByteBuffer buffer(){
        int length = 8 + content.length;
        System.out.println("put content.length:"+content.length);
        ByteBuffer buffer = ByteBuffer.allocate(4 + length);
        buffer.putInt(length);
        buffer.putLong(timestamp);
        buffer.put(content);
        buffer.flip();
        return buffer;
    }

    public byte[] content(){
        int length = 4 + 8 + content.length;
        byte[] data = new byte[length];
        ByteBuffer buffer = buffer();
        buffer.get(data);
        return data;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getContent() {

        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
