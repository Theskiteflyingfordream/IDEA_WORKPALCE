package com.ljw.blog.enums;

public enum UserRole {
    ADMIN("admin","管理员"),
    USER("user","普通用户");

    private String value;
    private String description;

    private UserRole(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
