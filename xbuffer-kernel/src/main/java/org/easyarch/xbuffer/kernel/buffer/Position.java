package org.easyarch.xbuffer.kernel.buffer;

import java.nio.ByteBuffer;

/**
 * Created by xingtianyu on 2018/10/22.
 * 记录位置信息：文件名和position
 * |length(4byte)|content(n byte)|position(8byte)|
 */
public class Position extends Block {
    private byte[] vector;

    private long position;

    public Position(byte[] vector, long position) {
        this.vector = vector;
        this.position = position;
        ByteBuffer buffer = ByteBuffer.allocate(4+vector.length+8);
        buffer.putInt(vector.length);
        buffer.put(vector);
        buffer.putLong(position);
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
}
