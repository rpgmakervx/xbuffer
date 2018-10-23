package org.easyarch.xbuffer.kernel;

/**
 * Created by xingtianyu on 2018/10/21.
 */
public class XConfig {

    public static String dataDir = "";

    public static final String dataSuffix = ".xb";
    public static final String stateSuffix = ".st";

    public static final String dataPrefix = "log-%d";
    public static final String statePrefix = "state";

    public static final String DATA_FILE_NAME = dataPrefix+dataSuffix;
    public static final String STATE_FILE_NAME = statePrefix+stateSuffix;
}
