package org.easyarch.xbuffer.kernel.buffer;

import java.nio.ByteBuffer;

/**
 * Created by xingtianyu on 2018/10/21.
 * |length(4byte)|id(8byte)|timestamp(8byte)|offset(4byte)|
 */
public class Header extends Block {

    private long id;

    private long timestamp;
    //body长度
    private int offset;

    public Header(long id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
        //length + id + timestamp + offset
        this.length = 4 + 8 + 8 + 4;

    }

    public void fill(int bodyLength){
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
}
