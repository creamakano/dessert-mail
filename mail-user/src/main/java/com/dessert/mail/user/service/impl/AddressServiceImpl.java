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

import java.util.List;

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

    @Override
    public Result setDefault(AddressInfo addressInfo) {
        LambdaQueryWrapper<AddressInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressInfo::getUserId,addressInfo.getUserId());
        wrapper.eq(AddressInfo::getIsDefault,1);
        AddressInfo address = this.getOne(wrapper);
        address.setIsDefault(0);
        this.updateById(address);
        addressInfo.setIsDefault(1);
        this.updateById(addressInfo);
        return Result.success();
    }

    @Override
    public Result delete(Long id, Long userId) {
        AddressInfo deleteAddress = this.getById(id);
        this.removeById(id);
        if(deleteAddress.getIsDefault() == 1) {
            LambdaQueryWrapper<AddressInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(AddressInfo::getUserId,userId);
            List<AddressInfo> addressInfoList = this.list(wrapper);
            if(ObjectUtils.isNotEmpty(addressInfoList)){
                AddressInfo addressInfo = addressInfoList.get(0);
                addressInfo.setIsDefault(1);
                this.updateById(addressInfo);
            }
        }

        return Result.success();
    }

    @Override
    public Result insert(AddressInfo addressInfo) {
        LambdaQueryWrapper<AddressInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressInfo::getUserId,addressInfo.getUserId());
        if(ObjectUtils.isEmpty(this.list(wrapper))){
            addressInfo.setIsDefault(1);
        }
        this.save(addressInfo);
        return Result.success();
    }

    private void buildCondition(AddressInfoVo vo, LambdaQueryWrapper<AddressInfo> wrapper) {
        if(ObjectUtils.isNotNull(vo.getUserId())){
            wrapper.eq( AddressInfo::getUserId, vo.getUserId());
        }
    }
}
