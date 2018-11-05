package org.easyarch.xbuffer.kernel;

import org.easyarch.xbuffer.kernel.mq.buffer.*;
import org.easyarch.xbuffer.kernel.env.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xingtianyu on 2018/10/21.
 */
public class Boostrap {

    private static final Logger logger = LoggerFactory.getLogger(Boostrap.class);

    private static String[] contents = {"this is xbuffer!","xbuffer is a message queue built by code4j","{'message':'success','code':200}"};

    public static void main(String[] args) throws InterruptedException {
        test();
    }

    private static void test() throws InterruptedException {
        XConfig.dataDir = "/Users/xingtianyu/IdeaProjects/xbuffer/datadir/";
        Settings settings = new Settings("/Users/xingtianyu/IdeaProjects/xbuffer/xbuffer-kernel/src/main/resources/xbuffer.yml");
        final FileBuffer buffer = org.easyarch.xbuffer.kernel.mq.buffer.FileBuffer.fileBufferBySettings(settings);
        ExecutorService threadpool = Executors.newCachedThreadPool();
        threadpool.submit(new Runnable() {
            @Override
            public void run() {
                int index = 0;
                while (true){
                    try {
                        Thread.sleep(990);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    write(buffer,"xbuffer"+index++);
                }
            }
        });
        threadpool.submit(new Runnable() {
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    read(buffer);
                }
            }
        });
    }

    public static void write(FileBuffer buffer,String content){
        Header header = new Header(123L, System.currentTimeMillis());
        Body body = new Body();
        body.put(content.getBytes());
        Event event = new Event(header,body);
        buffer.push(event);
        logger.info("write event:{}",event);
    }

    public static void read(FileBuffer buffer){
        Event event = buffer.pop();
        State state = buffer.state();
        logger.info("event is:{} ,position is:{}", event,state.position());

    }

    public static void state(FileBuffer buffer){
        State state = buffer.state();
        logger.info("stat is:{}",state);

    }
}
