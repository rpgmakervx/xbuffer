package org.easyarch.xbuffer.kernel.rest.controller;

import org.easyarch.xbuffer.kernel.rest.AbstractRestController;
import org.easyarch.xbuffer.kernel.rest.XHttpRequest;
import org.easyarch.xbuffer.kernel.rest.XHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xingtianyu on 2018/11/1.
 */
public class TestController extends AbstractRestController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Override
    public void doAction(XHttpRequest request, XHttpResponse response) {
        logger.info("TestController execute");
        response.writeJson(request.body());
    }
}
