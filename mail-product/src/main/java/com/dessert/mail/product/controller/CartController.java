package com.dessert.mail.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Cart;
import com.dessert.common.entity.pms.CartVo;
import com.dessert.common.entity.ums.LoginUser;
import com.dessert.common.entity.ums.User;
import com.dessert.mail.product.service.CartService;
import org.codehaus.jackson.sym.NameN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController extends BaseController{

    @Autowired
    private CartService baseService;

    @GetMapping("/list")
    public Result list(HttpSession session){
        Long userId = this.getLoginUserId(session);
        return baseService.getList(userId);
    }
    @GetMapping("/page")
    public Result page(CartVo vo , HttpSession session){
        Long userId = this.getLoginUserId(session);
        if(userId == null){
            return Result.unauthorized();
        }
        vo.setUserId(userId);
        return baseService.getPage(vo);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Cart cart){
        return Result.success(baseService.updateById(cart));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return Result.success(baseService.removeById(id));
    }

    @PostMapping("/insert")
    public Result insert(@RequestBody Cart cart, HttpSession session){
        Long userId = getLoginUserId(session);
        if(userId == null){
            return Result.unauthorized();
        }
        cart.setUserId(userId);
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId,userId);
        wrapper.eq(Cart::getProductId,cart.getProductId());
        Cart one = baseService.getOne(wrapper);
        if(ObjectUtils.isNotNull(one)){
            Integer num = one.getNum() + cart.getNum();
            if(num>100){
                num=100;
            }
            one.setNum(num);
            baseService.updateById(one);
            return Result.success();
        }
        return Result.success(baseService.save(cart));
    }

    @GetMapping("/getListByIds")
    public Result<List<CartVo>> getListByIds(@RequestParam ("ids")List<Long> ids){
        return baseService.getListByIds(ids);
    }
    @GetMapping("/cartSubmitByIds")
    public Result<List<CartVo>> cartSubmitByIds(@RequestParam ("ids")List<Long> ids){
        return baseService.cartSubmitByIds(ids);
    }

    @GetMapping("/settlement")
    public Result settlement(CartVo vo){
        return baseService.settlement(vo.getIds());
    }

    @GetMapping("/checkStorage")
    public Result checkStorage(CartVo vo){
        return baseService.checkStorage(vo);
    }
}
