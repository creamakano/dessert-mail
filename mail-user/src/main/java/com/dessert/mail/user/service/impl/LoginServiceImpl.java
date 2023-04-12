package com.dessert.mail.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.LoginUser;
import com.dessert.common.entity.ums.User;
import com.dessert.mail.user.mapper.UserMapper;
import com.dessert.mail.user.service.LoginService;
import com.dessert.mail.user.utils.Email;
import io.netty.util.internal.ObjectUtil;
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

    @Override
    public Result logout(HttpSession session) {
        session.removeAttribute("LoginUser");
        return Result.success();
    }

    @Override
    public Result getCode(String mail, HttpSession session) {
        //参数判断
        if("".equals(mail)||mail==null){
            return Result.error("邮箱不能为空");
        }
        String code = Email.sendEmail(mail);
        if(code==null){
            return Result.error("验证码发送失败");
        }
        session.setAttribute("code",code);
        return Result.success();
    }

    @Override
    public Result registry(User user, HttpSession session) {
        if(ObjectUtils.isNull(user.getMail(),user.getPassword(),user.getCode())){
            return Result.error("注册失败，请正确填写信息");
        }
        if(!user.getCode().equals(session.getAttribute("code"))){
            return Result.error("注册失败，验证码错误");
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getMail,user.getMail());
        if(ObjectUtils.isNotEmpty(this.getOne(wrapper))){
            return Result.error("该邮箱已经注册");
        }
        user.setAuth(0);
        user.setName(user.getMail());
        this.save(user);
        return Result.success();
    }
}
