package org.easyarch.xbuffer.kernel.netty;

import org.easyarch.xbuffer.kernel.ClusterState;
import org.easyarch.xbuffer.kernel.rest.controller.HelloController;
import org.easyarch.xbuffer.kernel.rest.controller.TestController;
import org.easyarch.xbuffer.kernel.rest.router.RestRouteTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xingtianyu on 2018/10/20.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        RestRouteTable table = ClusterState.restRouteTable();
        table.registController("/mq/put",new HelloController());
        table.registController("/hello/test",new TestController());
        XHttpServer server = new XHttpServer();
        server.start(7777);
    }
}
