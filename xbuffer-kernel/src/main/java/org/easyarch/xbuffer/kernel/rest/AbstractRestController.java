package org.easyarch.xbuffer.kernel.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingtianyu(code4j) Created on 2018-10-30.
 */
public abstract class AbstractRestController {

    private List<String> urls = new ArrayList<>();

    public void addUrl(String url){
        this.urls.add(url);
    }

    public abstract void doAction(RestHttpRequest request, RestHttpResponse response);

}
