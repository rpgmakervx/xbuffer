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
    protected ByteBuffer content;

    protected volatile long length;

    protected volatile long position;

    public void fill(byte[] data) {
        this.content = ByteBuffer.wrap(data);
        this.content.put(data);
    }

    public byte[] content() {
        int length = content.position();
        byte[] data = new byte[length];
        this.content.flip();
        this.content.get(data);
        return data;
    }

    protected ByteBuffer contentBuffer(){
        this.content.flip();
        return this.content;
    }

    protected long position(){
        return this.position;
    }

    protected long length(){
        return this.length;
    }
}
