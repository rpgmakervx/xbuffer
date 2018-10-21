package org.easyarch.xbuffer.kernel.buffer;

import javafx.geometry.Pos;

import java.nio.ByteBuffer;

/**
 * Created by xingtianyu on 2018/10/21.
 * 记录kernel读取到当前队列的状态信息
 */
public class State{

    private Position position;

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

    @Override
    public String toString() {
        return "State{" +
                "position=" + position +
                '}';
    }
}
