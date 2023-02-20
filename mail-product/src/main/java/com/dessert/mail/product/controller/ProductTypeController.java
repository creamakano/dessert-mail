package com.dessert.mail.product.controller;


import com.dessert.common.entity.common.Result;
import com.dessert.mail.product.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/type")
public class ProductTypeController {

    @Autowired
    private ProductTypeService baseService;

    @GetMapping("/list")
    public Result list(){
        return Result.success(baseService.list());
    }
}
