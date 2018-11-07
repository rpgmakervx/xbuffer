package org.easyarch.xbuffer.kernel.rest.controller.topic;

import org.easyarch.xbuffer.kernel.ClusterState;
import org.easyarch.xbuffer.kernel.rest.AbstractRestController;
import org.easyarch.xbuffer.kernel.rest.RestHttpRequest;
import org.easyarch.xbuffer.kernel.rest.RestHttpResponse;
import org.easyarch.xbuffer.kernel.rest.RestMethod;
import org.easyarch.xbuffer.kernel.rest.router.RestRouteTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xingtianyu on 2018/11/4.
 * 清空topic中的所有消息
 */
public class TopicTruncateController extends AbstractRestController {
    private static final Logger logger = LoggerFactory.getLogger(TopicTruncateController.class);
    private RestRouteTable table = ClusterState.restRouteTable();

    public TopicTruncateController() {
        this.table.registController(RestMethod.DELETE,"/topic/{topicName}/truncate",this);
        this.table.registController(RestMethod.PUT,"/topic/{topicName}/truncate",this);
        this.table.registController(RestMethod.POST,"/topic/{topicName}/truncate",this);
    }

    @Override
    public void doAction(RestHttpRequest request, RestHttpResponse response) {

    }
}