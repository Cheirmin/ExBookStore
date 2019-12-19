package com.cheirmin.service.impl;

import com.cheirmin.common.Constants;
import com.cheirmin.common.ServiceResultEnum;
import com.cheirmin.controller.vo.UserVO;
import com.cheirmin.dao.UserMapper;
import com.cheirmin.pojo.User;
import com.cheirmin.service.UserService;
import com.cheirmin.util.BeanUtil;
import com.cheirmin.util.CodecUtils;
import com.cheirmin.util.PageQueryUtil;
import com.cheirmin.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 20:09
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResult getNewBeeMallUsersPage(PageQueryUtil pageUtil) {
        return null;
    }

    @Override
    public String register(String email, String password) {

        Example example=new Example(User.class);
        example.createCriteria().andEqualTo("userEmail",email);
        if (userMapper.selectOneByExample(example) != null) {
            return ServiceResultEnum.SAME_REGISTER_EMAIL_EXIST.getResult();
        }

        //加密密码
        String passwordCode = CodecUtils.passwordBcryptEncode(email,password);

        User registerUser = new User();
        registerUser.setUserEmail(email);
        registerUser.setPassword(passwordCode);
        registerUser.setNickName(email);

        if (userMapper.insertSelective(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String login(String loginName, String password, HttpSession httpSession) {
        Example example=new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userEmail",loginName);

        List<User> users = userMapper.selectByExample(example);

        if (users.size()<=0) {
            return ServiceResultEnum.LOGIN_ERROR.getResult();
        }else {
            User user = users.get(0);

            System.out.println("password--" + user.getPassword());

            Boolean aBoolean = CodecUtils.passwordConfirm(loginName.concat(password), user.getPassword());
            System.out.println(aBoolean?"--登录成功--":"--登录失败--");
            if ( user != null && httpSession != null) {
                if (user.getLockedFlag() == 1) {
                    return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
                }
                //昵称太长 影响页面展示
                if (user.getNickName() != null && user.getNickName().length() > 7) {
                    String tempNickName = user.getNickName().substring(0, 7) + "..";
                    user.setNickName(tempNickName);
                }

                UserVO userVO = new UserVO();
                //设置购物车中的数量
                userVO.setShopCartItemCount(2);
                BeanUtil.copyProperties(user, userVO);

                httpSession.setAttribute(Constants.USER_SESSION_KEY, userVO);
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.LOGIN_ERROR.getResult();
        }
    }

    @Override
    public UserVO updateUserInfo(User user, HttpSession httpSession) {
        return null;
    }

    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        return null;
    }
}
