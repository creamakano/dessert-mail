package com.dessert.mail.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.oms.Order;
import com.dessert.common.entity.oms.OrderVo;
import com.dessert.mail.order.mapper.OrderMapper;
import com.dessert.mail.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Override
    public Result getPage(OrderVo vo) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        buildCondition(vo, wrapper);
        IPage<Order> page = this.page(new Page<>(vo.getPageNo(), vo.getPageSize()), wrapper);
        return Result.success(page);
    }

    @Override
    public Object confirmReceipt(Order order) {
        order.setStatus(4);
        this.updateById(order);
        return Result.success();
    }

    private void buildCondition(OrderVo vo, LambdaQueryWrapper<Order> wrapper) {
        if(ObjectUtils.isNotNull(vo.getUserId())){
            wrapper.eq(Order::getUserId, vo.getUserId());
        }
        if(ObjectUtils.isNotNull(vo.getId())){
            wrapper.eq(Order::getId, vo.getId());
        }
        if(ObjectUtils.isNotNull(vo.getOrderNum())){
            wrapper.like(Order::getOrderNum, vo.getOrderNum());
        }
        if(ObjectUtils.isNotNull(vo.getAddress())){
            wrapper.like(Order::getAddress, vo.getAddress());
        }
        if(vo.getIsComment()!=null){
            wrapper.eq(Order::getIsComment, vo.getIsComment());
        }
        if(vo.getStatus()!=null&&vo.getStatus()!=0){
            wrapper.eq(Order::getStatus, vo.getStatus());
        }
        if(vo.getStartTime()!=null){
            wrapper.ge(Order::getDate,vo.getStartTime());
        }
        if(vo.getEndTime()!=null){
            wrapper.le(Order::getDate,new Date(vo.getEndTime().getTime()+86400000));
        }
        wrapper.orderByDesc(Order::getDate);
    }
}
