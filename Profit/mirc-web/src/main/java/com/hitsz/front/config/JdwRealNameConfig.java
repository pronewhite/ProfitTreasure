package com.hitsz.front.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jdwx.realname")
public class JdwRealNameConfig {

    private String url;

    private String appkey;

    public JdwRealNameConfig() {
    }

    public JdwRealNameConfig(String url, String appkey) {
        this.url = url;
        this.appkey = appkey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
}

