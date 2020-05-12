package com.cheirmin.service;

import com.cheirmin.vo.ShoppingCartItemVO;
import com.cheirmin.pojo.ShoppingCartItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Message:购物车条目
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:38
 * @Version 1.0
 */
@Repository
public interface ShoppingCartService {

//    String putCart(Model model,Integer bookCount, Long bookId, HttpSession session);
//
//    String selectCart(Model model,HttpSession session);
//
//    String deleteBooks(Long bookId,HttpSession session);
//
//    String clear(HttpSession session);
//
//    String orderConfirm(Model model,HttpSession session);

    /**
     * 保存购物车条目
     * @param shoppingCartItem
     * @return
     */
    String saveCartItem(ShoppingCartItem shoppingCartItem);

    /**
     * 更新
     * @param shoppingCartItem
     * @return
     */
    String updateCartItem(ShoppingCartItem shoppingCartItem);

    /**
     * 获取
     * @param cartItemId
     * @return
     */
    ShoppingCartItem getCartItemByCartItemId(Long cartItemId);

    /**
     * 删除
     * @param cartItemId
     * @return
     */
    Boolean deleteByCartItemId(Long cartItemId);

    /**
     * 根据用户id获取购物车条目
     * @param userId
     * @return
     */
    List<ShoppingCartItemVO> getMyShoppingCartItems(Long userId);

}
