package com.cheirmin.controller.store;

import com.cheirmin.common.Constants;
import com.cheirmin.common.ServiceResultEnum;
import com.cheirmin.vo.ShoppingCartItemVO;
import com.cheirmin.vo.UserVO;
import com.cheirmin.pojo.ShoppingCartItem;
import com.cheirmin.service.ShoppingCartService;
import com.cheirmin.util.Result;
import com.cheirmin.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Message:购物车控制器
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:13
 * @Version 1.0
 */
@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/shop-cart")
    public String cartListPage(HttpServletRequest request,HttpSession session){
        UserVO userVO = (UserVO) session.getAttribute(Constants.USER_SESSION_KEY);
        int booksTotal = 0;
        BigDecimal priceTotal = new BigDecimal(0);
        List<ShoppingCartItemVO> myShoppingCartItems = shoppingCartService.getMyShoppingCartItems(userVO.getUserId());
        if (!CollectionUtils.isEmpty(myShoppingCartItems)){
            //订单项总数
            booksTotal = myShoppingCartItems.stream().mapToInt(ShoppingCartItemVO::getBookCount).sum();

            if (booksTotal < 1){
                return "error/500";
            }
            //总价
            for (ShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems){
                priceTotal = priceTotal.add(shoppingCartItemVO.getSellingPrice().multiply(BigDecimal.valueOf(shoppingCartItemVO.getBookCount())));
            }
            if (priceTotal.compareTo(BigDecimal.valueOf(1)) == -1){
                return "error/500";
            }
        }
        request.setAttribute("booksTotal",booksTotal);
        request.setAttribute("priceTotal",priceTotal);
        request.setAttribute("myShoppingCartItems",myShoppingCartItems);
        return "store/cart";
    }

    @PostMapping("/shop-cart")
    @ResponseBody
    public Result saveShoppingCartItem(@RequestBody ShoppingCartItem shoppingCartItem,HttpSession session){
        UserVO userVO = (UserVO) session.getAttribute(Constants.USER_SESSION_KEY);
        shoppingCartItem.setUserId(userVO.getUserId());

        //判断购物车中是否已经存在该书籍
        List<ShoppingCartItemVO> myShoppingCartItems = shoppingCartService.getMyShoppingCartItems(userVO.getUserId());
        for (ShoppingCartItemVO s :myShoppingCartItems){
            if(s.getBookId().equals(shoppingCartItem.getBookId())){
                //判断是否超过5本
                if (s.getBookCount()>=5){
                    return ResultGenerator.genFailResult("可够数量超过上限");
                }else{
                    //购物车内数量进行加一操作
                    shoppingCartItem.setBookCount(s.getBookCount()+1);
                }
            }
        }

        String saveResult = shoppingCartService.saveCartItem(shoppingCartItem);
        //添加成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)){
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult(saveResult);
    }

    @PutMapping("/shop-cart")
    @ResponseBody
    public Result updateShoppingCartItem(@RequestBody ShoppingCartItem shoppingCartItem,HttpSession session){
        UserVO userVO = (UserVO) session.getAttribute(Constants.USER_SESSION_KEY);
        shoppingCartItem.setUserId(userVO.getUserId());
        //判断数量
        String saveResult = shoppingCartService.updateCartItem(shoppingCartItem);
        //修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)){
            return ResultGenerator.genSuccessResult();
        }
        //修改失败
        return ResultGenerator.genFailResult(saveResult);
    }

    @DeleteMapping("/shop-cart/{shoppingCartItemId}")
    @ResponseBody
    public Result deleteShoppingCartItem(@PathVariable("shoppingCartItemId") Long shoppingCartItemId,HttpSession session){
        UserVO userVO = (UserVO) session.getAttribute(Constants.USER_SESSION_KEY);
        Boolean deleteResult = shoppingCartService.deleteByCartItemId(shoppingCartItemId);
        //删除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @GetMapping("/shop-cart/settle")
    public String settlePage(HttpServletRequest request,HttpSession session){
        BigDecimal priceTotal = new BigDecimal(0);
        UserVO userVO = (UserVO) session.getAttribute(Constants.USER_SESSION_KEY);
        List<ShoppingCartItemVO> myShoppingCartItems = shoppingCartService.getMyShoppingCartItems(userVO.getUserId());
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //无数据则不跳转至结算页
            return "/shop-cart";
        }else {
            //总价
            for (ShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems){
                priceTotal = priceTotal.add(shoppingCartItemVO.getSellingPrice().multiply(BigDecimal.valueOf(shoppingCartItemVO.getBookCount())));
            }
            if (priceTotal.compareTo(BigDecimal.valueOf(1)) == -1){
                return "error/500";
            }
        }
        request.setAttribute("priceTotal",priceTotal);
        request.setAttribute("myShoppingCartItems",myShoppingCartItems);
        return "store/order-settle";
    }
}
