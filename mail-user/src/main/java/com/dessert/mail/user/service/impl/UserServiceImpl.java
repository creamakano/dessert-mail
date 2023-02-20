package com.dessert.mail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.User;
import com.dessert.mail.user.mapper.UserMapper;
import com.dessert.mail.user.service.LoginService;
import com.dessert.mail.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

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
}
