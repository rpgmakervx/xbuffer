package org.easyarch.xbuffer.kernel.mq.buffer;

import com.google.inject.Inject;
import org.easyarch.xbuffer.kernel.common.Streamable;
import org.easyarch.xbuffer.kernel.common.io.StreamInput;
import org.easyarch.xbuffer.kernel.common.io.StreamOutput;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by xingtianyu on 2018/10/21.
 * 记录kernel读取到当前队列的状态信息
 */
public class State implements Streamable,Entity<State> {

    /**
     * 读取位置
     */
    private Position position;

    public State() throws IOException {
        this.position = new Position();
    }

    public State(Position position) {
        this.position = position;
    }


    @Override
    public int length(){
        return this.position.length;
    }

    @Override
    public State entity() {
        return isEmpty()?null:this;
    }

    public Position position(){
        return this.position;
    }

//    public void writeTo(FileChannel channel) throws IOException {
//        //写指针复位
//        channel.write(content(),0);
//        channel.force(true);
//    }

//    public void readFrom(FileChannel channel) throws IOException {
//        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
//        //先读取文件名长度
//        channel.read(lengthBuffer);
//        lengthBuffer.flip();
//        int length = lengthBuffer.getInt();
//
//        //读取文件名
//        byte[] fileNameBytes = new byte[length];
//        ByteBuffer fileNameBuffer = ByteBuffer.allocate(length);
//        channel.read(fileNameBuffer);
//        fileNameBuffer.flip();
//        fileNameBuffer.get(fileNameBytes);
//
//        //读取位置信息
//        ByteBuffer posBuffer = ByteBuffer.allocate(8);
//        channel.read(posBuffer);
//        posBuffer.flip();
//        long position = posBuffer.getLong();
//        //读指针复位
//        channel.position(0);
//        this.position = new Position(fileNameBytes,position);
//
//    }

    @Override
    public String toString() {
        return "State{" +
                "position=" + position +
                '}';
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        this.position.readFrom(in);
        //读指针复位
        in.position(0);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        out.write(content(),0);
    }

    public boolean isEmpty() {
        return position.isEmpty();
    }

    @Override
    public ByteBuffer content() {
        return position.buffer();
    }
}
