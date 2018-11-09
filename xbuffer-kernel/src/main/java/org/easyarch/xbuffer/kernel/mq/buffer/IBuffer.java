package org.easyarch.xbuffer.kernel.mq.buffer;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * Created by xingtianyu on 2018/10/20.
 * buffer的多种实现
 * v0.0.1实现MemoryBuffer,FileBuffer
 */
public interface IBuffer {


    public void push(Event event) throws IOException;

    public void batch(List<Event> events) throws IOException;

    public Event pop() throws IOException;

    public List<Event> drain(int batchSize);

    public State state() throws IOException;


}
