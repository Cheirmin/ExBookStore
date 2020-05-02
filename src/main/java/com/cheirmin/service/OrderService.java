package com.cheirmin.service;

import com.cheirmin.vo.OrderDetailVO;
import com.cheirmin.vo.OrderItemVO;
import com.cheirmin.vo.ShoppingCartItemVO;
import com.cheirmin.vo.UserVO;
import com.cheirmin.pojo.Order;
import com.cheirmin.util.PageQueryUtil;
import com.cheirmin.util.PageResult;

import java.util.List;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/13 19:38
 * @Version 1.0
 */
public interface OrderService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getOrdersPage(PageQueryUtil pageUtil);

    /**
     * 订单信息修改
     *
     * @param order
     * @return
     */
    String updateOrderInfo(Order order);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);

    /**
     * 出库
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);

    /**
     * 保存订单
     *
     * @param user
     * @param myShoppingCartItems
     * @return
     */
    String saveOrder(UserVO user, List<ShoppingCartItemVO> myShoppingCartItems);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
    OrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @return
     */
    Order getOrderByOrderNo(String orderNo);

    /**
     * 我的订单列表
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrders(PageQueryUtil pageUtil);

    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Long userId);

    /**
     * 确认收货
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String finishOrder(String orderNo, Long userId);

    String paySuccess(String orderNo, int payType);

    List<OrderItemVO> getOrderItems(Long id);
}
