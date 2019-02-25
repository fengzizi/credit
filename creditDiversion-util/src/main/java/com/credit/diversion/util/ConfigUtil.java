package com.credit.diversion.util;

import java.io.InputStreamReader;
import java.util.Properties;

public class ConfigUtil {

    private static ConfigUtil configUtil;
    private static Properties properties;

    private ConfigUtil(){
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("config/conf.properties"),"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConfigUtil getInstance(){
        if(configUtil == null){
            configUtil = new ConfigUtil();
        }
        return configUtil;
    }

    public String getString(String key){
        return properties.getProperty(key);
    }
}
