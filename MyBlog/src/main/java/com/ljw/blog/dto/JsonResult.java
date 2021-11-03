package com.ljw.blog.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName 用来封装请求的返回信息
 * @Description TODO
 * @DATE 2021/9/19 10:00
 */

@Data
public class JsonResult {

    /*错误码,1代表成功,0代表失败*/
    private Integer code;
    /*返回操作信息*/
    private String msg;
    /*封装返回给前端的信息*/
    private Map<String,Object> extend = new HashMap<>();

    public JsonResult() {
    }

    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResult add(String key,Object value) {
        this.extend.put(key, value);
        return this;
    }

    public JsonResult ok() {
        return new JsonResult(1,"操作成功");
    }

    public JsonResult fail(){
        return new JsonResult(0,"操作失败");
    }
}
