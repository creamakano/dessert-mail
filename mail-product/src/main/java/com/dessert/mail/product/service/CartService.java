package com.dessert.mail.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Cart;
import com.dessert.common.entity.pms.CartVo;

import java.util.List;

public interface CartService extends IService<Cart> {
    Result getList(Long userId);

    Result<List<CartVo>> getListByIds(List<Long> ids);

    Result getPage(CartVo vo);
}
