package org.easyarch.xbuffer.kernel.buffer;

import org.easyarch.xbuffer.kernel.buffer.entity.Event;
import org.easyarch.xbuffer.kernel.buffer.entity.State;

import java.util.List;

/**
 * Created by xingtianyu on 2018/10/20.
 * buffer的多种实现
 * v0.0.1实现MemoryBuffer,FileBuffer
 */
public abstract class AbstractBuffer {


    abstract public void push(Event event);

    abstract public void batch(List<Event> events);

    abstract public Event pop();

    abstract public List<Event> drain(int batchSize);

    abstract public State state();

}
