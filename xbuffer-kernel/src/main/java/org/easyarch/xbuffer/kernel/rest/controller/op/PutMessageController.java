package org.easyarch.xbuffer.kernel.rest.controller.op;

import com.alibaba.fastjson.JSONObject;
import org.easyarch.xbuffer.kernel.ClusterState;
import org.easyarch.xbuffer.kernel.XConfig;
import org.easyarch.xbuffer.kernel.mq.BufferOperator;
import org.easyarch.xbuffer.kernel.mq.XMessage;
import org.easyarch.xbuffer.kernel.mq.buffer.FileBuffer;
import org.easyarch.xbuffer.kernel.rest.AbstractRestController;
import org.easyarch.xbuffer.kernel.rest.RestHttpRequest;
import org.easyarch.xbuffer.kernel.rest.RestHttpResponse;
import org.easyarch.xbuffer.kernel.rest.RestMethod;
import org.easyarch.xbuffer.kernel.rest.controller.topic.TopicCreateController;
import org.easyarch.xbuffer.kernel.rest.router.RestRouteTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xingtianyu on 2018/11/9.
 */
public class PutMessageController extends AbstractRestController {
    private static final Logger logger = LoggerFactory.getLogger(PutMessageController.class);
    private RestRouteTable table = ClusterState.restRouteTable();

    public PutMessageController() {
        table.registController(RestMethod.PUT,"/topic/{topicId}/put",this);
        table.registController(RestMethod.POST,"/topic/{topicId}/put",this);
    }

    @Override
    public void doAction(RestHttpRequest request, RestHttpResponse response) {
        String topicId = request.param("topicId");
        BufferOperator operator = BufferOperator.getOperator(topicId);
        if (operator == null){
            operator = new BufferOperator(new FileBuffer(XConfig.dataDir() + File.separator + topicId));
            BufferOperator.addOperator(topicId,operator);
        }
        Map<String, Object> body = request.bodyAsMap();
        String message = String.valueOf(body.get("message"));
        XMessage xMessage = new XMessage(topicId,message.getBytes());
        JSONObject json = new JSONObject();
        try {
            operator.produce(xMessage);
            json.put("result","put message successfully!");
            response.writeJson(json.toJSONString());
        } catch (IOException e) {
            logger.error("put message error",e);
            JSONObject errorJson = new JSONObject();
            errorJson.put("reason","put message error,"+e.getMessage());
            json.put("status",500);
            json.put("error",errorJson);
            response.writeJson(json.toJSONString(),500);
        }
    }

    public static void main(String[] args) {
        System.out.println(2 << 10);
    }
}
