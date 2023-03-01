package com.dessert.mail.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.oms.OrderDetail;
import com.dessert.mail.order.mapper.OrderDetailMapper;
import com.dessert.mail.order.service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Override
    public Result getList(OrderDetail detail) {
        if(detail.getOrderId() == null){
            return Result.parameterError();
        }
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper();
        buildCondition(detail, wrapper);
        List<OrderDetail> list = this.list(wrapper);
        return Result.success(list);
    }

    void buildCondition(OrderDetail detail, LambdaQueryWrapper<OrderDetail> wrapper) {
        if(detail.getOrderId() != null){
            wrapper.eq(OrderDetail::getOrderId, detail.getOrderId());
        }
    }
}
