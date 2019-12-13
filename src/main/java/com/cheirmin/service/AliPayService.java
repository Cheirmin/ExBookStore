package com.cheirmin.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.cheirmin.config.AliPayConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

/**
 * @author 吴星辰
 * @createTime 2019.12.09.15:45
 */
@Service
public class AliPayService implements  InitializingBean {

    @Autowired
    AliPayConfig aliPayConfig;



    public String genPayPic() {
        return null;
    }


    public void updateById(String id) {
        System.out.println("修改订单 状态 ！");
    }


    public String genPage() {
        Random r=new Random();
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.GATEWAY_URL
                , aliPayConfig.APP_ID,aliPayConfig.APP_PRIVATE_KEY, aliPayConfig.FORMAT
                , aliPayConfig.CHARSET, aliPayConfig.ALIPAY_PUBLIC_KEY, aliPayConfig.SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(aliPayConfig.RETURN_URL);
        request.setNotifyUrl(aliPayConfig.NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        String out_trade_no = UUID.randomUUID().toString();
        //付款金额，必填
        String total_amount =Integer.toString(239999);
        //订单名称，必填
        String subject ="王国纪元三爹 金绝号 108张机票";
        //商品描述，可空
        String body = "王国纪元三爹 金绝号 108张机票 随意飞 飞新区直接当老大";
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }


    public String refund(String outTradeNo) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.GATEWAY_URL
                , aliPayConfig.APP_ID, aliPayConfig.APP_PRIVATE_KEY, "json"
                , aliPayConfig.CHARSET, aliPayConfig.ALIPAY_PUBLIC_KEY, aliPayConfig.SIGN_TYPE);
        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        //商户订单号，必填
        String out_trade_no = new String(outTradeNo);
        //需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = "239999.00";
        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        String out_request_no = new String(UUID.randomUUID().toString());

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"refund_amount\":\""+ refund_amount +"\","
                + "\"out_request_no\":\""+ out_request_no +"\"}");
        //请求
        return alipayClient.execute(alipayRequest).getBody();
        //输出
    }


    public void afterPropertiesSet() throws Exception {

    }
}
