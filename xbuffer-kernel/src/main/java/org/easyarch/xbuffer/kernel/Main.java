package org.easyarch.xbuffer.kernel;

import com.google.inject.Guice;
import com.google.inject.Injector;
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
        Guice.createInjector(new RestControllerModule());
        XHttpServer server = new XHttpServer();
        server.start(7777);
    }
}
