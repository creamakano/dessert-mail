package com.dessert.mail.order.service.impl;

import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.common.StatisticsVo;
import com.dessert.common.entity.oms.OrderDetail;
import com.dessert.common.entity.pms.ProductType;
import com.dessert.mail.order.feign.ProductFeignClient;
import com.dessert.mail.order.service.OrderDetailService;
import com.dessert.mail.order.service.OrderService;
import com.dessert.mail.order.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private OrderDetailService detailService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public Result getData() {
        Map<String,Object> map = new HashMap<>();

        //类型统计
        Map<Long, String> typeDict = productFeignClient.getProductTypeDict().getData();
        List<StatisticsVo> typeStatistics = detailService.typeStatistics();
        typeStatistics.stream().forEach(e->e.setName(typeDict.get(Long.parseLong(e.getName()))));
        //订单状态统计
        Map<String, String> orderStatusDict = getOrderStatusDict();
        List<StatisticsVo> orderStatusStatistics = orderService.orderStatusStatistics();
        orderStatusStatistics.stream().forEach(e->e.setName(orderStatusDict.get(e.getName())));
        //近30天订单数统计
        List<StatisticsVo> orderStatistics = orderService.orderStatistics();
        List<String> orderX = new ArrayList<>();
        List<String> orderY = new ArrayList<>();
        orderStatistics.stream().forEach(e->{
            orderX.add(e.getName());
            orderY.add(e.getValue());
        });

        Integer todayOrderNums = orderService.countTodayOrderNums();
        Double todayTotalMoney = orderService.countTodayTotalMoney();
        Double yesterdayOrderNums = orderService.countYesterdayOrderNums();
        Double weekOrderNums = orderService.countWeekOrderNums();

        map.put("type",typeStatistics);
        map.put("orderStatus",orderStatusStatistics);
        map.put("orderX",orderX);
        map.put("orderY",orderY);
        map.put("todayOrderNums",todayOrderNums==null?0:todayOrderNums);
        map.put("todayTotalMoney",todayTotalMoney==null?0:todayTotalMoney);
        map.put("yesterdayOrderNums",yesterdayOrderNums==null?0:yesterdayOrderNums);
        map.put("weekOrderNums",weekOrderNums==null?0:weekOrderNums);
        return Result.success(map);
    }

    Map<String, String> getOrderStatusDict(){
        Map<String, String> map = new HashMap<>();
        map.put("1","待支付");
        map.put("2","待发货");
        map.put("3","待收货");
        map.put("4","待评价");
        map.put("5","已完成");
        return map;
    }

}
