package org.easyarch.xbuffer.kernel.buffer;

import org.easyarch.xbuffer.kernel.buffer.entity.Event;
import org.easyarch.xbuffer.kernel.buffer.entity.State;

import java.util.List;

/**
 * @author xingtianyu(code4j) Created on 2018-10-23.
 */
public class MemoryBuffer extends AbstractBuffer {
    @Override
    public void push(Event event) {

    }

    @Override
    public void batch(List<Event> events) {

    }

    @Override
    public Event pop() {
        return null;
    }

    @Override
    public List<Event> drain(int batchSize) {
        return null;
    }

    @Override
    public State state() {
        return null;
    }
}
