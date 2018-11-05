package org.easyarch.xbuffer.kernel;

import com.google.inject.Guice;
import org.easyarch.xbuffer.kernel.mq.buffer.BufferModule;
import org.easyarch.xbuffer.kernel.env.Settings;
import org.easyarch.xbuffer.kernel.netty.XHttpServer;
import org.easyarch.xbuffer.kernel.rest.RestControllerModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xingtianyu on 2018/10/20.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Settings settings = new Settings("/Users/xingtianyu/IdeaProjects/xbuffer/xbuffer-kernel/src/main/resources/xbuffer.yml");
        Guice.createInjector(new RestControllerModule());
        XHttpServer server = new XHttpServer();
        server.start(settings.getAsInteger("port",7000));
    }
}
