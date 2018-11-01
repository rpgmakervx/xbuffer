package org.easyarch.xbuffer.kernel.rest;

/**
 * @author xingtianyu(code4j) Created on 2018-10-30.
 */
public abstract class AbstractRestController {

    public abstract void doAction(RestHttpRequest request, RestHttpResponse response);

}
