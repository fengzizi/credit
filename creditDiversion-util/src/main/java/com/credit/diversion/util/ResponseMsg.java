package com.credit.diversion.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResponseMsg<T> {
    //状态码 1=成功 其余失败
    private int code;
    //消息内容
    private String message;
    //数据实体
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String asJson() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); //日期格式化
        return gson.toJson(this);
    }

    public static <T> ResponseMsg<T> print(int _code) {
        ResponseMsg<T> responseMsg = new ResponseMsg<T>();
        responseMsg.code = _code;
        responseMsg.message = "";
        responseMsg.data = null;
        return responseMsg;
    }

    public static <T> ResponseMsg<T> print(int _code, String _message) {
        ResponseMsg<T> responseMsg = new ResponseMsg<T>();
        responseMsg.code = _code;
        responseMsg.message = _message;
        responseMsg.data = null;
        return responseMsg;
    }

    public static <T> ResponseMsg<T> print(int _code, String _message, T _data) {
        ResponseMsg<T> responseMsg = new ResponseMsg<T>();
        responseMsg.code = _code;
        responseMsg.message = _message;
        responseMsg.data = _data;
        return responseMsg;
    }
}
