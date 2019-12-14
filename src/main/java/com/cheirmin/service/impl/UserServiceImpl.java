package com.cheirmin.service.impl;

import com.cheirmin.dao.UserMapper;
import com.cheirmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 20:09
 * @Version 1.0
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
}
