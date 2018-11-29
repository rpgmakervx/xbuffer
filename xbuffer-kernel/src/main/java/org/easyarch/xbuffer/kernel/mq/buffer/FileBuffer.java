package org.easyarch.xbuffer.kernel.mq.buffer;

import org.easyarch.xbuffer.kernel.log.XLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by xingtianyu on 2018/10/21.
 */
public class FileBuffer extends AbstractClosableBuffer {

    private static final Logger logger = LoggerFactory.getLogger(FileBuffer.class);


    private XLog xLog;

    public FileBuffer(String dataDir) {
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
