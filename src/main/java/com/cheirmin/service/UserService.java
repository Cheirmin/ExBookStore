package com.cheirmin.service;

import com.cheirmin.pojo.User;
import com.cheirmin.util.PageQueryUtil;
import com.cheirmin.util.PageResult;
import com.cheirmin.util.Result;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Message:前台用户
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:38
 * @Version 1.0
 */
public interface UserService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallUsersPage(PageQueryUtil pageUtil);

    /**
     * 用户注册
     *
     * @param loginName
     * @param password
     * @return
     */
    String register(String loginName, String password);

    /**
     * 登录
     *
     * @param loginName
     * @param passwordMD5
     * @param httpSession
     * @return
     */
    String login(String loginName, String passwordMD5, HttpSession httpSession);

    /**
     * 用户信息修改并返回最新的用户信息
     *
     * @param user
     * @return
     */
    Result updateUserInfo(User user, HttpSession httpSession);

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     *
     * @param ids
     * @param lockStatus
     * @return
     */
    Boolean lockUsers(Integer[] ids, int lockStatus);

    /**
     * 改密码
     * @param map
     * @return
     */
    Result updatepassword(Map<String, String> map);

    /**
     * 获取地址
     * @param map
     * @return
     */
    Result getaddresssbefore(Map<String, String> map);

    /**
     * 设置默认地址
     * @param map
     * @param httpSession
     * @return
     */
    Result setdefulat(Map<String, String> map, HttpSession httpSession);

    /**
     * 新增地址
     * @param map
     * @return
     */
    Result addAddreBefore(Map<String, String> map);

    /**
     * 更新
     * @param map
     * @param httpSession
     * @return
     */
    Result updateAddressBefore(Map<String, String> map, HttpSession httpSession);

    /**
     * 获取
     * @param pageUtil
     * @return
     */
    PageResult getCategorisPage(PageQueryUtil pageUtil);

    /**
     * 锁定
     * @param lockStatus
     * @param ids
     * @return
     */
    Result setlock(Integer lockStatus, List<Integer> ids);

    /**
     * 管理员页面
     * @param pageUtil
     * @return
     */
    PageResult getAdminUserPage(PageQueryUtil pageUtil);

    /**
     * 锁定管理员
     * @param lockStatus
     * @param ids
     * @return
     */
    Result setadminlock(Integer lockStatus, List<Integer> ids);

    /**
     * 新增管理员
     * @param map
     * @return
     */
    Result addAdminUser(Map<String, String> map);
}
