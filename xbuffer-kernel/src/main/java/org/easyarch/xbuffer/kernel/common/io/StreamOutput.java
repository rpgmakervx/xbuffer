package org.easyarch.xbuffer.kernel.common.io;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by xingtianyu on 2018/10/24.
 */
public abstract class StreamOutput {
    public abstract void write(byte[] bytes) throws IOException;

    public abstract void write(ByteBuffer buffer) throws IOException;

    public abstract void write(ByteBuffer buffer, long position) throws IOException;
}
