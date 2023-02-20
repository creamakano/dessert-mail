package com.dessert.mail.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Cart;
import com.dessert.common.entity.pms.CartVo;
import com.dessert.mail.product.mapper.CartMapper;
import com.dessert.mail.product.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {


    @Override
    public Result getList(Long userId) {
        List<CartVo> list = baseMapper.getList(userId);
        return Result.success(list);
    }
}
