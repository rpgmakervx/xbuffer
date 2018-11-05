package org.easyarch.xbuffer.kernel.common.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xingtianyu on 2018/10/24.
 */
public class DiskStreamOutput extends StreamOutput {

    private FileChannel writeChannel;

    public DiskStreamOutput(FileChannel writeChannel) {
        this.writeChannel = writeChannel;
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        this.writeChannel.write(buffer);
        this.writeChannel.force(true);
    }

    @Override
    public void write(ByteBuffer buffer) throws IOException {
        this.writeChannel.write(buffer);
        this.writeChannel.force(true);
    }

    @Override
    public void write(ByteBuffer buffer, long position) throws IOException {
        this.writeChannel.write(buffer, position);
        this.writeChannel.force(true);
    }

    @Override
    public long position() throws IOException {
        return this.writeChannel.position();
    }
}
