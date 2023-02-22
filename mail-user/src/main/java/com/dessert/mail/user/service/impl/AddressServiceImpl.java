package com.dessert.mail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.AddressInfo;
import com.dessert.common.entity.ums.AddressInfoVo;
import com.dessert.mail.user.mapper.AddressMapper;
import com.dessert.mail.user.service.AddressService;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressInfo> implements AddressService {

    @Override
    public Result getPage(AddressInfoVo vo) {
        if(ObjectUtils.isNull(vo.getUserId())){
            return Result.parameterError();
        }
        LambdaQueryWrapper<AddressInfo> wrapper = new LambdaQueryWrapper<>();
        buildCondition(vo, wrapper);
        IPage<AddressInfo> page = this.page(new Page<>(vo.getPageNo(), vo.getPageSize()), wrapper);
        return Result.success(page);
    }

    private void buildCondition(AddressInfoVo vo, LambdaQueryWrapper<AddressInfo> wrapper) {
        if(ObjectUtils.isNotNull(vo.getUserId())){
            wrapper.eq( AddressInfo::getUserId, vo.getUserId());
        }
    }
}
