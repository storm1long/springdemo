package com.preseed.springdemo.security.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 安全配置属性
 *
 * @author haoxr
 * @since 2024/4/18
 */
@ConfigurationProperties(prefix = "security")
public class CustomSecurityProperties {

    /**
     * 会话方式
     */
    private SessionProperty session;

    /**
     * 白名单 URL 集合
     */
    private String[] whiteListPaths;

    public SessionProperty getSession() {
        return session;
    }

    public void setSession(SessionProperty session) {
        this.session = session;
    }

    public String[] getWhiteListPaths() {
        return whiteListPaths;
    }

    public void setWhiteListPaths(String[] whiteListPaths) {
        this.whiteListPaths = whiteListPaths;
    }




    /**
     * 会话属性
     */
    public static class SessionProperty {
        private String type;
    }
}