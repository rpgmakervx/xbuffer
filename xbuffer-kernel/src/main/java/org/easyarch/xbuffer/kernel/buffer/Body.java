package org.easyarch.xbuffer.kernel.buffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xingtianyu on 2018/10/21.
 * body子集不存储数据长度，由header存储
 */
public class Body extends Block {

    public Body() {
    }

    public Body(FileChannel channel, int length) throws IOException {
        this.length = length;
        readFrom(channel);
    }

    private void readFrom(FileChannel channel) throws IOException {
        //读取body
        ByteBuffer bodybuffer = ByteBuffer.allocate(this.length);
        channel.read(bodybuffer);
        //解析body
        this.put(bodybuffer);
    }

}
