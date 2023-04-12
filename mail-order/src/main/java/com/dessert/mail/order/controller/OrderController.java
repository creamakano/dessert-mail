package com.dessert.mail.order.controller;

import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.oms.Order;
import com.dessert.common.entity.oms.OrderVo;
import com.dessert.mail.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
    @PutMapping("")
    private Result update(@RequestBody Order order){
        return Result.success(orderService.updateById(order));
    }
    @DeleteMapping("/{id}")
    private Result detail(@PathVariable("id") Long id){
        return Result.success(orderService.removeById(id));
    }
    @GetMapping("/{id}")
    private Result delete(@PathVariable("id") Long id){
        return Result.success(orderService.getById(id));
    }
    @GetMapping("/allPage")
    private Result allPage(OrderVo vo){
        return orderService.getPage(vo);
    }

    @PutMapping("/send")
    private Result send(@RequestBody Order order){
        order.setStatus(3);
        return Result.success(orderService.updateById(order));
    }
    @PutMapping("/confirmReceipt")
    private Result confirmReceipt(@RequestBody Order order){
        return Result.success(orderService.confirmReceipt(order));
    }

    @PostMapping("/submitOrder")
    private Result submit(@RequestBody Order order, HttpSession session){

        return orderService.submitOrder(order,getLoginUserId(session));
    }

    // @PostMapping("/settlement")
    // private Result settlement(@RequestBody Order order, HttpSession session){
    //     return orderService.settlement(order,getLoginUserId(session));
    // }
}
