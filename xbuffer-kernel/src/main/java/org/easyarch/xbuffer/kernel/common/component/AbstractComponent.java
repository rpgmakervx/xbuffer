package org.easyarch.xbuffer.kernel.common.component;

import org.easyarch.xbuffer.kernel.env.Settings;

/**
 * Created by xingtianyu on 2018/11/30.
 */
public class AbstractComponent {
    protected Settings settings;

    public AbstractComponent(Settings settings) {
        this.settings = settings;
    }
}
