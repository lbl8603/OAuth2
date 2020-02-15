package com.example.oauth2.service.impl;

import com.example.oauth2.entity.TokenData;
import com.example.oauth2.service.OAuth2Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 授权认证平台服务
 * @Author:lubeilin
 * @Date:Created in 12:12 2020/2/15
 * @Modified By:
 */
@Service
public class OAuth2ServiceImpl implements OAuth2Service {
    @Value("${usercode}")
    private String usercode;
    @Value("${password}")
    private String password;
    @Value("${appid}")
    private String appid;
    @Value("${appkey}")
    private String appkey;
    @Value("${redirect_uri}")
    private String redirect_uri;
    @Value("${openid}")
    private String openid;
    @Value("${expire_in}")
    private long expire_in;

    /**
     * 平台登录
     * @param usercode
     * @param password
     * @return
     */
    @Override
    public boolean login(String usercode, String password) {
        if(this.usercode.equals(usercode)&&this.password.equals(password)){
            return true;
        }
        return false;
    }

    /**
     * 校验应用信息
     * @param appid
     * @param redirect_uri
     * @return
     */
    @Override
    public boolean check(String appid, String redirect_uri) {
        if(this.appid.equals(appid)&&this.redirect_uri.equals(redirect_uri)){
            return true;
        }
        return false;
    }

    /**
     * 获取授权码
     * @param appid
     * @param redirect_uri
     * @param usercode
     * @return
     */
    @Override
    public String getCode(String appid, String redirect_uri, String usercode) {
        if(check(appid,redirect_uri)){
            String temp = appid+"."+usercode;
            String code = temp+"."+temp.hashCode();//用hashcode当数据签名
            return code;
        }else{
            return "app info err";
        }
    }

    /**
     * 获取授权令牌
     * @param appid
     * @param appkey
     * @param code
     * @return
     */
    @Override
    public TokenData getToken(String appid, String appkey, String code) {
        String[] arr = code.split("\\.");
        //校验code
        if((arr[0]+"."+arr[1]).hashCode()==Integer.parseInt(arr[2])){
            //code和appid对应，appid和appkey无误
            if(this.appid.equals(appid)&&this.appkey.equals(appkey)&&appid.equals(arr[0])){
                String usercode = arr[1];
                TokenData tokenData = new TokenData();
                tokenData.setExpire_in(expire_in);
                tokenData.setOpenid(openid);
                tokenData.setAccess_token(openid+"."+openid.hashCode());
                tokenData.setRefresh_token(tokenData.getAccess_token()+"."+tokenData.getAccess_token().hashCode());
                return tokenData;
            }else{
                throw new RuntimeException("应用信息错误");
            }
        }else{
            throw new RuntimeException("code错误");
        }

    }
}
