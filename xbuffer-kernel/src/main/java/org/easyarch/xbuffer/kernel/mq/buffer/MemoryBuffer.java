package org.easyarch.xbuffer.kernel.mq.buffer;

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
