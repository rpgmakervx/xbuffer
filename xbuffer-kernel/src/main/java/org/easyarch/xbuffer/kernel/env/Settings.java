package org.easyarch.xbuffer.kernel.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created by xingtianyu on 2018/10/23.
 */
public class Settings {

    private static final Logger logger = LoggerFactory.getLogger(Settings.class);

    private String configPath;

    private Map<String,Object> settings;

    public Settings(String configPath) {
        this.configPath = configPath;
        this.init();
    }

    private void init(){
        Yaml yaml = new Yaml();
        try {
            FileInputStream fis = new FileInputStream(configPath);
            this.settings = (Map<String, Object>) yaml.load(fis);
        } catch (FileNotFoundException e) {
            logger.error("config file not found!,path:{}",configPath,e);
        }
    }


    public String get(String setting,String defVal){
        Object value = this.settings.get(setting);
        if (value == null){
            return defVal;
        }
        return String.valueOf(value);
    }
    public Integer getAsInteger(String setting,Integer defVal){
        Object value = this.settings.get(setting);
        if (value == null){
            return defVal;
        }
        return Integer.valueOf(String.valueOf(value));
    }
    public Long getAsLong(String setting,Long defVal){
        Object value = this.settings.get(setting);
        if (value == null){
            return defVal;
        }
        return Long.valueOf(String.valueOf(value));
    }
}
