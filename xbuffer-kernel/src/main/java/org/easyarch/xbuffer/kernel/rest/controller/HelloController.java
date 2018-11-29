package org.easyarch.xbuffer.kernel.rest.controller;
import com.alibaba.fastjson.JSONObject;
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
public class HelloController extends AbstractRestController{
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    private RestRouteTable table = ClusterState.restRouteTable();

    @Inject
    public HelloController(Settings settings){
        super(settings);
        table.registController(RestMethod.GET,"/mq/get/{topic}/{clientId}",this);
        table.registController(RestMethod.GET,"/mq/{topic}/{clientId}",this);
    }

    @Override
    public void doAction(RestHttpRequest request, RestHttpResponse response) {
        logger.info("HelloController execute");
        JSONObject json = new JSONObject();
        json.put("topic",request.param("topic"));
        json.put("clientId",request.param("clientId"));
        response.writeJson(json.toJSONString());
    }
}
