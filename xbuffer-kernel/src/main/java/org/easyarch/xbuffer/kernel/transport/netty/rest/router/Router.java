package org.easyarch.xbuffer.kernel.transport.netty.rest.router;

import org.easyarch.xbuffer.kernel.transport.netty.rest.AbstractRestController;

/**
 * @author xingtianyu(code4j) Created on 2018-10-30.
 */
public class Router {

    private AbstractRestController controller;

    private String endpoint;

    public Router(String endpoint) {
        this.endpoint = endpoint;
    }

    public Router(String endpoint, AbstractRestController controller) {
        this.controller = controller;
        this.endpoint = endpoint;
    }

    public AbstractRestController getController() {
        return controller;
    }

    public void setController(AbstractRestController controller) {
        this.controller = controller;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public boolean hint(String endpoint){
        return this.endpoint.equals(endpoint) || endpoint.startsWith("{") && endpoint.endsWith("}");
    }
    public boolean isEmpty(){
        return controller == null;
    }

    @Override
    public String toString() {
        return "Router{" +
                "controller=" + controller +
                ", endpoint='" + endpoint + '\'' +
                '}';
    }
}
