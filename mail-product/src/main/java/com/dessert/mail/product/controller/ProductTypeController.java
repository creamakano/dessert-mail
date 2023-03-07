package com.dessert.mail.product.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.ProductType;
import com.dessert.common.entity.pms.ProductTypeVo;
import com.dessert.mail.product.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/type")
public class ProductTypeController {

    @Autowired
    private ProductTypeService baseService;

    @GetMapping("/list")
    public Result list(){
        return Result.success(baseService.list());
    }
    @GetMapping("/page")
    public Result page(ProductTypeVo vo){
        return baseService.getPage(vo);
    }
    @PutMapping("/update")
    public Result update(@RequestBody ProductType type){
        return baseService.updateType(type);
    }
    @PostMapping("/insert")
    public Result insert(@RequestBody ProductType type){
        return baseService.insert(type);
    }
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable("id") Long id){
        return baseService.deleteById(id);
    }
}
