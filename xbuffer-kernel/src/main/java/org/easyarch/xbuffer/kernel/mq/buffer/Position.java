package org.easyarch.xbuffer.kernel.mq.buffer;

import org.easyarch.xbuffer.kernel.common.io.StreamInput;
import org.easyarch.xbuffer.kernel.common.io.StreamOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by xingtianyu on 2018/10/22.
 * 记录位置信息：文件名和position
 * |length(4byte)|content(n byte)|position(8byte)|
 */
public class Position extends Block {
    private static final Logger logger = LoggerFactory.getLogger(FileBuffer.class);
    /**
     * 读取的文件名
     */
    private byte[] vector;
    /**
     * 当前读取的位置
     */
    private long position;

    public Position() {
    }

    public Position(byte[] vector, long position) {
        this.vector = vector;
        this.position = position;
        ByteBuffer buffer = ByteBuffer.allocate(4+vector.length+8);
        buffer.putInt(vector.length);
        buffer.put(vector);
        buffer.putLong(position);
        buffer.flip();
        put(buffer);
    }

    public byte[] getVector() {
        return vector;
    }

    public void setVector(byte[] vector) {
        this.vector = vector;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Position{" +
                "vector='" + new String(vector) + '\'' +
                ", position=" + position +
                '}';
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        //先读取文件名长度
        in.read(lengthBuffer);
        int length = lengthBuffer.getInt();

        //读取文件名
        ByteBuffer fileNameBuffer = ByteBuffer.allocate(length);
        in.read(fileNameBuffer);
        this.vector = new byte[length];
        fileNameBuffer.get(this.vector);

        //读取位置信息
        ByteBuffer posBuffer = ByteBuffer.allocate(8);
        in.read(posBuffer);
        this.position = posBuffer.getLong();
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        out.write(buffer());
    }

    @Override
    public boolean isEmpty() {
        return vector == null||vector.length == 0;
    }
}
