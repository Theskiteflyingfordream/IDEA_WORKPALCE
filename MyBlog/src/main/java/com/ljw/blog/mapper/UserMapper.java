package com.ljw.blog.mapper;

import com.ljw.blog.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User selectUserByUserName(String userName);
    void updateUserByUserName(User user);
    User selectUserByUserEmail(String userEmail);
    void insertUser(User user);
    User selectUserByUserId(Integer userId);
}