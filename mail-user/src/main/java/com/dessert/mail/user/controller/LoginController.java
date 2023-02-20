package com.dessert.mail.user.controller;

import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.User;
import com.dessert.mail.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping()
    public Result login(HttpSession session,@RequestBody User user){
        return loginService.userLogin(session,user);
    }

}
