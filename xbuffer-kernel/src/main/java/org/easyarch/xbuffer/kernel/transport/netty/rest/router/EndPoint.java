package org.easyarch.xbuffer.kernel.transport.netty.rest.router;

import org.easyarch.xbuffer.kernel.transport.netty.rest.AbstractRestController;

/**
 * @author xingtianyu(code4j) Created on 2018-10-30.
 */
public class EndPoint {

    private String text;

    private AbstractRestController controller;

    public boolean hint(String endpoint){
        return endpoint.equals(text) || text.startsWith("{") && text.endsWith("}");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AbstractRestController getController() {
        return controller;
    }

    public void setController(AbstractRestController controller) {
        this.controller = controller;
    }

    public boolean isEmpty(){
        return controller == null;
    }
}
