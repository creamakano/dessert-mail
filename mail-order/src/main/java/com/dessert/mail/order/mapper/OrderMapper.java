package com.dessert.mail.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dessert.common.entity.common.StatisticsVo;
import com.dessert.common.entity.oms.Order;

import java.util.List;

public interface OrderMapper extends BaseMapper<Order> {
    List<StatisticsVo> orderStatusStatistics();

    List<StatisticsVo> orderStatistics();

    Integer countTodayOrderNums();

    Double countTodayTotalMoney();

    Double countYesterdayOrderNums();

    Double countWeekOrderNums();

}
