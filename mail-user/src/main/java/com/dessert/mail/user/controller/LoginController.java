package com.dessert.mail.user.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.LoginUser;
import com.dessert.common.entity.ums.User;
import com.dessert.mail.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/login")
public class LoginController extends BaseController{

    @Autowired
    private LoginService loginService;

    @PostMapping()
    public Result login(HttpSession session,@RequestBody User user){
        return loginService.userLogin(session,user);
    }

    @PostMapping("/logout")
    public Result logout(HttpSession session){
        return loginService.logout(session);
    }

    @GetMapping("/session")
    public Result getSession(HttpSession session){
        LoginUser user = getSessionUser(session);
        if(ObjectUtils.isNull(user)){
            return Result.error("身份信息过期，请重新登录");
        }else {
            return Result.success(user);
        }
    }
}
