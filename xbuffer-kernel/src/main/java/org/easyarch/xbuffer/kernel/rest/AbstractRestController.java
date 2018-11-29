package org.easyarch.xbuffer.kernel.rest;

import org.easyarch.xbuffer.kernel.common.component.AbstractComponent;
import org.easyarch.xbuffer.kernel.env.Settings;

/**
 * @author xingtianyu(code4j) Created on 2018-10-30.
 */
public abstract class AbstractRestController extends AbstractComponent{

    public AbstractRestController(Settings settings) {
        super(settings);
    }

    public abstract void doAction(RestHttpRequest request, RestHttpResponse response);

}
