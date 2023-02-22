package com.dessert.mail.order.feign;

import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Cart;
import com.dessert.common.entity.pms.CartVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("mail-product")//服务名
public interface ProductFeignClient {
    @GetMapping("/product/cart/getListByIds")
    Result<List<CartVo>> getListByIds(@RequestParam ("ids")List<Long> ids);
}
