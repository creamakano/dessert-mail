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
        final LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        Page<Object> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        IPage<Product> iPage = baseMapper.getPage(page,vo);
        return Result.success(iPage);
    }

    private void buildCondition(LambdaQueryWrapper<Product> wrapper, ProductVo param) {
        if(ObjectUtils.isNotNull(param.getName())){
            wrapper.like(Product::getName,param.getName());
        }
        if(ObjectUtils.isNotNull(param.getTypeId())&& param.getTypeId()!=0){
            wrapper.eq(Product::getTypeId,param.getTypeId());
        }
        if(ObjectUtils.isNotNull(param.getIsAdvertisement())){
            wrapper.eq(Product::getIsAdvertisement,param.getIsAdvertisement());
        }
        if(ObjectUtils.isNotNull(param.getIsRecommend())){
            wrapper.eq(Product::getIsRecommend,param.getIsRecommend());
        }
        wrapper.orderByDesc(Product::getUpdateTime);
    }


}
