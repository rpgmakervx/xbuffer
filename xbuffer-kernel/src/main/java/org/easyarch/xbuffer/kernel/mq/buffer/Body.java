package org.easyarch.xbuffer.kernel.mq.buffer;

import org.easyarch.xbuffer.kernel.common.io.DiskStreamInput;
import org.easyarch.xbuffer.kernel.common.io.StreamInput;
import org.easyarch.xbuffer.kernel.common.io.StreamOutput;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xingtianyu on 2018/10/21.
 * body子集不存储数据长度，由header存储
 */
public class Body extends Block{

    public Body() {
    }

    public Body(FileChannel channel, int length) throws IOException {
        this.length = length;
        readFrom(new DiskStreamInput(channel));
    }


    @Override
    public void readFrom(StreamInput in) throws IOException {
        //读取body
        ByteBuffer bodybuffer = ByteBuffer.allocate(this.length);
        in.read(bodybuffer);
        //解析body
        this.put(bodybuffer);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        throw new UnsupportedOperationException("header can not directly written to stream");
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }
}
