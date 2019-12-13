package com.cheirmin.service.impl;

import com.cheirmin.dao.AdminUserMapper;
import com.cheirmin.service.AdminUserService;

import javax.annotation.Resource;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:35
 * @Version 1.0
 */
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

}
