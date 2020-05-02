package com.cheirmin.service;

import com.cheirmin.vo.ShoppingCartItemVO;
import com.cheirmin.pojo.ShoppingCartItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Message:
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

    String saveCartItem(ShoppingCartItem shoppingCartItem);

    String updateCartItem(ShoppingCartItem shoppingCartItem);

    ShoppingCartItem getCartItemByCartItemId(Long cartItemId);

    Boolean deleteByCartItemId(Long cartItemId);

    List<ShoppingCartItemVO> getMyShoppingCartItems(Long userId);

}
