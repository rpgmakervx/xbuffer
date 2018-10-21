package org.easyarch.xbuffer.kernel.buffer;

import org.easyarch.xbuffer.kernel.XConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by xingtianyu on 2018/10/21.
 */
public class FileBuffer extends AbstractBuffer {

    private static final Logger logger = LoggerFactory.getLogger(FileBuffer.class);

    //当前读取的位置
    private long position;

    //当前写入的位置
    private long offset;
    //读数据channel
    private FileChannel readChannel;
    //写数据channel
    private FileChannel writeChannel;
    //写statechannel
    private FileChannel stWriteChannel;
    //写读statechannel
    private FileChannel stReadChannel;

    public FileBuffer(){
        try {
            initWrite();
            initRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRead() throws Exception {
        File file = new File(XConfig.dataDir+"log-2"+XConfig.dataSuffix);
        FileInputStream fis = new FileInputStream(file);
        this.readChannel = fis.getChannel();
        File stFile = new File(XConfig.dataDir+"read"+XConfig.stateSuffix);
        boolean init = false;
        if (!stFile.exists()){
            stFile.createNewFile();
            init = true;
        }
        FileOutputStream stFos = new FileOutputStream(stFile,false);
        FileInputStream stFis = new FileInputStream(stFile);
        this.stWriteChannel = stFos.getChannel();
        this.stReadChannel = stFis.getChannel();
        if (init){
            logger.info("state init");
            State state = new State(new Position("log-2",0));
            this.stWriteChannel.write(state.content());
            this.stWriteChannel.force(true);
            this.position = 0;
        }else {
            logger.info("state already exists");
            State state = state();
            this.position = state.position().getPosition();
            this.readChannel.position(this.position);
        }
    }

    private void initWrite() throws Exception {
        File file = new File(XConfig.dataDir+"log-2"+XConfig.dataSuffix);
        if (!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file,true);
        this.writeChannel = fos.getChannel();

    }

    public void push(Event event) {
        try {
            this.offset = event.length();
            this.writeChannel.write(event.content());
            this.writeChannel.force(true);
//            this.writeChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }

    public void batch(List<Event> events) {
        if (events != null && !events.isEmpty()){
            for (Event e:events){
                push(e);
            }
        }
    }

    public Event pop() {
        try {
            if (readChannel.position() == readChannel.size()){
                return null;
            }
            //读取header长度
            ByteBuffer lenBuffer = ByteBuffer.allocate(4);
            lenBuffer.clear();
            this.readChannel.read(lenBuffer);
            lenBuffer.flip();
            int headerLength = lenBuffer.getInt();

            //读取剩下的header
            ByteBuffer headerBuffer = ByteBuffer.allocate(headerLength - 4);
            headerBuffer.clear();
            this.readChannel.read(headerBuffer);

            //解析header
            headerBuffer.flip();
            long id = headerBuffer.getLong();
            long timestamp = headerBuffer.getLong();
            int offset = headerBuffer.getInt();
            Header header = new Header(id,timestamp);
            header.setOffset(offset);
//            logger.info("length:{} , id:{} , timestamp:{} , offset:{}",headerLength ,id,timestamp,offset);

            //读取body
            ByteBuffer bodybuffer = ByteBuffer.allocate(offset);
            bodybuffer.clear();
            this.readChannel.read(bodybuffer);
            //解析body
            Body body = new Body();
            body.put(bodybuffer);

            //写状态，记录当前读取位置
            long position = readChannel.position();
            State state = new State(new Position("log-2",position));
            this.stWriteChannel.write(state.content());
            this.stWriteChannel.force(true);
            return new Event(header, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Event> drain(int batchSize) {
        return null;
    }

    public State state() {
        try {
            ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
            //先读取文件名长度
            this.stReadChannel.read(lengthBuffer);
            lengthBuffer.flip();
            int length = lengthBuffer.getInt();

            //读取文件名
            byte[] fileNameBytes = new byte[length];
            ByteBuffer fileNameBuffer = ByteBuffer.allocate(length);
            this.stReadChannel.read(fileNameBuffer);
            fileNameBuffer.flip();
            fileNameBuffer.get(fileNameBytes);

            //读取位置信息
            ByteBuffer posBuffer = ByteBuffer.allocate(8);
            this.stReadChannel.read(posBuffer);
            posBuffer.flip();
            long position = posBuffer.getLong();

            Position pos = new Position(new String(fileNameBytes),position);
            return new State(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
