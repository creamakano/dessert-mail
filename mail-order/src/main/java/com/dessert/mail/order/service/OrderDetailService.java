package com.dessert.mail.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.oms.OrderDetail;


public interface OrderDetailService extends IService<OrderDetail> {

    Result getList(OrderDetail detail);
}
