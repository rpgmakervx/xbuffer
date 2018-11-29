package org.easyarch.xbuffer.kernel.rest.controller.topic;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.Inject;
import org.easyarch.xbuffer.kernel.ClusterState;
import org.easyarch.xbuffer.kernel.XConfig;
import org.easyarch.xbuffer.kernel.env.Settings;
import org.easyarch.xbuffer.kernel.rest.AbstractRestController;
import org.easyarch.xbuffer.kernel.rest.RestHttpRequest;
import org.easyarch.xbuffer.kernel.rest.RestHttpResponse;
import org.easyarch.xbuffer.kernel.rest.RestMethod;
import org.easyarch.xbuffer.kernel.rest.controller.HelloController;
import org.easyarch.xbuffer.kernel.rest.router.RestRouteTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by xingtianyu on 2018/11/4.
 */
public class TopicCreateController extends AbstractRestController {
    private static final Logger logger = LoggerFactory.getLogger(TopicCreateController.class);
    private RestRouteTable table = ClusterState.restRouteTable();

    @Inject
    public TopicCreateController(Settings settings){
        super(settings);
        table.registController(RestMethod.PUT,"/topic/{topicId}/create",this);
        table.registController(RestMethod.POST,"/topic/{topicId}/create",this);
    }
    @Override
    public void doAction(RestHttpRequest request, RestHttpResponse response) {
        String topicId = request.param("topicId");
        String topicDir = XConfig.dataDir() + File.separator + topicId;
        JSONObject json = new JSONObject();
        try {
            Files.createDirectory(Paths.get(topicDir));
            logger.info("topic create successfully,topic name is {}",topicId);
            json.put("result","topic create successfully!");
            response.writeJson(json.toJSONString());
        } catch (IOException e) {
            logger.error("create topic error",e);
            JSONObject errorJson = new JSONObject();
            errorJson.put("reason","create topic error,"+e.getMessage());
            json.put("status",500);
            json.put("error",errorJson);
            response.writeJson(json.toJSONString(),500);
        }
    }
}
