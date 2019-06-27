package com.dayuanit.movie.handler;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.dayuanit.movie.dto.ResponseDTO;
import com.dayuanit.movie.entry.Film;
import com.dayuanit.movie.entry.Order;
import com.dayuanit.movie.exception.BizException;
import com.dayuanit.movie.service.OrderService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class AliPayHandler implements InitializingBean {

    @Value("${alipay.appid}")
    private String appid;

    @Value("${alipay.trade.pay.url}")
    private String payUrl;

    @Value("${alipay.merchant.private.key}")
    private String merchantPrivateKey;

    @Value("${alipay.public.key}")
    private String aliPublicKey;

    @Value("${alipay.return.url}")
    private String returnUrl;

    @Value("${alipay.notify.url}")
    private String notifyUrl;

    @Value("${alipay.body}")
    private String body;

    @Autowired
    private OrderService orderService;

    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
    public static final String TRADE_FINISHED = "TRADE_FINISHED";

    private AlipayClient alipayClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO 生成支付的URL地址
        alipayClient = new DefaultAlipayClient(payUrl,appid,merchantPrivateKey, "json", "utf-8", aliPublicKey, "RSA2");
    }

    // 创建支付表单
    public String createPayForm(Order order, Film film){
        String body = "大猿影院购票系统";
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(returnUrl + order.getId());
        alipayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ order.getId() +"\","
                + "\"total_amount\":\""+ order.getAmount() +"\","
                + "\"subject\":\""+ film.getFilmName() +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(form);
        return form;
    }

    public void processNotify(Map<String,String[]> parameterMap){
        Map<String,String> map = new HashMap<>(parameterMap.size());

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()){
            map.put(entry.getKey(),entry.getValue()[0]);
        }

        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(map, aliPublicKey, "UTF-8", "RSA2"); //调用SDK验证签名

            if(!signVerified){
                throw new BizException("验证签名失败");
            }

//            验签成功
            String tradStatus = map.get("trade_status");
            if (!"TRADE_SUCCESS".equals(tradStatus) && !"TRADE_FINISHED".equals(tradStatus)) {
                throw new BizException("支付失败");
            }

            String orderId = map.get("out_trade_no");
            String totalAmount = map.get("total_amount");
            String app_id = map.get("app_id");

            if (!appid.equals(app_id)){
                throw new BizException("appid不正确");
            }

            orderService.processAlipayNotify(Integer.parseInt(orderId),totalAmount);

        } catch (Exception e) {
            throw new BizException("支付宝异步通知处理异常");
        }
    }

    public void queryOrder(Order order){
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\""+order.getId()+"\"" +
                "  }");
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if(!response.isSuccess()){
                System.out.println("调用失败");
                return;
            }
            String totalAmount = response.getTotalAmount();
            String tradeStatus = response.getTradeStatus();
            if (!TRADE_SUCCESS.equals(tradeStatus) && !TRADE_FINISHED.equals(tradeStatus)) {
                throw new BizException("支付失败");
            }
            orderService.processQueryOrder(order,totalAmount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("阿里订单查询失败");
        }

    }
}
