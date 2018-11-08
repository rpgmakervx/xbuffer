package org.easyarch.xbuffer.kernel.rest;

import com.google.inject.AbstractModule;
import org.easyarch.xbuffer.kernel.rest.controller.HelloController;
import org.easyarch.xbuffer.kernel.rest.controller.TestController;
import org.easyarch.xbuffer.kernel.rest.controller.op.PutMessageController;
import org.easyarch.xbuffer.kernel.rest.controller.topic.TopicCreateController;
import org.easyarch.xbuffer.kernel.rest.controller.topic.TopicTruncateController;

/**
 * @author xingtianyu(code4j) Created on 2018-11-1.
 */
public class RestControllerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HelloController.class).asEagerSingleton();
        bind(TestController.class).asEagerSingleton();
        bind(TopicCreateController.class).asEagerSingleton();
        bind(TopicTruncateController.class).asEagerSingleton();
        bind(PutMessageController.class).asEagerSingleton();

    }
}
