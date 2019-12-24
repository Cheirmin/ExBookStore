package com.cheirmin.service.impl;

import com.cheirmin.common.OrderStatusEnum;
import com.cheirmin.controller.vo.*;
import com.cheirmin.dao.OrderItemMapper;
import com.cheirmin.dao.OrderMapper;
import com.cheirmin.pojo.Order;
import com.cheirmin.pojo.OrderItem;
import com.cheirmin.service.OrderService;
import com.cheirmin.util.BeanUtil;
import com.cheirmin.util.PageQueryUtil;
import com.cheirmin.util.PageResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @Message:
 * @Author：Cheirmin
 * @Date: 2019/12/24 12:09
 * @Version 1.0
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderMapper orderMapper;

    @Resource
    OrderItemMapper orderItemMapper;

    @Override
    public PageResult getOrdersPage(PageQueryUtil pageUtil) {
        return null;
    }

    @Override
    public String updateOrderInfo(Order order) {
        return null;
    }

    @Override
    public String checkDone(Long[] ids) {
        return null;
    }

    @Override
    public String checkOut(Long[] ids) {
        return null;
    }

    @Override
    public String closeOrder(Long[] ids) {
        return null;
    }

    @Override
    public String saveOrder(UserVO user, List<ShoppingCartItemVO> myShoppingCartItems) {
        return null;
    }

    @Override
    public OrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        return null;
    }

    @Override
    public Order getOrderByOrderNo(String orderNo) {
        return null;
    }

    @Override
    public PageResult getMyOrders(PageQueryUtil pageUtil) {
        int total = orderMapper.selectCountByExample(new Example(Order.class));

        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",pageUtil.get("userId"));
        
        RowBounds rowBounds = new RowBounds((pageUtil.getPage()-1)*pageUtil.getLimit(), pageUtil.getLimit());
        List<Order> orders = orderMapper.selectByExampleAndRowBounds(example,rowBounds);

        List<OrderListVO> orderListVOS = new ArrayList<>();
        if (total > 0) {
            //数据转换 将实体类转成vo
            orderListVOS = BeanUtil.copyList(orders, OrderListVO.class);
            //设置订单状态中文显示值
            for (OrderListVO orderListVO : orderListVOS) {
                orderListVO.setOrderStatusString(OrderStatusEnum.getOrderStatusEnumByStatus(orderListVO.getOrderStatus()).getName());
            }
            List<Long> orderIds = orders.stream().map(Order::getOrderId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orderIds)) {
                Example example1 = new Example(OrderItem.class);
                Example.Criteria criteria1 = example1.createCriteria();
                criteria1.andIn("orderId",orderIds);

                List<OrderItem> orderItems = orderItemMapper.selectByExample(example1);
                Map<Long, List<OrderItem>> itemByOrderIdMap = orderItems.stream().collect(groupingBy(OrderItem::getOrderId));
                for (OrderListVO orderListVO : orderListVOS) {
                    //封装每个订单列表对象的订单项数据
                    if (itemByOrderIdMap.containsKey(orderListVO.getOrderId())) {
                        List<OrderItem> orderItemListTemp = itemByOrderIdMap.get(orderListVO.getOrderId());
                        //将NewBeeMallOrderItem对象列表转换成NewBeeMallOrderItemVO对象列表
                        List<OrderItemVO> orderItemVos = BeanUtil.copyList(orderItemListTemp, OrderItemVO.class);
                        orderListVO.setOrderItemVOS(orderItemVos);
                    }
                }
            }
        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String cancelOrder(String orderNo, Long userId) {
        return null;
    }

    @Override
    public String finishOrder(String orderNo, Long userId) {
        return null;
    }

    @Override
    public String paySuccess(String orderNo, int payType) {
        return null;
    }

    @Override
    public List<OrderItemVO> getOrderItems(Long id) {
        return null;
    }
}
