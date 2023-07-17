package com.hitsz.front.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jdwx.sms")
public class jdwxSmsConfig {

    private String url;
    private String content;
    private String appkey;
    private String loginText;

    public jdwxSmsConfig() {
    }

    public jdwxSmsConfig(String url, String contend, String appkey) {
        this.url = url;
        this.content = contend;
        this.appkey = appkey;
    }

    public String getLoginText() {
        return loginText;
    }

    public void setLoginText(String loginText) {
        this.loginText = loginText;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String contend) {
        this.content = contend;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
}
