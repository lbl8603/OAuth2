package com.example.oauth2.controller;

import com.example.oauth2.entity.ResponseData;
import com.example.oauth2.service.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author:lubeilin
 * @Date:Created in 11:24 2020/2/15
 * @Modified By:
 */
@Controller
@RequestMapping("/demo")
public class OAuth2Controller {
    @Autowired
    private OAuth2Service service;

    /**
     * 模拟其他系统回调页
     * @return
     */
    @RequestMapping("/other_sys/index")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    /**
     * 其他系统服务器端请求令牌的api
     * @param code
     * @param appid
     * @param appkey
     * @return
     */
    @ResponseBody
    @RequestMapping("/oauth2/token")
    public ResponseData token(String code,String appid,String appkey){
        return new ResponseData(service.getToken(appid,appkey,code));
    }

    /**
     * 用户确认授权
     * @param request
     * @return
     */
    @RequestMapping("/oauth2/ackAuth")
    public String ackAuth(HttpServletRequest request){
        HttpSession session=request.getSession();
        String usercode = session.getAttribute("usercode").toString();
        String appid = session.getAttribute("appid").toString();
        String redirect_uri = session.getAttribute("redirect_uri").toString();
        String code = service.getCode(appid,redirect_uri,usercode);
        return "redirect:"+redirect_uri+"?code="+code;
    }

    /**
     * 授权认证页面
     * @param appid
     * @param redirect_uri
     * @param request
     * @return
     */
    @RequestMapping("/oauth2/authorize")
    public ModelAndView authorize(String appid, String redirect_uri, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();

        if(!service.check(appid,redirect_uri)){
            modelAndView.setViewName("error");
            modelAndView.addObject("err", "应用信息错误");
            return modelAndView;
        }
        HttpSession session=request.getSession();
        //缓存应用信息
        session.setAttribute("appid", appid);
        session.setAttribute("redirect_uri", redirect_uri);
        Object obj = session.getAttribute("usercode");
        if(obj==null){
            modelAndView.setViewName("login");
            return modelAndView;
        }
        modelAndView.addObject("usercode", appid);
        modelAndView.setViewName("authorize");

        return modelAndView;
    }

    /**
     * 未登录时，需要到授权平台的登录页
     * @param usercode
     * @param password
     * @param appid
     * @param redirect_uri
     * @param request
     * @return
     */
    @RequestMapping("/oauth2/login")
    public ModelAndView login(String usercode, String password,String appid, String redirect_uri, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        if(service.login(usercode,password)){
            HttpSession session=request.getSession();
            session.setAttribute("usercode",usercode);
            return authorize(appid,redirect_uri,request);
        }else {
            modelAndView.setViewName("error");
            modelAndView.addObject("err", "账号或密码错误");
            return modelAndView;
        }
    }
}
