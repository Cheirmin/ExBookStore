package com.cheirmin.service.impl;

import com.cheirmin.common.ExBookStoreException;
import com.cheirmin.common.OrderStatusEnum;
import com.cheirmin.common.ServiceResultEnum;
import com.cheirmin.vo.*;
import com.cheirmin.mapper.BookMapper;
import com.cheirmin.mapper.OrderItemMapper;
import com.cheirmin.mapper.OrderMapper;
import com.cheirmin.mapper.ShoppingCartItemMapper;
import com.cheirmin.pojo.*;
import com.cheirmin.service.OrderService;
import com.cheirmin.util.BeanUtil;
import com.cheirmin.util.NumberUtil;
import com.cheirmin.util.PageQueryUtil;
import com.cheirmin.util.PageResult;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;
    @Autowired
    private BookMapper bookMapper;

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
        //订单对应的数据库条目
        List<Long> itemIdList = myShoppingCartItems.stream().map(ShoppingCartItemVO::getCartItemId).collect(Collectors.toList());
        //对应的书本id
        List<Long> bookIds = myShoppingCartItems.stream().map(ShoppingCartItemVO::getBookId).collect(Collectors.toList());
        //数据库中对应的数据
        List<Book> books = bookMapper.selectByIdList(bookIds);

        Map<Long, Book> bookMap = books.stream().collect(Collectors.toMap(Book::getBookId, Function.identity(), (entity1, entity2) -> entity1));

        //判断商品库存
        for (ShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
            //查出的商品中不存在购物车中的这条关联商品数据，直接返回错误提醒
            if (!bookMap.containsKey(shoppingCartItemVO.getBookId())) {
                ExBookStoreException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
            }
            //存在数量大于库存的情况，直接返回错误提醒
            if (shoppingCartItemVO.getBookCount() > bookMap.get(shoppingCartItemVO.getBookId()).getStockNum()) {
                ExBookStoreException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
            }
        }

        //删除购物项
        if (!CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(books) && !CollectionUtils.isEmpty(books)) {
            Example example = new Example(ShoppingCartItem.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("cartItemId",itemIdList);

            if (shoppingCartItemMapper.deleteByExample(example) > 0) {
                //修改库存
                List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(myShoppingCartItems, StockNumDTO.class);

                int updateStockNumResult = bookMapper.updateStockNum(stockNumDTOS);

                if (updateStockNumResult < 1) {
                    ExBookStoreException.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
                }
                //生成订单号
                String orderNo = NumberUtil.genOrderNo();

                BigDecimal priceTotal = new BigDecimal(0);
                //保存订单
                Order order = new Order();
                order.setOrderNo(orderNo);
                order.setUserId(user.getUserId());
                order.setUserAddress(user.getAddress());
                //总价
                for (ShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
                    priceTotal = priceTotal.add(shoppingCartItemVO.getSellingPrice().multiply(BigDecimal.valueOf(shoppingCartItemVO.getBookCount())));
                }
                if (priceTotal.compareTo(BigDecimal.valueOf(1)) ==-1) {
                    ExBookStoreException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                order.setTotalPrice(priceTotal);
                //todo 订单body字段，用来作为生成支付单描述信息，暂时未接入第三方支付接口，故该字段暂时设为空字符串
                String extraInfo = "";
                order.setExtraInfo(extraInfo);
                //生成订单项并保存订单项纪录
                if (orderMapper.insertSelective(order) > 0) {
                    //获取订单id
                    Example example1 = new Example(Order.class);
                    Example.Criteria criteria1 = example1.createCriteria();
                    criteria1.andEqualTo("orderNo",orderNo);
                    order.setOrderId(orderMapper.selectOneByExample(example1).getOrderId());

                    //生成所有的订单项快照，并保存至数据库
                    List<OrderItem> orderItems = new ArrayList<>();
                    for (ShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
                        OrderItem orderItem = new OrderItem();
                        //使用BeanUtil工具类将ShoppingCartItemVO中的属性复制到OrderItem对象中
                        BeanUtil.copyProperties(shoppingCartItemVO, orderItem);
                        orderItem.setOrderId(order.getOrderId());
                        orderItems.add(orderItem);
                    }
                    //保存至数据库
                    if (orderItemMapper.insertBatch(orderItems) > 0) {
                        //所有操作成功后，将订单号返回，以供Controller方法跳转到订单详情
                        return orderNo;
                    }
                    ExBookStoreException.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                ExBookStoreException.fail(ServiceResultEnum.DB_ERROR.getResult());
            }
            ExBookStoreException.fail(ServiceResultEnum.DB_ERROR.getResult());
        }
        ExBookStoreException.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        return ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult();
    }

    @Override
    public OrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderNo",orderNo);

        Order order = orderMapper.selectOneByExample(example);
        if (order != null) {
            //todo 验证是否是当前userId下的订单，否则报错
            Example example1 = new Example(OrderItem.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("orderId",order.getOrderId());

            List<OrderItem> orderItems = orderItemMapper.selectByExample(example1);
            //获取订单项数据
            if (!CollectionUtils.isEmpty(orderItems)) {
                List<OrderItemVO> orderItemVOS = BeanUtil.copyList(orderItems, OrderItemVO.class);
                OrderDetailVO orderDetailVO = new OrderDetailVO();
                BeanUtil.copyProperties(order, orderDetailVO);
                orderDetailVO.setOrderStatusString(OrderStatusEnum.getOrderStatusEnumByStatus(orderDetailVO.getOrderStatus()).getName());
//                orderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(orderDetailVO.getPayType()).getName());
                orderDetailVO.setOrderItemVOS(orderItemVOS);
                return orderDetailVO;
            }
        }
        return null;
    }

    @Override
    public Order getOrderByOrderNo(String orderNo) {
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderNo",orderNo);

        return orderMapper.selectOneByExample(example);
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
                        //将OrderItem对象列表转换成OrderItemVO对象列表
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
    public String payResult(String orderNo,int status) {
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderNo",orderNo);

        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setPayStatus((byte) status);
        if (status == 1){
            order.setOrderStatus((byte) 1);
        }

        return orderMapper.updateByExampleSelective(order, example)>0?"true":"false";
    }

    @Override
    public List<OrderItemVO> getOrderItems(Long id) {
        return null;
    }
}
