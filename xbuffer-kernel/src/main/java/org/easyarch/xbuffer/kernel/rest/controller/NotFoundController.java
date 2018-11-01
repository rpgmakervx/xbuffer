package org.easyarch.xbuffer.kernel.rest.controller;

import com.alibaba.fastjson.JSONObject;
import org.easyarch.xbuffer.kernel.rest.AbstractRestController;
import org.easyarch.xbuffer.kernel.rest.RestHttpRequest;
import org.easyarch.xbuffer.kernel.rest.RestHttpResponse;

/**
 * @author xingtianyu(code4j) Created on 2018-11-1.
 */
public class NotFoundController extends AbstractRestController {

    @Override
    public void doAction(RestHttpRequest request, RestHttpResponse response) {
        JSONObject json = new JSONObject();
        JSONObject entity = new JSONObject();
        entity.put("status",404);
        entity.put("reason","404 Not Found");
        json.put("error",entity);
        response.writeJson(json.toJSONString(),404);
    }
}
