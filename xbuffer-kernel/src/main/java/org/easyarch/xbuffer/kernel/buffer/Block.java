package org.easyarch.xbuffer.kernel.buffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Created by xingtianyu on 2018/10/20.
 * block是数据的最小单位，例如header,body
 */
public class Block {
    private static final Logger logger = LoggerFactory.getLogger(Block.class);
    private ByteBuffer content;
    //当前数据长度
    protected volatile int length;


    public void put(byte[] data) {
        this.content = ByteBuffer.wrap(data);
        this.content.put(data);
        this.length = data.length;
    }

    /**
     * data 不需要flip
     * @param data
     */
    public void put(ByteBuffer data) {
        this.length = data.position();
        this.content = ByteBuffer.allocate(data.position());
        data.flip();
        this.content.put(data);
    }

    /**
     * 每次读取前先flip
     * @return
     */
    public byte[] content() {
        int length = content.position();
        byte[] data = new byte[length];
        this.content.flip();
        this.content.get(data);
//        logger.info("content data length:{}",data.length);
        return data;
    }

    protected ByteBuffer buffer(){
        this.content.flip();
        return this.content;
    }
}
