package com.dessert.mail.order.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.dessert.common.entity.common.BaseVo;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.oms.Order;
import com.dessert.common.entity.oms.OrderVo;
import com.dessert.common.entity.pms.CartVo;
import com.dessert.mail.order.feign.ProductFeignClient;
import com.dessert.mail.order.service.PayService;
import com.dessert.mail.order.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("pay")
public class PayController extends BaseController {

    @Autowired
    private PayService payService;
    @PostMapping("/settlement")
    public Result settlement(@RequestBody Order order, HttpSession session){

        return payService.settlement(order,getLoginUserId(session));
    }

    @PostMapping("/alipay")
    public Result toAlipay(@RequestBody Order order,HttpSession session)  {
        order.setUserId(getLoginUserId(session));
        return payService.toAlipay(order);
    }
}
