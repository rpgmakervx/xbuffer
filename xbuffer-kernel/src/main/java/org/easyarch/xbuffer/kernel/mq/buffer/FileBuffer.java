package org.easyarch.xbuffer.kernel.mq.buffer;

import org.easyarch.xbuffer.kernel.XConfig;
import org.easyarch.xbuffer.kernel.common.io.DiskStreamInput;
import org.easyarch.xbuffer.kernel.common.io.DiskStreamOutput;
import org.easyarch.xbuffer.kernel.env.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by xingtianyu on 2018/10/21.
 */
public class FileBuffer extends AbstractBuffer {

    private static final Logger logger = LoggerFactory.getLogger(FileBuffer.class);

    /**
     * 当前读取的位置
     */
    private long position;

    /**
     * 当前写入的位置
     */
    private long offset;

    /**
     * 读数据channel
     */
    private FileChannel readChannel;

    /**
     * 写数据channel
     */
    private FileChannel writeChannel;

    /**
     * 写statechannel
     */
    private FileChannel stWriteChannel;
    /**
     * 写读statechannel
     */
    private FileChannel stReadChannel;

    private static FileBuffer instance;

    public FileBuffer(String dir){
        try {
            initWrite(dir);
            initRead(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initRead(String dir) throws Exception {
        File file = new File(dir+File.separator+String.format(XConfig.DATA_FILE_NAME,2));
        if (!file.exists()){
            file.createNewFile();
        }
        FileInputStream fis = new FileInputStream(file);
        this.readChannel = fis.getChannel();
        File stFile = new File(dir+File.separator+XConfig.STATE_FILE_NAME);
        boolean init = false;
        if (!stFile.exists()){
            stFile.createNewFile();
            init = true;
        }
        FileOutputStream stFos = new FileOutputStream(stFile,true);
        FileInputStream stFis = new FileInputStream(stFile);
        this.stWriteChannel = stFos.getChannel();
        this.stReadChannel = stFis.getChannel();
        if (init){
            State state = new State(new Position(String.format(XConfig.dataPrefix,2).getBytes(),0));
            state.writeTo(new DiskStreamOutput(this.stWriteChannel));
            this.position = 0;
        }else {
            State state = state();
            this.position = state.position().getPosition();
            this.readChannel.position(this.position);
        }
    }

    private void initWrite(String dir) throws Exception {
        File file = new File(dir+File.separator+String.format(XConfig.DATA_FILE_NAME,2));
        if (!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file,true);
        this.writeChannel = fos.getChannel();

    }
    @Override
    public void push(Event event) throws IOException {
        this.offset = event.length();
        event.writeTo(new DiskStreamOutput(this.writeChannel));

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
        Event event = new Event();
        event.readFrom(new DiskStreamInput(this.readChannel));
        //写状态，记录当前读取位置
        this.position = readChannel.position();
        State state = new State(new Position(String.format(XConfig.dataPrefix,2).getBytes(),position));
        state.writeTo(new DiskStreamOutput(this.stWriteChannel));
        return event.entity();
    }


    @Override
    public List<Event> drain(int batchSize) {
        return null;
    }

    @Override
    public synchronized State state() throws IOException {
        State state = new State();
        state.readFrom(new DiskStreamInput(this.stReadChannel));
        return state.entity();
    }


}
