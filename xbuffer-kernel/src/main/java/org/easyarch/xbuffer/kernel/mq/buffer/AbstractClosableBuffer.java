package org.easyarch.xbuffer.kernel.mq.buffer;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by xingtianyu on 2018/11/10.
 */
abstract public class AbstractClosableBuffer implements IBuffer, Closeable {

    abstract public void closeRead() throws IOException;

    abstract public void closeWrite() throws IOException;
}
