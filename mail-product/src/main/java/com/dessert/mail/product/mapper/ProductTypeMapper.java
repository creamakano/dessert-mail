package com.dessert.mail.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dessert.common.entity.pms.ProductType;
import com.dessert.common.entity.pms.ProductTypeVo;
import org.apache.ibatis.annotations.Param;


public interface ProductTypeMapper extends BaseMapper<ProductType> {
    IPage<ProductType> getPage(@Param("page") Page<Object> objectPage, @Param("vo") ProductTypeVo vo);
}
