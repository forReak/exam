package com.fr.exam.entity.DTO;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author furao
 * @desc
 * @date 2021/2/25
 * @package com.fr.exam.entity
 */
public class BaiduAkResult {
    @JSONField(name = "refresh_token")
    private String refreshToken;
    @JSONField(name = "expires_in")
    private Integer expiresIn;
    @JSONField(name = "session_key")
    private String sessionKey;
    @JSONField(name = "access_token")
    private String accessToken;
    private String scope;
    @JSONField(name = "session_secret")
    private String sessionSecret;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSessionSecret() {
        return sessionSecret;
    }

    public void setSessionSecret(String sessionSecret) {
        this.sessionSecret = sessionSecret;
    }
}
