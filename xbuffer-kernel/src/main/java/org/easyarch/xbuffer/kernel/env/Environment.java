package org.easyarch.xbuffer.kernel.env;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by xingtianyu on 2018/10/23.
 */
public class Environment {

    private Path dataFile;

    private Path logFile;

    private int port;

    private String clusterName;

    private String nodeName;

    public String pathData(){
        return dataFile.toString();
    }

    public String pathLog(){
        return logFile.toString();
    }

    public int port(){
        return this.port;
    }

    public String clusterName(){
        return this.clusterName;
    }

    public String nodeName(){
        return this.nodeName;
    }

    public static class Builder{
        private Path dataFile;

        private Path logFile;

        private int port;

        private String clusterName;

        private String nodeName;

        public Builder dataFile(String dataDir){
            this.dataFile = Paths.get(dataDir);
            return this;
        }

        public Builder logFile(String logDir){
            this.logFile = Paths.get(logDir);
            return this;
        }

        public Builder port(int port){
            this.port = port;
            return this;
        }

        public Builder clusterName(String clusterName){
            this.clusterName = clusterName;
            return this;
        }

        public Builder nodeName(String nodeName){
            this.nodeName = nodeName;
            return this;
        }

        public Environment build(){
            Environment env = new Environment();
            env.clusterName = this.clusterName;
            env.nodeName = this.nodeName;
            env.dataFile = this.dataFile;
            env.logFile = this.logFile;
            env.port = this.port;
            return env;
        }
    }
}
