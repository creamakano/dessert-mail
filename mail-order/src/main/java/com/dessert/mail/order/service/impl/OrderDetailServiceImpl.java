package com.dessert.mail.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.common.StatisticsVo;
import com.dessert.common.entity.oms.Order;
import com.dessert.common.entity.oms.OrderDetail;
import com.dessert.common.entity.oms.OrderDetailVo;
import com.dessert.mail.order.mapper.OrderDetailMapper;
import com.dessert.mail.order.service.OrderDetailService;
import com.dessert.mail.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Autowired
    private OrderService orderService;

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
    @Override
    public Result getIdList(OrderDetail detail) {
        if(detail.getOrderId() == null){
            return Result.parameterError();
        }
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper();
        buildCondition(detail, wrapper);
        List<Long> list = this.list(wrapper).stream().map(OrderDetail::getId).collect(Collectors.toList());
        return Result.success(list);
    }

    @Override
    public Result getDetailList(List<Long> idList) {
        List<OrderDetail> detailList = baseMapper.listByIds(idList);
        return Result.success(detailList);
    }

    @Override
    public Integer updateCommentStatus(Long id) {
        OrderDetail detail = this.getById(id);
        detail.setIsComment(1);
        this.updateById(detail);
        //修改订单的状态
        LambdaQueryWrapper<OrderDetail> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getOrderId,detail.getOrderId());
        wrapper.eq(OrderDetail::getIsComment,0);
        if(ObjectUtils.isEmpty(this.list(wrapper))){//全都评价完了
            Order order = new Order();
            order.setId(detail.getOrderId());
            order.setStatus(5);
            orderService.updateById(order);
            return 1;
        }
        return 0;
    }

    @Override
    public List<StatisticsVo> typeStatistics() {
        return baseMapper.typeStatistics();
    }

    @Override
    public Result getPage(OrderDetailVo vo) {
        if(vo.getOrderId() == null){
            return Result.parameterError();
        }
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper();
        buildCondition(vo, wrapper);
        IPage<OrderDetail> iPage = this.page(new Page<>(vo.getPageNo(), vo.getPageSize()), wrapper);
        return Result.success(iPage);
    }

    void buildCondition(OrderDetail detail, LambdaQueryWrapper<OrderDetail> wrapper) {
        if(detail.getOrderId() != null){
            wrapper.eq(OrderDetail::getOrderId, detail.getOrderId());
        }
    }
    void buildCondition(OrderDetailVo detail, LambdaQueryWrapper<OrderDetail> wrapper) {
        if(detail.getOrderId() != null){
            wrapper.eq(OrderDetail::getOrderId, detail.getOrderId());
        }
    }
}
