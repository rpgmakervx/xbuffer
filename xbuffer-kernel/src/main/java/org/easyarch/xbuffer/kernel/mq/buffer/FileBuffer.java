package org.easyarch.xbuffer.kernel.mq.buffer;

import org.easyarch.xbuffer.kernel.XConfig;
import org.easyarch.xbuffer.kernel.common.IdGenerator;
import org.easyarch.xbuffer.kernel.common.io.DiskStreamInput;
import org.easyarch.xbuffer.kernel.common.io.DiskStreamOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by xingtianyu on 2018/10/21.
 */
public class FileBuffer extends AbstractClosableBuffer {

    private static final Logger logger = LoggerFactory.getLogger(FileBuffer.class);

    private String dataDir;

    /**
     * 当前读取的位置
     */
    private long position;

    /**
     * 当前数据文件后缀
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

    private IdGenerator generator = new IdGenerator(1,System.currentTimeMillis()%(2 << 9 - 1));

    public FileBuffer(String dir){
        try {
            this.dataDir = dir;
            this.offset = generator.nextId();
            initWrite();
            initRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private synchronized void initRead() throws IOException {
        File file = new File(this.dataDir+File.separator+String.format(XConfig.DATA_FILE_NAME,offset));
        if (!file.exists()){
            file.createNewFile();
        }
        FileInputStream fis = new FileInputStream(file);
        this.readChannel = fis.getChannel();
        File stFile = new File(this.dataDir+File.separator+XConfig.STATE_FILE_NAME);
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
            State state = new State(new Position(String.format(XConfig.dataPrefix,offset).getBytes(),0));
            state.writeTo(new DiskStreamOutput(this.stWriteChannel));
            this.position = 0;
        }else {
            State state = state();
            this.position = state.position().getPosition();
            this.readChannel.position(this.position);
        }
    }

    private synchronized void initWrite() throws IOException {
        File file = new File(this.dataDir+File.separator+String.format(XConfig.DATA_FILE_NAME,offset));
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
        State state = new State(new Position(String.format(XConfig.dataPrefix,offset).getBytes(),position));
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

    private void overThreshold(){
        try {
            closeWrite();
            this.offset = generator.nextId();
            initWrite();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeRead() throws IOException{
        readChannel.close();
    }

    @Override
    public void closeWrite() throws IOException{
        writeChannel.close();
    }


    @Override
    public void close() throws IOException {
        closeRead();
        closeWrite();
    }
}
