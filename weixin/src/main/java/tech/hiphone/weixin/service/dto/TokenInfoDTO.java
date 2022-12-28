package tech.hiphone.weixin.service.dto;

import org.apache.commons.lang3.StringUtils;

public class TokenInfoDTO implements Cloneable {

    private String accessToken;

    private long expireTime;

    public TokenInfoDTO() {
    }

    public TokenInfoDTO(String accessToken, long expireTime) {
        super();
        this.accessToken = accessToken;
        this.expireTime = expireTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public boolean needsRefresh() {
        return StringUtils.isEmpty(accessToken) || expireTime < System.currentTimeMillis();
    }

    @Override
    public TokenInfoDTO clone() {
        return new TokenInfoDTO(accessToken, expireTime);
    }

}
