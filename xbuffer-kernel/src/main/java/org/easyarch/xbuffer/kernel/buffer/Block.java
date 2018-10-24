package org.easyarch.xbuffer.kernel.buffer;

import org.easyarch.xbuffer.kernel.Boostrap;
import org.easyarch.xbuffer.kernel.common.Streamable;
import org.easyarch.xbuffer.kernel.common.io.StreamInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Created by xingtianyu on 2018/10/20.
 * block是数据的最小单位，例如header,body
 */
public abstract class Block implements Streamable{
    private static final Logger logger = LoggerFactory.getLogger(Block.class);

    private ByteBuffer content;
    //当前数据长度
    protected volatile int length;

    public void put(byte[] data) {
        this.length = data.length;
        this.content = ByteBuffer.wrap(data);
        this.content.put(data);
    }

    /**
     * buffer此时已经是可以直接读写的
     * @param buffer
     */
    public void put(ByteBuffer buffer) {
        this.length = buffer.limit();
        this.content = ByteBuffer.allocate(buffer.limit());
        this.content.put(buffer);
    }

    /**
     * 每次读取前先flip,确保可以多次读取
     * @return
     */
    public byte[] content() {
        int length = content.position();
        byte[] data = new byte[length];
        this.content.flip();
        this.content.get(data);
        return data;
    }

    public ByteBuffer buffer(){
        this.content.flip();
        return this.content;
    }

    public abstract boolean isEmpty();

    public int length(){
        return this.length;
    }
}
