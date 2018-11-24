package org.easyarch.xbuffer.kernel.log;

import org.easyarch.xbuffer.kernel.XConfig;
import org.easyarch.xbuffer.kernel.common.io.DiskStreamInput;
import org.easyarch.xbuffer.kernel.common.io.DiskStreamOutput;
import org.easyarch.xbuffer.kernel.mq.buffer.Event;
import org.easyarch.xbuffer.kernel.mq.buffer.Offset;
import org.easyarch.xbuffer.kernel.mq.buffer.Position;
import org.easyarch.xbuffer.kernel.mq.buffer.State;
import org.easyarch.xbuffer.kernel.util.FileUtil;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * Created by xingtianyu on 2018/11/25.
 */
public class XLog implements Closeable{

    private String dataDir;

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
     * 读statechannel
     */
    private FileChannel stReadChannel;
    /**
     * 读offsetchannel
     */
    private FileChannel ofReadChannel;
    /**
     * 写offsetchannel
     */
    private FileChannel ofWriteChannel;

    /**
     * 当前读取的位置
     */
    private long position;

    /**
     * 当前数据文件后缀
     */
    private volatile long offset;

    public XLog(String dataDir){
        this.dataDir = dataDir;
        initOffset();
        initLog();
        initPosition();
    }

    /**
     * 初始化写入位置文件
     */
    private void initOffset(){
        try {
            File offsetFile = new File(this.dataDir+File.separator+XConfig.OFFSET_FILE_NAME);
            boolean init = false;
            if (!offsetFile.exists()){
                offsetFile.createNewFile();
                init = true;
            }
            FileOutputStream ofFos = new FileOutputStream(offsetFile,true);
            FileInputStream ofFis = new FileInputStream(offsetFile);
            this.ofWriteChannel = ofFos.getChannel();
            this.ofReadChannel = ofFis.getChannel();
            if (init){
                Offset offset = new Offset(0);
                offset.writeTo(new DiskStreamOutput(this.ofWriteChannel));
                this.offset = 0;
            }else {
                long num = FileUtil.maxFileNameNum(this.dataDir);
                this.offset = num;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 初始化数据文件
     */
    private void initLog(){
        try {
            File file = new File(this.dataDir+File.separator+String.format(XConfig.DATA_FILE_NAME,offset));
            if (!file.exists()){
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            this.readChannel = fis.getChannel();
            FileOutputStream fos = new FileOutputStream(file,true);
            this.writeChannel = fos.getChannel();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initPosition(){
        try {
            //初始化读取状态文件
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
                State state = new State();
                state.readFrom(new DiskStreamInput(this.stReadChannel));
                this.position = state.position().getPosition();
                this.readChannel.position(this.position);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void writeLog(Event event) throws IOException {
        this.offset = event.length();
        event.writeTo(new DiskStreamOutput(this.writeChannel));
    }

    public Event readLog() throws IOException {
        Event event = new Event();
        event.readFrom(new DiskStreamInput(this.readChannel));
        //写状态，记录当前读取位置
        this.position = readChannel.position();
        State state = new State(new Position(String.format(XConfig.dataPrefix,offset).getBytes(),position));
        state.writeTo(new DiskStreamOutput(this.stWriteChannel));
        return event;
    }

    public State readState() throws IOException {
        State state = new State();
        state.readFrom(new DiskStreamInput(this.stReadChannel));
        return state;
    }

    public void closeReadLog() throws IOException{
        readChannel.close();
    }

    public void closeWriteLog() throws IOException{
        writeChannel.close();
    }

    public void closeWriteState() throws IOException {
        this.stWriteChannel.close();
    }

    public void closeReadState() throws IOException {
        this.stReadChannel.close();
    }

    public void closeReadOffset() throws IOException {
        this.ofReadChannel.close();
    }

    public void closeWriteOffset() throws IOException {
        this.ofWriteChannel.close();
    }
    @Override
    public void close() throws IOException {
        closeReadLog();
        closeWriteLog();
        closeReadState();
        closeWriteState();
        closeReadOffset();
        closeWriteOffset();
    }
}
