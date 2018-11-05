package org.easyarch.xbuffer.kernel.mq.buffer;

import com.google.inject.AbstractModule;
import org.easyarch.xbuffer.kernel.env.Settings;

/**
 * Created by xingtianyu on 2018/11/4.
 */
public class BufferModule extends AbstractModule {

    private Settings settings;

    public BufferModule(Settings settings){
        this.settings = settings;
    }

    @Override
    protected void configure() {
        bind(FileBuffer.class).toInstance(FileBuffer.fileBufferBySettings(this.settings));
    }
}
