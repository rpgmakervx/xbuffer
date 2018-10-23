package org.easyarch.xbuffer.kernel.buffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xingtianyu on 2018/10/21.
 * |length(4byte)|id(8byte)|timestamp(8byte)|offset(4byte)|
 */
public class Header extends Block {

    private static final Logger logger = LoggerFactory.getLogger(Header.class);

    /**
     * 消息的唯一id
     */
    private long id;
    /**
     * 消息产生的时间戳
     */
    private long timestamp;
    /**
     * body长度，即org.easyarch.xbuffer.kernel.buffer.Body的length属性
     */
    private int offset;

    public Header(FileChannel channel) throws Exception {
        readFrom(channel);
    }

    public Header(long id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
        //length + id + timestamp + offset
        this.length = 4 + 8 + 8 + 4;

    }

    private void readFrom(FileChannel channel) throws Exception{
        if (channel.position() == channel.size()){
            return ;
        }
        //读取header长度
        ByteBuffer lenBuffer = ByteBuffer.allocate(4);
        lenBuffer.clear();
        channel.read(lenBuffer);
        lenBuffer.flip();
        this.length = lenBuffer.getInt();
        //读取剩下的header
        ByteBuffer headerBuffer = ByteBuffer.allocate(this.length - 4);
        headerBuffer.clear();
        channel.read(headerBuffer);

        //解析header
        headerBuffer.flip();
        long id = headerBuffer.getLong();
        long timestamp = headerBuffer.getLong();
        int offset = headerBuffer.getInt();
        logger.info("header length:{},offset:{}",this.length,offset);
        this.id = id;
        this.timestamp = timestamp;
        this.offset = offset;
    }

    public void fill(int bodyLength){
        this.offset = bodyLength;
        ByteBuffer buffer = ByteBuffer.allocate(this.length + bodyLength);
        buffer.putInt(this.length);
        buffer.putLong(id);
        buffer.putLong(timestamp);
        buffer.putInt(bodyLength);
        //将buffer读入header中
        put(buffer);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "[ " +
                "id=" + id +
                " timestamp=" + timestamp +
                " offset=" + offset +
                " ]";
    }
}
