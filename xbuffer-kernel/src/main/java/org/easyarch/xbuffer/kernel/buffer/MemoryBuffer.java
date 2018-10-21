package org.easyarch.xbuffer.kernel.buffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xingtianyu on 2018/10/21.
 */
public class MemoryBuffer extends AbstractBuffer {

    private List<Event> buffer = Collections.synchronizedList(new ArrayList<Event>());

    public void push(Event block) {
        buffer.add(block);
    }

    public void batch(List<Event> blocks) {
        buffer.addAll(blocks);
    }

    public Event pop() {
        return buffer.remove(0);
    }

    public List<Event> drain(int batchSize) {
        List<Event> drainBlocks = buffer.subList(0,batchSize - 1);
        Iterator<Event> iterator = buffer.iterator();
        int index = 0;
        while (iterator.hasNext()){
            iterator.remove();
            if (index == batchSize - 1){
                break;
            }
        }
        return drainBlocks;
    }

    public State state() {
        return null;
    }
}
