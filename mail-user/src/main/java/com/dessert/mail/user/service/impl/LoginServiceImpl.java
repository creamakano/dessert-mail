package com.dessert.mail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.LoginUser;
import com.dessert.common.entity.ums.User;
import com.dessert.mail.user.mapper.UserMapper;
import com.dessert.mail.user.service.LoginService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {
    @Override
    public Result userLogin(HttpSession session, User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getMail,user.getMail())
                .eq(User::getPassword,user.getPassword());
        User checkUser = this.getOne(wrapper);
        if(ObjectUtils.isNull(checkUser)){
            return Result.error(401,"登录失败，身份信息有误");
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setId(checkUser.getId());
        loginUser.setMail(checkUser.getMail());
        loginUser.setName(checkUser.getName());
        loginUser.setPhone(checkUser.getPhone());
        loginUser.setAuth(checkUser.getAuth());
        session.setAttribute("LoginUser",loginUser);
        return Result.success(loginUser);
    }
}
