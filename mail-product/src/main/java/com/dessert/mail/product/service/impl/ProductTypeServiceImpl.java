package com.dessert.mail.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Product;
import com.dessert.common.entity.pms.ProductType;
import com.dessert.common.entity.pms.ProductTypeVo;
import com.dessert.mail.product.mapper.ProductTypeMapper;
import com.dessert.mail.product.service.ProductService;
import com.dessert.mail.product.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements ProductTypeService {

    @Autowired
    private ProductService productService;

    @Override
    public Result getPage(ProductTypeVo vo) {
        LambdaQueryWrapper<ProductType> wrapper = new LambdaQueryWrapper<>();
        if (ObjectUtils.isNotNull(vo.getName())) {
            wrapper.like(ProductType::getName, vo.getName());
        }
        IPage<ProductType> page = this.page(new Page<>(vo.getPageNo(), vo.getPageSize()), wrapper);
        return Result.success(page);
    }

    @Override
    public Result updateType(ProductType type) {
        LambdaQueryWrapper<ProductType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductType::getName, type.getName())
                .ne(ProductType::getId, type.getId());
        if (ObjectUtils.isNotEmpty(this.list(wrapper))) {
            return Result.error("该名称已存在");
        } else {
            this.updateById(type);
        }
        return Result.success();
    }

    @Override
    public Result deleteById(Long id) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getTypeId, id);
        if (ObjectUtils.isNotEmpty(productService.list(wrapper))) {
            return Result.error("该类型下存在商品，不能删除");
        }
        this.removeById(id);
        return Result.success();
    }

    @Override
    public Result insert(ProductType type) {
        LambdaQueryWrapper<ProductType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductType::getName, type.getName());
        if (ObjectUtils.isNotEmpty(this.list(wrapper))) {
            return Result.error("该名称已存在");
        } else {
            this.save(type);
        }
        return Result.success();
    }
}
