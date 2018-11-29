package org.easyarch.xbuffer.kernel.rest.controller.op;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.Inject;
import org.easyarch.xbuffer.kernel.ClusterState;
import org.easyarch.xbuffer.kernel.XConfig;
import org.easyarch.xbuffer.kernel.env.Settings;
import org.easyarch.xbuffer.kernel.mq.BufferOperator;
import org.easyarch.xbuffer.kernel.mq.XMessage;
import org.easyarch.xbuffer.kernel.mq.buffer.FileBuffer;
import org.easyarch.xbuffer.kernel.rest.AbstractRestController;
import org.easyarch.xbuffer.kernel.rest.RestHttpRequest;
import org.easyarch.xbuffer.kernel.rest.RestHttpResponse;
import org.easyarch.xbuffer.kernel.rest.RestMethod;
import org.easyarch.xbuffer.kernel.rest.router.RestRouteTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by xingtianyu on 2018/11/19.
 */
public class FetchMessageController extends AbstractRestController {

    private static final Logger logger = LoggerFactory.getLogger(PutMessageController.class);
    private RestRouteTable table = ClusterState.restRouteTable();

    @Inject
    public FetchMessageController(Settings settings){
        super(settings);
        table.registController(RestMethod.GET,"/topic/{topicId}/fetch",this);
    }

    @Override
    public void doAction(RestHttpRequest request, RestHttpResponse response) {
        String topicId = request.param("topicId");
        BufferOperator operator = BufferOperator.getOperator(topicId);
        JSONObject json = new JSONObject();
        JSONObject content = new JSONObject();
        XMessage message = null;
        try {
            Thread.sleep(2000);
            message = operator.consume(topicId,null);
            if (message == null){
                json.put("found",false);
            }else{
                content.put("topicId",topicId);
                content.put("message",new String(message.getContent()));
                json.put("found",true);
            }
            json.put("result",content);
            logger.info("fetch message:{}",message);
            response.writeJson(json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
