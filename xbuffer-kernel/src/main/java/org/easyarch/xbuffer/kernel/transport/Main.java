package org.easyarch.xbuffer.kernel.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xingtianyu on 2018/10/20.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        XHttpServer server = new XHttpServer();
        server.start(7777);
    }
}
