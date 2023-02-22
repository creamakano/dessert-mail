package com.dessert.mail.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.oms.Order;

public interface PayService extends IService<Order> {

    Result settlement(Order cartIds, Long loginUserId);

    Result toAlipay(Order order);
}
