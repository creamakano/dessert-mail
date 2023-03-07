package com.dessert.mail.product.controller;


import com.dessert.common.entity.common.BaseVo;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Product;
import com.dessert.common.entity.pms.ProductType;
import com.dessert.common.entity.pms.ProductTypeVo;
import com.dessert.common.entity.pms.ProductVo;
import com.dessert.mail.product.service.ProductService;
import com.dessert.mail.product.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTypeService typeService;

    @GetMapping("/page")
    public Result page(ProductVo vo,HttpSession session){
        if(this.getLoginUserId(session)!=null){
            vo.setUserId(this.getLoginUserId(session));
        }
        return productService.getPage(vo);
    }

    @GetMapping("/allPage")
    public Result allPage(ProductVo vo){
        return productService.allPage(vo);
    }

    @GetMapping("/getSession")
    public Result getSession(HttpSession session){
        return Result.success(session.getAttribute("user"));
    }

    @GetMapping("getById/{id}")
    public Result getById(@PathVariable("id") Long id){
        Product product = productService.getById(id);
        product.setType(typeService.getById(product.getTypeId()).getName());
        return Result.success(product);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Product product){
        return productService.updateProduct(product);
    }
    @PostMapping("/insert")
    public Result insert(@RequestBody Product product){
        return productService.insert(product);
    }
    @DeleteMapping("/delete/{id}")
    public Result insert(@PathVariable("id") Long id){
        return productService.delete(id);
    }
}
