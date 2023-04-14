package com.dessert.mail.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dessert.common.entity.common.StatisticsVo;
import com.dessert.common.entity.oms.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    List<OrderDetail> listByIds(@Param("list") List<Long> list);

    List<StatisticsVo> typeStatistics();
}
