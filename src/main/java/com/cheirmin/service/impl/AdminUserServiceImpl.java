package com.cheirmin.service.impl;

import com.cheirmin.mapper.AdminUserMapper;
import com.cheirmin.pojo.AdminUser;
import com.cheirmin.service.AdminUserService;
import com.cheirmin.util.CodecUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:35
 * @Version 1.0
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser getUserDetailById(Integer loginUserId){
        return adminUserMapper.selectByPrimaryKey(loginUserId);
    }

    @Override
    public boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            Boolean aBoolean = CodecUtils.passwordConfirm(adminUser.getLoginUserName().concat(originalPassword), adminUser.getLoginPassword());
            //比较原密码是否正确
            if (aBoolean) {
                String newPasswordCode = CodecUtils.passwordBcryptEncode(adminUser.getLoginUserName(),newPassword);
                //设置新密码并修改
                adminUser.setLoginPassword(newPasswordCode);
                if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                    //修改成功则返回true
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateAdmin(Integer loginUserId, String loginAdminUser, String loginAdminNickName) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);

        if (adminUser != null) {
            adminUser.setLoginUserName(loginAdminUser);
            adminUser.setNickName(loginAdminNickName);
            if ( adminUserMapper.updateByPrimaryKeySelective(adminUser)> 0) {
                //修改成功则返回true
                return true;
            }
        }
        return false;
    }

    @Override
    public AdminUser findAdminByUsername(String username) {
        Example example = new Example(AdminUser.class);
        example.createCriteria().andEqualTo("loginUserName",username);

        return adminUserMapper.selectOneByExample(example);
    }

}
