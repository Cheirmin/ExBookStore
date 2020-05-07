package com.cheirmin.controller.common;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.cheirmin.config.AliPayConfig;
import com.cheirmin.service.OrderService;
import com.cheirmin.service.impl.AliPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Filename
 * @auther
 * @data 2019/12/9 19:37;
 * @Descripion 支付控制器
 * @Version 1.1.1
 * @Function
 * @History
 */
@Controller
public class PayControl {

    @Autowired
    AliPayServiceImpl aliPayService;

    @Resource
    private OrderService orderService;

    @Autowired
    AliPayConfig aliPayConfig;

    @RequestMapping("payTest")
   public void testPay(HttpServletResponse httpResponse){
        String form = aliPayService.orderPay("123", BigDecimal.valueOf(132));
        httpResponse.setContentType("text/html;charset=" + aliPayConfig.CHARSET);
        try{
            // 直接将完整的表单html输出到页面
            httpResponse.getWriter().write(form);
            httpResponse.getWriter().flush();
            httpResponse.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
   }

    @GetMapping("/returnUrl")
    public String returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
        System.out.println("=================================同步回调=====================================");

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name =  iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        // 调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params
                ,aliPayConfig.ALIPAY_PUBLIC_KEY, aliPayConfig.CHARSET, aliPayConfig.SIGN_TYPE);

        // 商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

        // 付款金额
        String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
        System.out.println("商户订单号=" + out_trade_no);
        System.out.println("支付宝交易号=" + trade_no);
        System.out.println("付款金额=" + total_amount);

        //验证签名通过
        if(signVerified) {
            //支付成功，修复支付状态
            orderService.payResult(out_trade_no,1);
        }else {
            //支付失败，修复支付状态
            orderService.payResult(out_trade_no,-1);
        }
        //跳转订单列表页面

         return "forward:orders/"+out_trade_no;
    }

    @GetMapping("refund")
    @ResponseBody
    public void test_trade_refund(String outTradeNo,HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        try{
            PrintWriter out = response.getWriter();
            String result = aliPayService.refund(outTradeNo);
            //以下写自己的订单退款代码
            out.println(result);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
