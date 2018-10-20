package org.easyarch.xbuffer.kernel;

import org.easyarch.xbuffer.kernel.buffer.Body;
import org.easyarch.xbuffer.kernel.buffer.Event;
import org.easyarch.xbuffer.kernel.buffer.FileBuffer;

/**
 * Created by xingtianyu on 2018/10/21.
 */
public class Boostrap {
    public static void main(String[] args) {
        XConfig.dataDir = "/Users/xingtianyu/IdeaProjects/xbuffer/datadir/";
        Event event = new Event();
        Body body = new Body();
        body.fill("hello world!".getBytes());
        event.setBody(body);
        FileBuffer buffer = new FileBuffer();
        buffer.push(event);
    }
}
