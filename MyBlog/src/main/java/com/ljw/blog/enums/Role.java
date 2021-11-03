package com.ljw.blog.enums;

public enum Role {
    OWNER("博主",1),
    VISITOR("其他用户",0);

    private String Msg;

    public String getMsg() {
        return Msg;
    }

    public Integer getValue() {
        return value;
    }

    private Integer value;

    Role(String msg, Integer value) {
        Msg = msg;
        this.value = value;
    }


}
