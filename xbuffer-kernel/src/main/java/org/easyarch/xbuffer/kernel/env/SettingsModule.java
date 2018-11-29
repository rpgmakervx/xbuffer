package org.easyarch.xbuffer.kernel.env;

import com.google.inject.AbstractModule;

/**
 * Created by xingtianyu on 2018/11/30.
 */
public class SettingsModule extends AbstractModule {

    private Settings settings;

    public SettingsModule(Settings settings){
        this.settings = settings;
    }

    @Override
    protected void configure() {
        bind(Settings.class).toInstance(settings);
    }
}
