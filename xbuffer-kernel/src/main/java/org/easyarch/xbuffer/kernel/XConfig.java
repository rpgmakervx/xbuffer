package org.easyarch.xbuffer.kernel;

import org.easyarch.xbuffer.kernel.env.Settings;

/**
 * Created by xingtianyu on 2018/10/21.
 */
public class XConfig {

    public static final String dataSuffix = ".xb";
    public static final String stateSuffix = ".st";

    public static final String dataPrefix = "log-%d";
    public static final String statePrefix = "state";

    public static final String DATA_FILE_NAME = dataPrefix+dataSuffix;
    public static final String STATE_FILE_NAME = statePrefix+stateSuffix;

    private static Settings settings;

    private XConfig(){
    }

    public static void init(Settings settings){
        XConfig.settings = settings;
    }

    public static String dataDir(){
        return settings.get("path.data","");
    }
}
