package org.easyarch.xbuffer.kernel.rest.controller;

import com.google.inject.Inject;
import org.easyarch.xbuffer.kernel.ClusterState;
import org.easyarch.xbuffer.kernel.env.Settings;
import org.easyarch.xbuffer.kernel.rest.AbstractRestController;
import org.easyarch.xbuffer.kernel.rest.RestHttpRequest;
import org.easyarch.xbuffer.kernel.rest.RestHttpResponse;
import org.easyarch.xbuffer.kernel.rest.RestMethod;
import org.easyarch.xbuffer.kernel.rest.router.RestRouteTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xingtianyu on 2018/11/1.
 */
public class TestController extends AbstractRestController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    private RestRouteTable table = ClusterState.restRouteTable();

    @Inject
    public TestController(Settings settings) {
        super(settings);
        table.registController(RestMethod.POST,"/hello/test",this);
        table.registController(RestMethod.GET,"/hello/test",this);
    }

    @Override
    public void doAction(RestHttpRequest request, RestHttpResponse response) {
        logger.info("TestController execute");
        if (request.method().equals(RestMethod.GET)){
            response.writeJson("{'message':'tesst ok!'}");
        }else if (request.method().equals(RestMethod.POST)){
            response.writeJson(request.body());
        }
    }
}
