package com.dessert.mail.product.controller;

import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Cart;
import com.dessert.common.entity.pms.CartVo;
import com.dessert.common.entity.ums.LoginUser;
import com.dessert.common.entity.ums.User;
import com.dessert.mail.product.service.CartService;
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
        cart.setUserId(userId);
        return Result.success(baseService.save(cart));
    }

    @GetMapping("/getListByIds")
    public Result<List<CartVo>> getListByIds(@RequestParam ("ids")List<Long> ids){
        return baseService.getListByIds(ids);
    }
}
