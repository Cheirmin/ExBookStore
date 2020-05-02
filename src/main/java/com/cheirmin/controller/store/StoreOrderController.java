package com.cheirmin.controller.store;

import com.cheirmin.common.Constants;
import com.cheirmin.common.ExBookStoreException;
import com.cheirmin.common.ServiceResultEnum;
import com.cheirmin.vo.OrderDetailVO;
import com.cheirmin.vo.ShoppingCartItemVO;
import com.cheirmin.vo.UserVO;
import com.cheirmin.pojo.Order;
import com.cheirmin.service.OrderService;
import com.cheirmin.service.ShoppingCartService;
import com.cheirmin.util.PageQueryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Message:订单管理控制器
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:12
 * @Version 1.0
 */
@Controller
public class StoreOrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private ShoppingCartService shoppingCartService;

    @GetMapping("/orders")
    public String orderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.USER_SESSION_KEY);
        params.put("userId", user.getUserId());
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装我的订单数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("orderPageResult", orderService.getMyOrders(pageUtil));
        request.setAttribute("path", "orders");
        return "store/my-orders";
    }

    @GetMapping("/orders/{orderNo}")
    public String orderDetailPage(HttpServletRequest request, @PathVariable("orderNo") String orderNo, HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.USER_SESSION_KEY);
        OrderDetailVO orderDetailVO = orderService.getOrderDetailByOrderNo(orderNo, user.getUserId());
        if (orderDetailVO == null) {
            return "error/error_5xx";
        }

        request.setAttribute("orderDetailVO", orderDetailVO);
        return "store/order-detail";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.USER_SESSION_KEY);
        List<ShoppingCartItemVO> myShoppingCartItems = shoppingCartService.getMyShoppingCartItems(user.getUserId());
        if (StringUtils.isEmpty(user.getAddress().trim())) {
            //无收货地址
           ExBookStoreException.fail(ServiceResultEnum.NULL_ADDRESS_ERROR.getResult());
        }
        if (CollectionUtils.isEmpty(myShoppingCartItems)) {
            //购物车中无数据则跳转至错误页
            ExBookStoreException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        }
        //保存订单并返回订单号
        String saveOrderResult = orderService.saveOrder(user, myShoppingCartItems);
        //跳转到订单详情页
        return "redirect:/orders/" + saveOrderResult;
    }

    @GetMapping("/selectPayType")
    public String selectPayType(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.USER_SESSION_KEY);
        Order order = orderService.getOrderByOrderNo(orderNo);
        //todo 判断订单userId
        //todo 判断订单状态
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", order.getTotalPrice());
        return "store/pay-select";
    }

    @GetMapping("/payPage")
    public String payOrder(HttpServletRequest request, @RequestParam("orderNo") String orderNo, HttpSession httpSession) {
        UserVO user = (UserVO) httpSession.getAttribute(Constants.USER_SESSION_KEY);
        Order order = orderService.getOrderByOrderNo(orderNo);
        //todo 判断订单userId
        //todo 判断订单状态
        request.setAttribute("orderNo", orderNo);
        request.setAttribute("totalPrice", order.getTotalPrice());
        return "store/alipay";
    }
}
