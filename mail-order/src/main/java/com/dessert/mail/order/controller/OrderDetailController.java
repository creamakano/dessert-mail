package com.dessert.mail.order.controller;

import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.oms.OrderDetail;
import com.dessert.common.entity.oms.OrderDetailVo;
import com.dessert.mail.order.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("orderDetail")
public class OrderDetailController extends BaseController{
    @Autowired
    private OrderDetailService detailService;

    @GetMapping("/list")
    public Result list(OrderDetail detail){
        return detailService.getList(detail);
    }
    @GetMapping("/idList")
    public Result idList(OrderDetail detail){
        return detailService.getIdList(detail);
    }
    /**
     * 商品评价获取订单详情对应的商品详情
     */
    @GetMapping("/getDetailList")
    public Result getDetailList(OrderDetailVo vo){
        return detailService.getDetailList(vo.getIdList());
    }

    @GetMapping("/page")
    public Result page(OrderDetailVo vo){
        return detailService.getPage(vo);
    }


    /**
     * 修改订单详情评论状态
     */
    @GetMapping("/updateCommentStatus")
    public Integer updateCommentStatus(Long id){
        return detailService.updateCommentStatus(id);
    }
}
