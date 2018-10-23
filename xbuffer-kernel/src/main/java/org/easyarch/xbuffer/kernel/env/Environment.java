package org.easyarch.xbuffer.kernel.env;

import java.nio.file.Path;

/**
 * Created by xingtianyu on 2018/10/23.
 */
public class Environment {

    private Path dataFile;

    private Path logFile;

    private int port;

    private String clusterName;

    private String nodeName;

    public Path getDataFile() {
        return dataFile;
    }

    public void setDataFile(Path dataFile) {
        this.dataFile = dataFile;
    }

    public Path getLogFile() {
        return logFile;
    }

    public void setLogFile(Path logFile) {
        this.logFile = logFile;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
