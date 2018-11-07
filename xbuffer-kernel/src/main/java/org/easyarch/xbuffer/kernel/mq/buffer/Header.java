package org.easyarch.xbuffer.kernel.mq.buffer;

import org.easyarch.xbuffer.kernel.common.IdGenerator;
import org.easyarch.xbuffer.kernel.common.io.DiskStreamInput;
import org.easyarch.xbuffer.kernel.common.io.StreamInput;
import org.easyarch.xbuffer.kernel.common.io.StreamOutput;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xingtianyu on 2018/10/21.
 * |length(4byte)|id(8byte)|timestamp(8byte)|bodyLength(4byte)|
 */
public class Header extends Block {

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
    private int bodyLength;

    private IdGenerator generator = new IdGenerator(1,System.currentTimeMillis()%(2 << 10) - 1);

    public Header(){}

    public Header(FileChannel channel) throws Exception {
        readFrom(new DiskStreamInput(channel));
    }

    public Header(long timestamp) {
        this.id = generator.nextId();
        this.timestamp = timestamp;
        //length + id + timestamp + bodyLength
        this.length = 4 + 8 + 8 + 4;

    }

//    private void readFrom(FileChannel channel) throws Exception{
//        if (channel.position() == channel.size()){
//            return ;
//        }
//        //读取header长度
//        ByteBuffer lenBuffer = ByteBuffer.allocate(4);
//        lenBuffer.clear();
//        channel.read(lenBuffer);
//        lenBuffer.flip();
//        this.length = lenBuffer.getInt();
//        //读取剩下的header
//        ByteBuffer headerBuffer = ByteBuffer.allocate(this.length - 4);
//        headerBuffer.clear();
//        channel.read(headerBuffer);
//
//        //解析header
//        headerBuffer.flip();
//        long id = headerBuffer.getLong();
//        long timestamp = headerBuffer.getLong();
//        int bodyLength = headerBuffer.getInt();
//        this.id = id;
//        this.timestamp = timestamp;
//        this.bodyLength = bodyLength;
//    }

    public void fill(int bodyLength){
        this.bodyLength = bodyLength;
        ByteBuffer buffer = ByteBuffer.allocate(this.length + bodyLength);
        buffer.putInt(this.length);
        buffer.putLong(id);
        buffer.putLong(timestamp);
        buffer.putInt(bodyLength);
        buffer.flip();
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

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    @Override
    public String toString() {
        return "[ " +
                " length=" + this.length +
                " id=" + id +
                " timestamp=" + timestamp +
                " bodyLength=" + bodyLength +
                " ]";
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        if (in.position() == in.size()){
            return ;
        }
        //读取header长度
        ByteBuffer lenBuffer = ByteBuffer.allocate(4);
        in.read(lenBuffer);
        this.length = lenBuffer.getInt();
        //读取剩下的header
        ByteBuffer headerBuffer = ByteBuffer.allocate(this.length - 4);
        in.read(headerBuffer);

        //解析header
        long id = headerBuffer.getLong();
        long timestamp = headerBuffer.getLong();
        int offset = headerBuffer.getInt();
        this.id = id;
        this.timestamp = timestamp;
        this.bodyLength = offset;
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        throw new UnsupportedOperationException("header can not directly written to stream");
    }

    @Override
    public boolean isEmpty() {
        return this.length == 0;
    }
}
