package org.easyarch.xbuffer.kernel.common.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xingtianyu on 2018/10/24.
 */
public class DiskStreamInput extends StreamInput {

    private FileChannel channel;

    public DiskStreamInput(FileChannel channel) {
        this.channel = channel;
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        int read = channel.read(buffer);
        buffer.flip();
        buffer.get(bytes);
        return read;
    }

    @Override
    public int read(ByteBuffer buffer) throws IOException {
        int read = channel.read(buffer);
        buffer.flip();
        return read;
    }

    @Override
    public long position() throws IOException {
        return this.channel.position();
    }

    @Override
    public void position(long position) throws IOException {
        this.channel.position(position);
    }

    @Override
    public long size() throws IOException {
        return this.channel.size();
    }
}
