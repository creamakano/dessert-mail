package com.dessert.mail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.User;
import com.dessert.common.entity.ums.UserVo;
import com.dessert.mail.user.mapper.UserMapper;
import com.dessert.mail.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public Result updatePassword(User user) {
        if(ObjectUtils.isNull(user.getPassword(),user.getId(),user.getNewPassword())){
            return Result.parameterError();
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId,user.getId())
                .eq(User::getPassword,user.getPassword());
        if (ObjectUtils.isNull(this.getOne(wrapper))){
            return Result.error("密码错误");
        }
        user.setPassword(user.getNewPassword());
        this.updateById(user);
        return Result.success();
    }

    @Override
    public Result getPage(UserVo vo) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        buildCondition(vo, wrapper);
        IPage<User> page = this.page(new Page<>(vo.getPageNo(), vo.getPageSize()), wrapper);
        return Result.success(page);
    }

    private void buildCondition(UserVo vo, LambdaQueryWrapper<User> wrapper) {
        if(ObjectUtils.isNotNull(vo.getName())){
            wrapper.like(User::getName, vo.getName());
        }
        if(ObjectUtils.isNotNull(vo.getMail())){
            wrapper.like(User::getMail, vo.getMail());
        }
        if(vo.getAuth()!=null){
            wrapper.eq(User::getAuth, vo.getAuth());
        }
        if(vo.getId()!=null){
            wrapper.eq(User::getId, vo.getId());
        }
        if(ObjectUtils.isNotNull(vo.getPhone())){
            wrapper.like(User::getPhone, vo.getPhone());
        }
    }
}
