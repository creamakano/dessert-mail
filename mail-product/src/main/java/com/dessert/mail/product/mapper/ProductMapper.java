package com.dessert.mail.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dessert.common.entity.pms.Product;
import com.dessert.common.entity.pms.ProductVo;
import org.apache.ibatis.annotations.Param;


public interface ProductMapper extends BaseMapper<Product> {
    IPage<Product> getPage(@Param("page") Page<Object> page, @Param("vo") ProductVo vo);
}
