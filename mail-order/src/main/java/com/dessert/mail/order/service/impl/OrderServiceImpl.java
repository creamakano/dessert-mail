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
import com.dessert.common.entity.oms.OrderVo;
import com.dessert.common.entity.pms.CartVo;
import com.dessert.mail.order.feign.ProductFeignClient;
import com.dessert.mail.order.mapper.OrderMapper;
import com.dessert.mail.order.service.OrderDetailService;
import com.dessert.mail.order.service.OrderService;
import com.dessert.mail.order.utils.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private OrderDetailService detailService;

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

    @Override
    public Result settlement(Order order, Long userId) {
        if(ObjectUtils.isEmpty(order.getCartIds())){
            return Result.parameterError();
        }
        List<CartVo> cartList = productFeignClient.getListByIds(order.getCartIds()).getData();
        Double total = cartList.stream().reduce(0.0, (x, y) -> x + (y.getPrice() * y.getDiscount() * y.getNum()), Double::sum);
        order.setTotal(total);
        return Result.success(order);
    }

    @Override
    public Result submitOrder(Order order, Long userId) {
        if(ObjectUtils.isEmpty(order.getCartIds())){
            return Result.parameterError();
        }
        List<CartVo> cartList = productFeignClient.cartSubmitByIds(order.getCartIds()).getData();
        Double total = cartList.stream().reduce(0.0, (x, y) -> x + (y.getPrice() * y.getDiscount() * y.getNum()), Double::sum);
        String description = cartList.stream().map(CartVo::getProductName).collect(Collectors.joining(","));
        order.setOrderNum(OrderUtil.getOrderNum());
        order.setDescription(description);
        order.setTotal(total);
        order.setDate(new Date());
        order.setIsComment(0);
        order.setStatus(1);
        order.setUserId(userId);
        this.save(order);
        List<OrderDetail> detailList = new ArrayList<>();
        //保存订单详情
        for (CartVo cart : cartList) {
            OrderDetail detail = new OrderDetail();
            detail.setNum(cart.getNum());
            detail.setOrderId(order.getId());
            detail.setProductId(cart.getProductId());
            detail.setProductName(cart.getProductName());
            detail.setProductPrice(cart.getPrice()*cart.getDiscount());
            detail.setProductPicture(cart.getPicture());
            detail.setProductTypeId(cart.getTypeId());
            detailList.add(detail);
        }
        detailService.saveBatch(detailList);
        return Result.success(order);
    }

    @Override
    public List<StatisticsVo> orderStatusStatistics() {
        return baseMapper.orderStatusStatistics();
    }

    @Override
    public List<StatisticsVo> orderStatistics() {
        return baseMapper.orderStatistics();
    }

    @Override
    public Integer countTodayOrderNums() {
        return baseMapper.countTodayOrderNums();
    }

    @Override
    public Double countTodayTotalMoney() {
        return baseMapper.countTodayTotalMoney();
    }

    @Override
    public Double countYesterdayOrderNums() {
        return baseMapper.countYesterdayOrderNums();
    }

    @Override
    public Double countWeekOrderNums() {
        return baseMapper.countWeekOrderNums();
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
