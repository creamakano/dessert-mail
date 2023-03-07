package com.dessert.mail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.pms.Product;
import com.dessert.common.entity.ums.*;
import com.dessert.mail.user.mapper.AddressMapper;
import com.dessert.mail.user.mapper.CollectionMapper;
import com.dessert.mail.user.mapper.UserMapper;
import com.dessert.mail.user.service.AddressService;
import com.dessert.mail.user.service.CollectionService;
import com.dessert.mail.user.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.WeakHashMap;

@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {

    @Override
    public Result insert(Collection collection) {
        LambdaQueryWrapper<Collection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collection::getUserId,collection.getUserId());
        wrapper.eq(Collection::getProductId,collection.getProductId());
        if(ObjectUtils.isNotEmpty(this.list(wrapper))){
            return Result.success();
        }
        collection.setCollectionTime(new Date());
        this.save(collection);
        return Result.success();
    }

    @Override
    public Result deleteByProductId(Long productId, Long loginUserId) {
        LambdaQueryWrapper<Collection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collection::getProductId,productId)
                .eq(Collection::getUserId,loginUserId);
        this.remove(wrapper);
        return Result.success();
    }

    @Override
    public Result getPage(CollectionVo vo) {
        Page<Product> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        IPage<Product> iPage = baseMapper.getPage(page,vo);
        return Result.success(iPage);
    }
}
