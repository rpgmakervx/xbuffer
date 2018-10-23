package org.easyarch.xbuffer.kernel.buffer;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by xingtianyu on 2018/10/21.
 * mq中的数据单位
 */
public class Event {

    private Header header;

    private Body body;

    public Event(Header header, Body body) {
        this.header = header;
        this.body = body;
        this.header.fill(body.length);
    }

    /**
     * 获取完整信息
     * @return
     */
    public ByteBuffer content(){
        int hLength = header.length;
        int bLength = body.length;
        ByteBuffer content = ByteBuffer.allocate(hLength + bLength);
        content.put(header.content());
        content.put(body.content());
        content.flip();
        return content;
    }
    public int length(){

        int hLength = header.length;
        int bLength = body.length;
        return hLength + bLength;
    }

    public Body body(){
        return body;
    }

    public Header header(){
        return header;
    }

    public void writeTo(FileChannel channel) throws IOException {
        channel.write(content());
        channel.force(true);
    }

    public String toString(){
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
