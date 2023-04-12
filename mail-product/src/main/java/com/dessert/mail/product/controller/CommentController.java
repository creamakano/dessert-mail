package com.dessert.mail.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Cart;
import com.dessert.common.entity.pms.CartVo;
import com.dessert.common.entity.pms.Comment;
import com.dessert.common.entity.pms.CommentVo;
import com.dessert.mail.product.feign.OrderFeignClient;
import com.dessert.mail.product.service.CartService;
import com.dessert.mail.product.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController{


    @Autowired
    private CommentService baseService;

    @PostMapping("/insert")
    public Result insert(@RequestBody Comment comment, HttpSession session){
        Long userId = this.getLoginUserId(session);
        if(userId == null){
            return Result.unauthorized();
        }
        comment.setUserId(userId);
        return baseService.insert(comment);
    }
    @GetMapping("/page")
    public Result page(CommentVo vo){
        return baseService.getPage(vo);
    }

    @GetMapping("/allPage")
    public Result allPage(CommentVo vo){
        return baseService.getAllPage(vo);
    }
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable("id") Long id ){
        baseService.removeById(id);
        return Result.success();
    }
}
