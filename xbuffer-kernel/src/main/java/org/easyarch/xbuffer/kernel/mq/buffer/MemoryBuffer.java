package org.easyarch.xbuffer.kernel.mq.buffer;

import org.easyarch.xbuffer.kernel.log.XLog;

import java.io.IOException;
import java.util.List;

/**
 * @author xingtianyu(code4j) Created on 2018-10-23.
 */
public class MemoryBuffer extends AbstractClosableBuffer {

    private XLog xLog;

    public MemoryBuffer(String dataDir) {
        xLog = new XLog(dataDir);
    }

    @Override
    public void push(Event event) throws IOException {
        xLog.writeLog(event);
    }

    @Override
    public void batch(List<Event> events) throws IOException {
        if (events != null && !events.isEmpty()){
            for (Event e:events){
                push(e);
            }
        }
    }

    @Override
    public Event pop() throws IOException {
        return xLog.readLog();
    }

    @Override
    public List<Event> drain(int batchSize) {
        return null;
    }

    @Override
    public State state() throws IOException {
        return xLog.readState();
    }

    @Override
    public void closeRead() throws IOException {
        xLog.closeReadLog();
    }

    @Override
    public void closeWrite() throws IOException {
        xLog.closeWriteLog();
    }

    @Override
    public void close() throws IOException {
        xLog.close();
    }
}
