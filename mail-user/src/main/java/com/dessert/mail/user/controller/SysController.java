package com.dessert.mail.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.SysDict;
import com.dessert.mail.user.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys")
public class SysController {
    @Autowired
    private SysDictService baseService;
    @GetMapping("/dict")
    public Result getDict(String name){
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getName,name);
        List<SysDict> list = baseService.list(wrapper);
        Map<Integer,String> map = new HashMap<>();
        list.forEach(e -> map.put(e.getValue(),e.getLabel()));
        return Result.success(map);
    }
}
