package org.easyarch.xbuffer.kernel.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xingtianyu(code4j) Created on 2018-10-30.
 */
public abstract class AbstractRestController {

    private Map<String,Map<Integer,String>> pathVariable = new HashMap<>(2);

    protected void addPathVariable(String url,Integer index,String path){
//        pathVariable.put(index,path);
    }
    protected String getPathParam(Integer index){
        return null;
    }

    public abstract void doAction(RestHttpRequest request, RestHttpResponse response);

}
