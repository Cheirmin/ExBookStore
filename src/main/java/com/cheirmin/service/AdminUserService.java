package com.cheirmin.service;

import com.cheirmin.pojo.AdminUser;

/**
 * @Message:管理员
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:34
 * @Version 1.0
 */
public interface AdminUserService {
    /**
     * 获取一个AdminUser对象
     * @param loginUserId
     * @return
     */
    AdminUser getUserDetailById(Integer loginUserId);

    /**
     * 修改密码
     * @param loginUserId
     * @param originalPassword
     * @param newPassword
     * @return
     */
    boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);

    /**
     * 管理员登陆
     * @param username
     * @return
     */
    AdminUser findAdminByUsername(String username);
}
