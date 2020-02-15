package com.example.oauth2.entity;

/**
 * @Author:lubeilin
 * @Date:Created in 11:25 2020/2/15
 * @Modified By:
 */
public class ResponseData {
    private Integer code;
    private String msg;
    private Object data;
    public ResponseData(Integer code,String msg) {
        this.msg = msg;
        this.code = code;
        data = null;
    }
    public ResponseData(Object data){
        this.data = data;
        code = 200;
        msg = "success";
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
