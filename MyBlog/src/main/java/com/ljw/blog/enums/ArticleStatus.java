package com.ljw.blog.enums;

/**
 * @ClassName ArticleStatus
 * @Description 用于描述Article的状态,已发布或者草稿
 * @DATE 2021/9/18 11:25
 */
public enum ArticleStatus {
    Publish(1,"已发布"),
    DRAFT(0,"草稿");

    private Integer value;
    private String message;

    private ArticleStatus(Integer value,String message){
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

}
