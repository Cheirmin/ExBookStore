package com.cheirmin.interceptor;

import com.cheirmin.common.Constants;
import com.cheirmin.vo.UserVO;
import com.cheirmin.dao.ShoppingCartItemMapper;
import com.cheirmin.pojo.ShoppingCartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 购物车数量处理
 */
@Component
public class CartNumberInterceptor implements HandlerInterceptor {

    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //购物车中的数量会更改，但是在这些接口中并没有对session中的数据做修改，这里统一处理一下
        if (null != request.getSession() && null != request.getSession().getAttribute(Constants.USER_SESSION_KEY)) {
            //如果当前为登陆状态，就查询数据库并设置购物车中的数量值
            UserVO userVO = (UserVO) request.getSession().getAttribute(Constants.USER_SESSION_KEY);
            //设置购物车中的数量
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setUserId(userVO.getUserId());
            userVO.setShopCartItemCount(shoppingCartItemMapper.selectCount(shoppingCartItem));
            request.getSession().setAttribute(Constants.USER_SESSION_KEY, userVO);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
