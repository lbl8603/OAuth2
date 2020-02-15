package com.example.oauth2.service;

import com.example.oauth2.entity.TokenData;
import org.springframework.stereotype.Service;

/**
 * 授权认证平台
 * @Author:lubeilin
 * @Date:Created in 11:43 2020/2/15
 * @Modified By:
 */
public interface OAuth2Service {
    boolean login(String usercode,String password);
    boolean check(String appid,String redirect_uri);
    String getCode(String appid,String redirect_uri,String usercode);
    TokenData getToken(String appid,String appkey,String code);
}
