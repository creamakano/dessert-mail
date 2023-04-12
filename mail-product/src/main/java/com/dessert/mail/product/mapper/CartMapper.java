package com.dessert.mail.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dessert.common.entity.pms.Cart;
import com.dessert.common.entity.pms.CartVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper extends BaseMapper<Cart> {
    List<CartVo> getList(@Param("userId") Long userId);

    List<CartVo> getListByIds(List<Long> ids);

    IPage<CartVo> getPage(@Param("page") Page<CartVo> page, @Param("vo") CartVo vo);

    List<CartVo> checkStorage(List<Long> ids);
}
