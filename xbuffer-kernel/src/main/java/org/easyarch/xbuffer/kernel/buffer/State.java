package org.easyarch.xbuffer.kernel.buffer;

import javafx.geometry.Pos;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xingtianyu on 2018/10/21.
 * 记录kernel读取到当前队列的状态信息
 */
public class State{

    private Position position;

    public State(FileChannel channel) throws IOException {
        readFrom(channel);
    }

    public State(Position position) {
        this.position = position;
    }

    public ByteBuffer content(){
        return position.buffer();
    }

    public int length(){
        return this.position.length;
    }

    public Position position(){
        return this.position;
    }

    public void writeTo(FileChannel channel) throws IOException {
        //写指针复位
        channel.write(content(),0);
        channel.force(true);
    }

    public void readFrom(FileChannel channel) throws IOException {
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        lengthBuffer.clear();
        //先读取文件名长度
        channel.read(lengthBuffer);
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        //读取文件名
        byte[] fileNameBytes = new byte[length];
        ByteBuffer fileNameBuffer = ByteBuffer.allocate(length);
        channel.read(fileNameBuffer);
        fileNameBuffer.flip();
        fileNameBuffer.get(fileNameBytes);

        //读取位置信息
        ByteBuffer posBuffer = ByteBuffer.allocate(8);
        channel.read(posBuffer);
        posBuffer.flip();
        long position = posBuffer.getLong();
        //读指针复位
        channel.position(0);
        this.position = new Position(fileNameBytes,position);

    }

    @Override
    public String toString() {
        return "State{" +
                "position=" + position +
                '}';
    }
}