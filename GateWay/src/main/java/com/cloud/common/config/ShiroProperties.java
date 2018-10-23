package com.cloud.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author : BieFeNg
 * @DATE : 2018/10/8 22:20
 */
@Component
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {

    private List<String> filter;

    private String logout;

    private String authc;

    private String loginUrl;

    private String successUrl;

    private String errorUrl;

    public List<String> getFilter() {
        return filter;
    }

    public void setFilter(List<String> filter) {
        this.filter = filter;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }

    public String getAuthc() {
        return authc;
    }

    public void setAuthc(String authc) {
        this.authc = authc;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }
}
