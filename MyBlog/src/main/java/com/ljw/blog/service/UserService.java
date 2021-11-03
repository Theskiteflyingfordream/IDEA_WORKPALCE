package com.ljw.blog.service;

import com.ljw.blog.entity.User;

/**
 * @ClassName UserService
 * @Description TODO
 * @DATE 2021/9/29 10:37
 */
public interface UserService {
    User getUserByUserName(String userName);
    void updateUserByUserName(User user);
    User getUserByUserEmail(String userEmail);
    void insertUser(User user);
}
