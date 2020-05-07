package com.cheirmin.service.impl;

import com.cheirmin.common.Constants;
import com.cheirmin.common.ServiceResultEnum;
import com.cheirmin.vo.UserVO;
import com.cheirmin.mapper.AddressMapper;
import com.cheirmin.mapper.ShoppingCartItemMapper;
import com.cheirmin.mapper.UserAdminMapper;
import com.cheirmin.mapper.UserMapper;
import com.cheirmin.pojo.*;
import com.cheirmin.service.UserService;
import com.cheirmin.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

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

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    UserAdminMapper userAdminMapper;

    @Autowired
    ShoppingCartItemMapper shoppingCartItemMapper;

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
        registerUser.setNickName("暂无");

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

            Boolean aBoolean = CodecUtils.passwordConfirm(loginName.concat(password), user.getPassword());
            if (!aBoolean){
                //密码错误
                return ServiceResultEnum.LOGIN_PASSWORD_ERROR.getResult();
            }
            if ( user != null && httpSession != null) {
                if (user.getLockedFlag() == 1) {
                    return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
                }

                UserVO userVO = new UserVO();
                BeanUtil.copyProperties(user, userVO);
                //设置购物车中的数量
                ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
                shoppingCartItem.setUserId(user.getUserId());
                userVO.setShopCartItemCount(shoppingCartItemMapper.selectCount(shoppingCartItem));

                httpSession.setAttribute(Constants.USER_SESSION_KEY, userVO);
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.LOGIN_ERROR.getResult();
        }
    }

    @Override
    public Result updateUserInfo(User user, HttpSession httpSession) {
        if (userMapper.updateByPrimaryKeySelective(user)>0){
            UserVO userVO = new UserVO();

            //设置购物车中的数量
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setUserId(user.getUserId());
            userVO.setShopCartItemCount(shoppingCartItemMapper.selectCount(shoppingCartItem));

            BeanUtil.copyProperties(userMapper.selectByPrimaryKey(user.getUserId()), userVO);

            httpSession.setAttribute(Constants.USER_SESSION_KEY, userVO);
            return new Result(200,"更新成功");
        }
        return new Result(100,"更新失败");
    }

    @Override
    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        return null;
    }

    @Override
    public Result updatepassword(Map<String, String> map) {
        Long userId= Long.valueOf(map.get("userId"));
        String password1=map.get("password1");
        String password2=map.get("password2");
         User user=userMapper.selectByPrimaryKey(userId);
        if (CodecUtils.passwordConfirm(user.getUserEmail()+password1,user.getPassword())){
            String newPassword=CodecUtils.passwordBcryptEncode(user.getUserEmail(),password2);
            user.setPassword(newPassword);
            if (userMapper.updateByPrimaryKeySelective(user)>0){
                return new Result(200,"更改成功");
            }
            return new Result(100,"更改失败");
        }
        return new Result(100,"原密码错误");
    }


    @Override
    public Result getaddresssbefore(Map<String, String> map) {
        String userId= map.get("userId");
        Example example=new Example(Address.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<Address> addresses= addressMapper.selectByExample(example);
        Result<List<Address>> result=new Result(200,"ok");
        result.setData(addresses);
        return result;
    }

    @Override//设定默认地址
    public Result setdefulat(Map<String, String> map, HttpSession httpSession) {
        String userId=map.get("userId");
        String addressId=map.get("id");
        //更改address表 默认状态
        addressMapper.updateDefaultTo0();
        Address address=new Address();
        address.setId(Integer.valueOf(addressId));
        address.setIsDefulat(1);
        addressMapper.updateByPrimaryKeySelective(address);

        Address a=addressMapper.selectByPrimaryKey(addressId);
        //更新主表的状态
        User user=new User();
        user.setUserId(Long.valueOf(userId));
        user.setAddress(a.getAddress());
        userMapper.updateByPrimaryKeySelective(user);

        //更新session状态
        UserVO userVO = new UserVO();
        //设置购物车中的数量
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setUserId(user.getUserId());
        int count = shoppingCartItemMapper.selectCount(shoppingCartItem);
//        System.out.println("setdefulat--count1--"+count);
        userVO.setShopCartItemCount(count);

        BeanUtil.copyProperties(userMapper.selectByPrimaryKey(user.getUserId()), userVO);
        httpSession.setAttribute(Constants.USER_SESSION_KEY, userVO);
        return new Result(200,"设置成功");

    }

    @Override
    public Result addAddreBefore(Map<String, String> map) {
        String address=map.get("address");
        String userId=map.get("userId");
        Address a=new Address();
        a.setAddress(address);
        a.setUserId(Long.valueOf(userId));
        Example example=new Example(Address.class);
        example.createCriteria().andEqualTo("userId",userId);
        if (addressMapper.selectByExample(example).size()>=3){
            return new Result(122,"最多3个地址");
        }
        if (addressMapper.insertSelective(a)>0){
            return new Result(200,"欧了");
        }
        return new Result(122,"服务器出错");
    }

    @Override
    public Result updateAddressBefore(Map<String, String> map, HttpSession httpSession) {
        String userId=map.get("userId");
        String addressId=map.get("id");
        String addressValue=map.get("addressValue");
        //更改address表 默认状态
        Address address=new Address();
        address.setId(Integer.valueOf(addressId));
        address.setAddress(addressValue);
        addressMapper.updateByPrimaryKeySelective(address);

        Address a=addressMapper.selectByPrimaryKey(addressId);
        User user=new User();
        if (a.getIsDefulat()==1){
            //更新主表的状态
            user.setUserId(Long.valueOf(userId));
            user.setAddress(a.getAddress());
            userMapper.updateByPrimaryKeySelective(user);
        }

        //更新session状态
        UserVO userVO = new UserVO();
        //设置购物车中的数量
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setUserId(user.getUserId());
        int count = shoppingCartItemMapper.selectCount(shoppingCartItem);
        System.out.println("updateAddressBefore--count0--"+count);
        userVO.setShopCartItemCount(count);

        BeanUtil.copyProperties(userMapper.selectByPrimaryKey(user.getUserId()), userVO);
        httpSession.setAttribute(Constants.USER_SESSION_KEY, userVO);
        return new Result(200,"设置成功");

    }

    @Override
    public PageResult getCategorisPage(PageQueryUtil pageUtil) {
        Example example=new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("parentId",pageUtil.get("parentId"));
//        criteria.andEqualTo("categoryLevel",pageUtil.get("categoryLevel"));
        example.setOrderByClause("`user_id` DESC");
        RowBounds rowBounds = new RowBounds((pageUtil.getPage()-1)*pageUtil.getLimit(), pageUtil.getLimit());
        List<User> users = userMapper.selectByExampleAndRowBounds(example,rowBounds);

        int total = userMapper.selectCountByExample(example);
        PageResult pageResult = new PageResult(users, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;

    }


    @Override
    public PageResult getAdminUserPage(PageQueryUtil pageUtil) {
        Example example=new Example(UserAdmin.class);
        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("parentId",pageUtil.get("parentId"));
        criteria.andEqualTo("super_admin",0);
        example.setOrderByClause("`admin_user_id` DESC");
        RowBounds rowBounds = new RowBounds((pageUtil.getPage()-1)*pageUtil.getLimit(), pageUtil.getLimit());
        List<UserAdmin> users = userAdminMapper.selectByExampleAndRowBounds(example,rowBounds);

        int total = userAdminMapper.selectCountByExample(example);
        PageResult pageResult = new PageResult(users, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public Result setadminlock(Integer lockStatus, List<Integer> ids) {
        UserAdmin user=new UserAdmin();
        user.setLocked(lockStatus);//Integer 转byte
        for (Integer id : ids) {
            user.setUserId(Long.valueOf(id));
            if (userAdminMapper.updateByPrimaryKeySelective(user)<1){
                return ResultGenerator.genFailResult("更新失败");
            }
        }
        return ResultGenerator.genSuccessResult();
    }

    @Override
    public Result addAdminUser(Map<String, String> map) {
       String loginName=map.get("loginName");
       String password=map.get("password");
       String nickName=map.get("nickName");
       password= CodecUtils.passwordBcryptEncode(loginName,password);

       UserAdmin userAdmin=new UserAdmin();
       userAdmin.setLocked(0);
       userAdmin.setUserEmail(loginName);
       userAdmin.setPassword(password);
       userAdmin.setNick_name(nickName);
       try {
        if ( userAdminMapper.insertSelective(userAdmin)>0) {
            return ResultGenerator.genSuccessResult();
        }
       }catch (Exception e){
           return ResultGenerator.genFailResult("已存在");
       }
        return ResultGenerator.genFailResult("添加失败");
    }

    @Override
    public Result setlock(Integer lockStatus, List<Integer> ids) {
            User user=new User();
            user.setLockedFlag(Byte.valueOf(lockStatus.toString()));//Integer 转byty
        for (Integer id : ids) {
            user.setUserId(Long.valueOf(id));
            if (userMapper.updateByPrimaryKeySelective(user)<1){
                return ResultGenerator.genFailResult("更新失败");
            }
        }
            return ResultGenerator.genSuccessResult();
    }
}
