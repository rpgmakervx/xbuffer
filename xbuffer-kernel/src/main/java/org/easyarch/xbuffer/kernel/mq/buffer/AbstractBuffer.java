package org.easyarch.xbuffer.kernel.mq.buffer;

import java.io.IOException;
import java.util.List;

/**
 * Created by xingtianyu on 2018/10/20.
 * buffer的多种实现
 * v0.0.1实现MemoryBuffer,FileBuffer
 */
public abstract class AbstractBuffer {


    abstract public void push(Event event) throws IOException;

    abstract public void batch(List<Event> events) throws IOException;

    abstract public Event pop() throws IOException;

    abstract public List<Event> drain(int batchSize);

    abstract public State state() throws IOException;

}
