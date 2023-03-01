package com.dessert.mail.order.controller;

import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.oms.OrderDetail;
import com.dessert.mail.order.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("orderDetail")
public class OrderDetailController extends BaseController{
    @Autowired
    private OrderDetailService detailService;


    @GetMapping("/list")
    public Result list(OrderDetail detail){
        return detailService.getList(detail);
    }
}
