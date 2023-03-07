package com.dessert.mail.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.oms.Order;
import com.dessert.common.entity.oms.OrderVo;

public interface OrderService extends IService<Order> {
    Result getPage(OrderVo vo);

    Object confirmReceipt(Order order);
}
