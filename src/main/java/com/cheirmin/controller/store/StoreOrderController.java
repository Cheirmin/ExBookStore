package com.cheirmin.controller.store;

import com.cheirmin.common.Constants;
import com.cheirmin.controller.vo.UserVO;
import com.cheirmin.service.OrderService;
import com.cheirmin.util.PageQueryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
}
