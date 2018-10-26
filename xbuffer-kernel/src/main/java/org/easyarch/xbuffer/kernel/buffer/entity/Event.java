package org.easyarch.xbuffer.kernel.buffer.entity;


import org.easyarch.xbuffer.kernel.common.Streamable;
import org.easyarch.xbuffer.kernel.common.io.StreamInput;
import org.easyarch.xbuffer.kernel.common.io.StreamOutput;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by xingtianyu on 2018/10/21.
 * mq中的数据单位
 * Event的创建方式：
 * 1.通过现有的Header和Body构建
 * 2.通过输入流生成内容
 */
public class Event implements Streamable,Entity<Event>{

    private Header header;

    private Body body;

    public Event() {
        this.header = new Header();
        this.body = new Body();
    }

    public Event(Header header, Body body) {
        this.header = header;
        this.body = body;
        this.header.fill(body.length());
    }

    /**
     * 获取完整信息，前提是数据已经写入header和body
     * @return
     */
    @Override
    public ByteBuffer content(){
        int hLength = header.length;
        int bLength = body.length;
        ByteBuffer content = ByteBuffer.allocate(hLength + bLength);
        content.put(header.content());
        content.put(body.content());
        content.flip();
        return content;
    }

    /**
     * 获取完整的长度。前提是数据已经写入header和body
     * @return
     */
    @Override
    public int length(){

        int hLength = header.length;
        int bLength = body.length;
        return hLength + bLength;
    }

    @Override
    public Event entity() {
        return isEmpty()?null:this;
    }

    public Body body(){
        return body;
    }

    public Header header(){
        return header;
    }

    public boolean isEmpty(){
        return header == null || header.isEmpty();
    }


    /**
     * 直接从流读取成完整的Event
     * 使用该方法获取对象内容后，返回使用entity()方法，空内容实体直接返回null
     * @param in
     * @throws IOException
     */
    @Override
    public void readFrom(StreamInput in) throws IOException {
        //先读取header
        header.readFrom(in);
        //然后设置body长度
        this.body.length = this.header.getOffset();
        //读取body数据
        body.readFrom(in);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        if (header == null){
            throw new UnsupportedOperationException("header is null,you cannot write now");
        }
        if (body == null){
            throw new UnsupportedOperationException("body is null,you cannot write now");
        }
        out.write(content());
    }

    @Override
    public String toString(){
        if (isEmpty()){
            return "null";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("[ length=")
                .append(header.length)
                .append(" id=")
                .append(header.getId())
                .append(" timestamp=")
                .append(header.getTimestamp())
                .append(" offset=")
                .append(header.getOffset())
                .append(" ] body=")
                .append(new String(body.content()));
        return sb.toString();
    }
}
