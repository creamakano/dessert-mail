package com.dessert.mail.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Cart;

public interface CartService extends IService<Cart> {
    Result getList(Long userId);
}
