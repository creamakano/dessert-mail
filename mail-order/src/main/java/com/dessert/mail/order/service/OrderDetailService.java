package com.dessert.mail.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.common.StatisticsVo;
import com.dessert.common.entity.oms.OrderDetail;
import com.dessert.common.entity.oms.OrderDetailVo;

import java.util.List;


public interface OrderDetailService extends IService<OrderDetail> {

    Result getList(OrderDetail detail);

    Result getPage(OrderDetailVo vo);

    Result getIdList(OrderDetail detail);

    Result getDetailList(List<Long> idList);

    Integer updateCommentStatus(Long id);

    List<StatisticsVo> typeStatistics();
}
