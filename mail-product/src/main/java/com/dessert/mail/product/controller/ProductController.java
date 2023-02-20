package com.dessert.mail.product.controller;


import com.dessert.common.entity.common.BaseVo;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.ProductVo;
import com.dessert.mail.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @GetMapping("/page")
    public Result page(ProductVo vo,HttpSession session){
        vo.setUserId(this.getLoginUserId(session));
        return productService.getPage(vo);
    }

    @GetMapping("/getSession")
    public Result getSession(HttpSession session){
        return Result.success(session.getAttribute("user"));
    }
}
