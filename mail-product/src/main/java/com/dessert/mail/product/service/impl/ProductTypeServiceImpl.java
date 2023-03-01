package com.dessert.mail.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.ProductType;
import com.dessert.common.entity.pms.ProductTypeVo;
import com.dessert.mail.product.mapper.ProductTypeMapper;
import com.dessert.mail.product.service.ProductTypeService;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements ProductTypeService {


    @Override
    public Result getPage(ProductTypeVo vo) {
        LambdaQueryWrapper<ProductType> wrapper = new LambdaQueryWrapper<>();
        if(ObjectUtils.isNotNull(vo.getName())){
            wrapper.like(ProductType::getName,vo.getName());
        }
        IPage<ProductType> page = this.page(new Page<>(vo.getPageNo(), vo.getPageSize()),wrapper);
        return Result.success(page);
    }
}
