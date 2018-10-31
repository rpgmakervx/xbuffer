package org.easyarch.xbuffer.kernel.rest.controller;
import com.alibaba.fastjson.JSONObject;
import org.easyarch.xbuffer.kernel.netty.HttpDispatcherHandler;
import org.easyarch.xbuffer.kernel.rest.AbstractRestController;
import org.easyarch.xbuffer.kernel.rest.XHttpRequest;
import org.easyarch.xbuffer.kernel.rest.XHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xingtianyu on 2018/11/1.
 */
public class HelloController extends AbstractRestController{
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);


    @Override
    public void doAction(XHttpRequest request, XHttpResponse response) {
        logger.info("HelloController execute");
        JSONObject json = new JSONObject();
        json.put("status",200);
        json.put("message","message put success");
        response.writeJson(json.toJSONString());
    }
}
