package com.dessert.mail.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Product;
import com.dessert.common.entity.pms.ProductVo;
import com.dessert.mail.product.mapper.ProductMapper;
import com.dessert.mail.product.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {


    @Override
    public Result getPage(ProductVo vo) {
        Page<Product> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        IPage<Product> iPage;
        if (vo.getUserId() != null) {
            iPage = baseMapper.getPage(page, vo);
        }else {
            LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
            this.buildCondition(wrapper,vo);
            iPage = this.page(page,wrapper);
        }
        return Result.success(iPage);
    }

    @Override
    public Result allPage(ProductVo vo) {
        Page<Product> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        IPage<Product> iPage = baseMapper.getAllPage(page, vo);
        return Result.success(iPage);
    }

    @Override
    public Result updateProduct(Product product) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getName, product.getName())
                .ne(Product::getId, product.getId());
        if (ObjectUtils.isNotEmpty(this.list(wrapper))) {
            return Result.error("该名称已存在");
        }
        this.updateById(product);
        return Result.success();
    }

    @Override
    public Result insert(Product product) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getName, product.getName());
        if (ObjectUtils.isNotEmpty(this.list(wrapper))) {
            return Result.error("该名称已存在");
        }
        this.save(product);
        return Result.success();
    }

    @Override
    public Result delete(Long id) {
        this.removeById(id);
        return Result.success();
    }

    private void buildCondition(LambdaQueryWrapper<Product> wrapper, ProductVo param) {
        if (ObjectUtils.isNotNull(param.getName())) {
            wrapper.like(Product::getName, param.getName());
        }
        if (ObjectUtils.isNotNull(param.getTypeId()) && param.getTypeId() != 0) {
            wrapper.eq(Product::getTypeId, param.getTypeId());
        }
        if (ObjectUtils.isNotNull(param.getDescription())) {
            wrapper.eq(Product::getDescription, param.getDescription());
        }
        if (ObjectUtils.isNotNull(param.getStatus())) {
            wrapper.eq(Product::getStatus, param.getStatus());
        }
        wrapper.orderByDesc(Product::getUpdateTime);
    }


}
