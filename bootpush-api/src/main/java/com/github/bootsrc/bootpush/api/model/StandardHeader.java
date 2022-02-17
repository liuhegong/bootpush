package com.github.bootsrc.bootpush.api.model;

import java.io.Serializable;

public class StandardHeader implements Serializable {
    private static final long serialVersionUID = 395281078633878131L;
    /**
     * 消息类型
     */
    private int type;
    /**
     * 长链接会话id
     */
    private String sessionId;
    /**
     * 消息的优先级
     */
    private int priority;
    /**
     * 客户端设备的注册id(每个设备的registrationId是唯一的)
     */
    private String registrationId;
    /**
     * 客户端token(跟registrationId对应的客户端token)
     */
    private String clientToken;
    /**
     * 推送/IM应用系统的应用id，每个应用项目的appId是唯一的
     */
    private String appId;
    private String resultCode;
    private String resultText;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }
}
