package com.dessert.mail.order.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.oms.Order;
import com.dessert.common.entity.pms.CartVo;
import com.dessert.mail.order.config.AlipayConfig;
import com.dessert.mail.order.feign.ProductFeignClient;
import com.dessert.mail.order.mapper.OrderMapper;
import com.dessert.mail.order.service.OrderService;
import com.dessert.mail.order.service.PayService;
import com.dessert.mail.order.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PayServiceImpl extends ServiceImpl<OrderMapper, Order> implements PayService {
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private OrderService orderService;

    @Override
    public Result settlement(Order order, Long loginUserId) {
        if(ObjectUtils.isEmpty(order.getCartIds())){
            return Result.parameterError();
        }
        List<CartVo> cartList = productFeignClient.getListByIds(order.getCartIds()).getData();
        Double total = cartList.stream().reduce(0.0, (x, y) -> x + (y.getPrice() * y.getDiscount() * y.getNum()), Double::sum);
        String description = cartList.stream().map(CartVo::getProductName).collect(Collectors.joining(","));
        order.setOrderNum(OrderUtil.getOrderNum());
        order.setDescription(description);
        order.setTotal(total);
        // order.setUserId(loginUserId);
        // order.setDate(new Date());
        // order.setIsComment(0);
        // order.setStatus(0);
        return Result.success(order);
    }

    @Override
    public Result toAlipay(Order order) {
        //保存订单
        if(ObjectUtils.isNull(order.getOrderNum(),order.getDescription(),order.getTotal())){
            return Result.parameterError();
        }
        order.setDate(new Date());
        order.setIsComment(0);
        order.setStatus(0);
        orderService.save(order);

        AlipayClient alipayClient = new DefaultAlipayClient(
                AlipayConfig.gatewayUrl,
                AlipayConfig.app_id,
                AlipayConfig.merchant_private_key, "json",
                AlipayConfig.charset,
                AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);//签名方式
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //这里设置支付后跳转的地址
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        String out_trade_no = order.getOrderNum();
        String total_amount = String.valueOf(order.getTotal());
        String subject = "甜品商城订单";
        String body = order.getDescription();
        String timeout_express = "5m";
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = null;
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return Result.success(form);
    }
}
