package com.ljw.blog.service.impl;

import com.ljw.blog.entity.User;
import com.ljw.blog.mapper.UserMapper;
import com.ljw.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @DATE 2021/9/29 10:38
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUserName(String userName){
        return userMapper.selectUserByUserName(userName);
    }

    @Override
    public void updateUserByUserName(User user) { userMapper.updateUserByUserName(user); }

    @Override
    public User getUserByUserEmail(String userEmail){
        return userMapper.selectUserByUserEmail(userEmail);
    }

    @Override
    public void insertUser(User user){
        user.setUserRegisterTime(new Date());
        userMapper.insertUser(user);
    }
}
