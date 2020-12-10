package com.gxcy.entity;

public class ResultBean<T> {

    //返回码（00-成功，01-失败）
    private String code;

    //返回描述信息（SUCCESS、FAIL、PROCESSING、UNKNOW）
    private String message;

    //返回数据
    private T info;

    //口令
    private String token;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private ResultBean() {

    }

    public static ResultBean error(String message) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode("01");
        resultBean.setMessage(message);
        return resultBean;
    }
    public static<T> ResultBean error(String message, T info) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode("01");
        resultBean.setMessage(message);
        resultBean.setInfo(info);
        return resultBean;
    }

    public static ResultBean success(String message) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode("00");
        resultBean.setMessage(message);
        return resultBean;
    }

    public static<T> ResultBean success(String message, T info) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode("00");
        resultBean.setMessage(message);
        resultBean.setInfo(info);
        return resultBean;
    }
    public static ResultBean success(String message, Object info, String token) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode("00");
        resultBean.setMessage(message);
        resultBean.setInfo(info);
        resultBean.setToken(token);
        return resultBean;
    }
}
