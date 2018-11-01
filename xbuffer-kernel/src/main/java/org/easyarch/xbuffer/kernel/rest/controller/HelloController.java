package org.easyarch.xbuffer.kernel.rest.controller;
import com.alibaba.fastjson.JSONObject;
import org.easyarch.xbuffer.kernel.ClusterState;
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

    public HelloController(){
        table.registController(RestMethod.GET,"/mq/{name}",this);
        table.registController(RestMethod.GET,"/mq/put/xty",this);
    }

    @Override
    public void doAction(RestHttpRequest request, RestHttpResponse response) {
        logger.info("HelloController execute");
        JSONObject json = new JSONObject();
        json.put("status",200);
        json.put("message","message put success");
        response.writeJson(json.toJSONString());
    }
}
