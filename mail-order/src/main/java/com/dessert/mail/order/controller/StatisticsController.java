package com.dessert.mail.order.controller;

import com.dessert.common.entity.common.Result;
import com.dessert.mail.order.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("")
    public Result statistics(){
        return statisticsService.getData();
    }
}
