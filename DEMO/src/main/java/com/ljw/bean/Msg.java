package com.ljw.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: Msg
 * Description:通用的返回页面的类
 * date: 2021/8/1 22:53
 *
 * @author: 19228
 * @version:
 * @since JDK 1.8
 */
public class Msg {
    //状态码
    private int code;
    //提示信息
    private String msg;

    //用户返回给浏览器的信息
    private Map<String,Object> extend = new HashMap<>();

    public static Msg success(){
        Msg result = new Msg();
        result.setCode(100);
        result.setMsg("处理成功!");
        return result;
    }

    public static Msg fail(){
        Msg result = new Msg();
        result.setCode(200);
        result.setMsg("处理失败!");
        return result;
    }

    public Msg add(String key,Object value){
        this.getExtend().put(key,value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
