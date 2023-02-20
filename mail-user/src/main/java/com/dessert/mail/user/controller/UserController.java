package com.dessert.mail.user.controller;

import com.dessert.common.entity.common.Result;
import com.dessert.common.entity.ums.User;
import com.dessert.mail.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        return Result.success(userService.updateById(user));
    }

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody User user, HttpSession session){
        user.setId(this.getLoginUserId(session));
        return userService.updatePassword(user);
    }
}
