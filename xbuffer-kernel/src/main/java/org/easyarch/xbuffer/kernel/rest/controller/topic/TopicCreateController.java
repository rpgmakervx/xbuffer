package org.easyarch.xbuffer.kernel.rest.controller.topic;

import org.easyarch.xbuffer.kernel.ClusterState;
import org.easyarch.xbuffer.kernel.rest.AbstractRestController;
import org.easyarch.xbuffer.kernel.rest.RestHttpRequest;
import org.easyarch.xbuffer.kernel.rest.RestHttpResponse;
import org.easyarch.xbuffer.kernel.rest.RestMethod;
import org.easyarch.xbuffer.kernel.rest.controller.HelloController;
import org.easyarch.xbuffer.kernel.rest.router.RestRouteTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xingtianyu on 2018/11/4.
 */
public class TopicCreateController extends AbstractRestController {
    private static final Logger logger = LoggerFactory.getLogger(TopicCreateController.class);
    private RestRouteTable table = ClusterState.restRouteTable();

    public TopicCreateController(){
        table.registController(RestMethod.PUT,"/topic/{topicName}/create",this);
        table.registController(RestMethod.POST,"/topic/{topicName}/create",this);
    }
    @Override
    public void doAction(RestHttpRequest request, RestHttpResponse response) {

    }
}
