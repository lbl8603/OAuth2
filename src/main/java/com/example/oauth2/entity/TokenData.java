package com.example.oauth2.entity;

/**
 * 授权令牌信息
 * @Author:lubeilin
 * @Date:Created in 12:04 2020/2/15
 * @Modified By:
 */
public class TokenData {
    private String openid;
    private String access_token;
    private String refresh_token;
    private long expire_in;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public long getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(long expire_in) {
        this.expire_in = expire_in;
    }
}
