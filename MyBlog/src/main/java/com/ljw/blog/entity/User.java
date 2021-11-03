package com.ljw.blog.entity;

import com.ljw.blog.enums.UserRole;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/*QUES：mbg导入出错？*/
@Data
public class User {
    private Integer userId;

    @Pattern(regexp="(^[a-zA-Z0-9_-]{3,16}$)"
            ,message="用户名必须是3-16位英文或数字的组合")
    private String userName;

    @Pattern(regexp = "(^[a-zA-Z0-9_-]{6,18}$)"
            ,message = "密码必须是6-18位的英文或者数字或者_-或其组合")
    private String userPass;

    @Pattern(regexp = "(^[\\u2E80-\\u9FFF]{2,5}$)"
            ,message = "昵称必须是2-5位的中文")
    private String userNickname;

    @Pattern(regexp = "(^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$)"
            ,message = "邮箱格式不正确")
    private String userEmail;

    private String userUrl;

    private String userAvatar;

    private String userLastLoginIp;

    private Date userRegisterTime;

    private Date userLastLoginTime;

    private Integer userStatus;

    /**
     * 用户角色：admin/user
     */
    private String userRole;

    /**
     * 文章数量（不是数据库字段）
     */
    private Integer articleCount;

    public String getUserRole(){
        return userRole;
    }

}