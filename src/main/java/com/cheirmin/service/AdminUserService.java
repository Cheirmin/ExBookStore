package com.cheirmin.service;

import com.cheirmin.pojo.AdminUser;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:34
 * @Version 1.0
 */
public interface AdminUserService {
    //获取一个AdminUser对象
    AdminUser getUserDetailById(Integer loginUserId);
    //修改密码
    boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);
}
