package org.easyarch.xbuffer.kernel.common.io;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by xingtianyu on 2018/10/24.
 */
public abstract class StreamInput {

    /**
     * 读取字节数组到输入流
     * @param bytes
     * @return
     * @throws IOException
     */
    public abstract int read(byte[] bytes) throws IOException;

    /**
     * 读取数据到输入流后，buffer立即进入可读状态
     * @param buffer
     * @throws IOException
     */
    public abstract int read(ByteBuffer buffer) throws IOException;

    /**
     * 获取当前读取位置
     * @return
     * @throws IOException
     */
    public abstract long position() throws IOException;

    /**
     * 读取指针复位
     * @param position
     * @return
     * @throws IOException
     */
    public abstract void position(long position) throws IOException;

    /**
     * 流的有效长度
     * @return
     * @throws IOException
     */
    public abstract long size() throws IOException;
}
