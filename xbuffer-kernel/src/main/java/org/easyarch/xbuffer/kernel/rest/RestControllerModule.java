package org.easyarch.xbuffer.kernel.rest;

import com.google.inject.AbstractModule;
import org.easyarch.xbuffer.kernel.rest.controller.HelloController;
import org.easyarch.xbuffer.kernel.rest.controller.TestController;

/**
 * @author xingtianyu(code4j) Created on 2018-11-1.
 */
public class RestControllerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HelloController.class).asEagerSingleton();
        bind(TestController.class).asEagerSingleton();

    }
}
