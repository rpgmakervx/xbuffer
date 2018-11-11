package org.easyarch.xbuffer.kernel.mq.buffer;

import org.easyarch.xbuffer.kernel.common.Streamable;
import org.easyarch.xbuffer.kernel.common.io.StreamInput;
import org.easyarch.xbuffer.kernel.common.io.StreamOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by xingtianyu on 2018/11/11.
 */
public class Offset implements Streamable,Entity<Offset> {
    private static final Logger logger = LoggerFactory.getLogger(FileBuffer.class);
    /**
     * 写入的当前位置，有没有用先记下来。。
     */
    private long offset;

    public Offset(long offset) {
        this.offset = offset;
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        ByteBuffer offsetBuffer = ByteBuffer.allocate(8);
        in.read(offsetBuffer);
        this.offset = offsetBuffer.getLong();
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        out.write(content());
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ByteBuffer content() {
        ByteBuffer offsetBuffer = ByteBuffer.allocate(8);
        offsetBuffer.putLong(this.offset);
        offsetBuffer.flip();
        return offsetBuffer;
    }

    @Override
    public int length() {
        return 8;
    }

    @Override
    public Offset entity() {
        return this;
    }
}
