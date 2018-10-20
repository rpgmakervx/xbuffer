package org.easyarch.xbuffer.kernel.buffer;

/**
 * Created by xingtianyu on 2018/10/21.
 * mq中的数据单位
 */
public class Event {

    private Header header;

    private Body body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }


}
