package org.easyarch.xbuffer.kernel.mq;

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
    }

    public byte[] toBytes(){
        int length = 4 + 8 + content.length;
        byte[] data = new byte[length];
        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.putInt(length);
        buffer.putLong(timestamp);
        buffer.put(content);
        buffer.flip();
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
