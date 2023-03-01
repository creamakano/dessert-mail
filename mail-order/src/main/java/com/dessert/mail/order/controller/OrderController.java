package com.dessert.mail.order.controller;

import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.oms.Order;
import com.dessert.common.entity.oms.OrderVo;
import com.dessert.mail.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("order")
public class OrderController extends BaseController{

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    private Result page(OrderVo vo, HttpSession session){
        Long userId = getLoginUserId(session);
        vo.setUserId(userId);
        return orderService.getPage(vo);
    }
    @GetMapping("/allPage")
    private Result allPage(OrderVo vo){
        return orderService.getPage(vo);
    }
}
